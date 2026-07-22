# Photo Widget

Android-додаток з домашнім віджетом, який показує обране фото з галереї на робочому столі.

## Можливості

Коротко: фото на робочому столі через віджет; кілька незалежних інстансів; framing (cover/contain, поворот, alignment, форма); pin / edit / reset у додатку.

Канон екранів і вмісту in-app UI (для дизайну / переписування): **[`docs/UI_CAPABILITIES.md`](docs/UI_CAPABILITIES.md)**.

## Вимоги

- Android 13+ (API 33)
- JDK 17
- Android SDK 35

## Технології

- Kotlin
- Jetpack Compose + Material 3
- App Widget (`AppWidgetProvider` + `RemoteViews`)
- DataStore Preferences
- Coil (прев’ю в додатку)
- ExifInterface (коректна орієнтація фото з камери)

## Збірка

```bash
./gradlew assembleDebug
```

APK буде тут:

```
app/build/outputs/apk/debug/app-debug.apk
```

### Локальний SDK

Створіть `local.properties` у корені проєкту:

```properties
sdk.dir=/шлях/до/Android/Sdk
```

## Встановлення

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Як користуватися

1. Відкрийте додаток **Photo Widget**.
2. Оберіть фото, назву віджета та налаштуйте стиль.
3. Натисніть **Додати на робочий стіл** або додайте віджет вручну через лаунчер.
4. На робочому столі змінюйте розмір віджета пальцями.
5. Для редагування або скидання існуючого віджета відкрийте додаток і виберіть його в списку **Активні віджети**.
6. Якщо фото не вибрано, на placeholder відображається номер віджета (`Віджет #N`).

## Обмеження лаунчера

- На багатьох сучасних launcher (Android 12+) система може додатково застосовувати власне округлення зовнішніх кутів віджета.
- Це поведінка хоста віджетів (launcher), тому повністю вимкнути її з боку застосунку зазвичай неможливо.

## Структура проєкту

```
app/src/main/java/com/photowidget/
├── MainActivity.kt              # головний екран
├── WidgetConfigureActivity.kt   # конфігурація при додаванні віджета
├── data/                        # модель і збереження налаштувань
├── widget/                      # RemoteViews-віджет і рендер зображень
└── ui/                          # спільний UI налаштувань
```

Для AI-агентів і розробників, які змінюють віджет: **`AGENTS.md`** (критичні обмеження рендеру) та **`.cursor/rules/widget-rendering.mdc`**.

Канон екранів UI: **[`docs/UI_CAPABILITIES.md`](docs/UI_CAPABILITIES.md)**.

Публікація в Google Play і маркетинг: **[`go-to-market/`](go-to-market/)**.

## Ліцензія

MIT
