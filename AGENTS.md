# AGENTS.md — Photo Widget

LLM-friendly контекст для роботи з цим репозиторієм. Читай **перед змінами у віджеті або рендері зображень**.

## Проєкт

- **Пакет:** `com.photowidget`
- **minSdk:** 33, **targetSdk:** 35
- **UI додатку:** Jetpack Compose + Material 3
- **Віджет:** `AppWidgetProvider` + `RemoteViews` (НЕ Glance)
- **Дані:** DataStore Preferences (`WidgetConfigRepository`)
- **Прев’ю в додатку:** Coil (`WidgetImagePreview` — EXIF вимкнено в Coil, поворот вручну)

## Ключові файли

| Файл | Відповідальність |
|------|------------------|
| `widget/PhotoWidgetRenderer.kt` | Збірка `RemoteViews`, кліки, розмір віджета |
| `widget/WidgetImageCache.kt` | Decode → EXIF/rotate/scale/shape → bitmap для віджета |
| `widget/ImageOrientationHelper.kt` | EXIF-орієнтація |
| `widget/WidgetUriHelper.kt` | Дозволи URI галереї (НЕ для показу у віджеті) |
| `widget/PhotoWidgetReceiver.kt` | `AppWidgetProvider` |
| `data/WidgetConfig.kt` | Модель налаштувань (rotation, alignment, scale, shape) |
| `data/ImageAlignment.kt` | 9 варіантів вирівнювання |
| `ui/WidgetImagePreview.kt` | Прев’ю з синхронізованим EXIF/поворотом |
| `res/layout/widget_photo.xml` | `initialLayout` віджета |
| `res/xml/photo_widget_info.xml` | Метадані віджета |

## Збірка та тест

```bash
export JAVA_HOME=/path/to/jdk-17
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Після змін віджета: **видалити старі віджети з робочого столу**, додати заново, перевірити **≥2 віджети** на **Android 17+** (Pixel + Nexus Launcher).

```bash
adb logcat | grep -iE "PhotoWidgetRenderer|AppWidgetHostView|RemoteViews|FileProvider|photowidget"
```

---

## CRITICAL: «не вдається завантажити віджет»

Найчастіша регресія проєкту. **Не змінюй архітектуру без читання цього розділу.**

### Симптом

На домашньому екрані: **«не вдається завантажити віджет»** / **«не вдається відобразити контент»**.

### Історія (що вже пробували)

| Підхід | Результат |
|--------|-----------|
| Jetpack Glance + `ImageProvider(bitmap)` | FAIL — Binder ~1 MB |
| Glance + `ImageProvider(uri)` | Нестабільно → відмова від Glance |
| `RemoteViews` + `setImageViewUri` + FileProvider | Нестабільно; 2-й віджет часто ламається |
| `setImageViewUri` на **Android 17** (Pixel, Nexus Launcher) | **FAIL** — `AppsFilter: BLOCKED`, лаунчер не читає `content://` |
| `setImageViewBitmap` + bitmap < ~1 MB | **OK — поточний стандарт** |
| `setViewOutlinePreferredRadius` / `setClipToOutline` у RemoteViews | FAIL на частині лаунчерів |

### Поточна архітектура (MUST KEEP)

1. Рендер офлайн у `WidgetImageCache.renderForWidget()`.
2. Доставка в лаунчер **тільки** через `views.setImageViewBitmap()` у `PhotoWidgetRenderer`.
3. `MAX_BITMAP_BYTES` ≈ **2_100_000** (RGB_565) у `WidgetImageCache` — не перевищувати без тесту на Android 17.
4. Форма (rounded/circle) **запікається в bitmap** з **прозорими** кутами (ARGB), не через RemoteViews outline API і не з фоном `#121212`.
5. `widget_photo.xml`: `scaleType="fitXY"`, фон `#121212` у `initialLayout`.
6. `FileProvider` у маніфесті **не використовувати** для `ImageView` у віджеті.

### FORBIDDEN (без явного запиту + тесту на Android 17)

```kotlin
// ❌ НІКОЛИ як основний шлях для фото віджета
views.setImageViewUri(R.id.widget_image, fileProviderUri)

// ❌ НІКОЛИ для форми віджета
views.setViewOutlinePreferredRadius(...)
views.setClipToOutline(...)

// ❌ НІКОЛИ повертати Glance для цього віджета
```

```kotlin
// ✅ Єдиний стабільний шлях
val bitmap = WidgetImageCache.renderForWidget(...)
views.setImageViewBitmap(R.id.widget_image, bitmap)
```

### Якість vs стабільність

- `setImageViewBitmap` обмежує payload (~2.1 MB). Для великих віджетів рендер намагається зайти в **повну** роздільність віджета в px, щоб `fitXY` не розтягував дрібний bitmap (головна причина розмиття).
- **RGB_565** лише для непрозорих варіантів (`RECTANGLE` + `COVER`) — у 2× більше пікселів у тому ж ліміті.
- **ARGB_8888 з прозорими кутами** для `ROUNDED_RECT`, `CIRCLE` і `CONTAIN` — **не** заповнювати `#121212` під кліпом (лаунчер показує прозорий фон).
- Якщо повний розмір не вміщується — **один** пропорційний downscale, не цикл.
- **Не** вирішувати якість через `setImageViewUri` — ламає Android 17.
- Після зміни `MAX_BITMAP_BYTES` тестувати завантаження віджета на Android 17+.

### EXIF і прев’ю

- `BitmapFactory` **не** застосовує EXIF; Coil **застосовує** за замовчуванням.
- Віджет: `ImageOrientationHelper` + `totalRotation(exif + userDegrees)`.
- Прев’ю: `WidgetImagePreview` з `ExifOrientationPolicy.IGNORE` + той самий `totalRotation`.
- **Завжди синхронізуй** логіку прев’ю і `WidgetImageCache`.

### Типові logcat-маркери поломки

- `AppWidgetHostView: Error displaying widget`
- `Permission Denial` / `AppsFilter: BLOCKED` (URI-шлях)
- `TransactionTooLargeException` (завеликий bitmap)

---

## Інші обмеження

- Launcher може додатково округляти зовнішні кути віджета — це не виправляється з боку застосунку.
- Розмір віджета для рендеру: portrait → `minWidth × maxHeight` dp; landscape → `maxWidth × minHeight` dp.
