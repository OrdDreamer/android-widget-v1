# UI design — Photo Widget

Канонічний список **екранів і вмісту** для дизайну / переписування in-app UI.

Віджет (`RemoteViews`), рендер bitmap і DataStore — **поза scope** (див. `AGENTS.md`).

**Статус реалізації** (орієнтир для розробки, не для макетів): `done` · `partial` · `planned`

---

## Канон екранів (дизайн)

| # | Екран | Тип |
|---|-------|-----|
| 1 | Launch screen | fullscreen, коротко на старті |
| 2а | Головний — empty | стан головного |
| 2б | Головний — список віджетів | стан головного |
| 3 | App settings | екран |
| 4 | Reset widget | dialog |
| 5 | About | екран |
| 6 | Налаштування віджета | екран (Edit + Configure з лаунчера — один макет) |

Окремих onboarding-екранів немає: роль «першого знайомства» виконує **empty** головного + (опційно) Launch screen.

---

### 1. Launch screen · `planned`

Чисто стилістичний екран на кілька секунд при запуску.

- Title + subtitle
- Теплий декор у стилі додатку
- Без кнопок і продуктової логіки

---

### 2а. Головний — empty · `done` (макет оновити під канон)

- Hero-зображення
- Коротка інструкція, як додати віджет
- Кнопка «Додати віджет» → `requestPinAppWidget`
- Header: назва додатку + ⚙ → App settings
- Fallback copy / Toast, якщо pin не підтримується

---

### 2б. Головний — список віджетів · `done` (макет оновити під канон)

- Список віджетів
- Кнопка «Додати віджет» — **sticky внизу** екрана
- Header: назва + ⚙ → App settings

Кожен рядок списку:

- маленьке зображення (thumbnail; placeholder, якщо фото немає)
- назва (`displayName` або `Віджет #N`)
- підзаголовок / мета (напр. розмір у клітинках · стиль)
- кнопка редагування → екран 6
- кнопка reset → dialog 4

**Не робити в v1:** swipe-delete, drag reorder, folders.

---

### 3. App settings · `partial`

Вхід: ⚙ з головного. Стандартний розділ налаштувань **додатку** (не віджета).

- Language (вибір мови додатку)
- About → екран 5
- Privacy (пункт / лінк; без акценту в UI)
- Стиль: у загальній темі додатку

Мову **не** дублювати на головному, empty чи в налаштуваннях віджета.

---

### 4. Reset widget (dialog) · `done`

- Заголовок
- Підзаголовок
- Підтвердити
- Скасувати

Поведінка: очищає фото й налаштування; віджет **лишається** на робочому столі.

---

### 5. About · `planned`

- Мінімалістично, тепло, у стилі додатку
- Типовий вміст: назва, короткий опис, версія, за потреби ліцензія / посилання
- Опційно: блок підтримки автора

---

### 6. Налаштування віджета · `done` (макет оновити під канон)

Один scroll-екран для in-app Edit і launcher Configure (`WidgetConfigureActivity`).

| Блок | Вміст |
|------|--------|
| Прев’ю | фото / placeholder; tap може відкривати picker |
| Фото | кнопка обрати / змінити (system Photo Picker) |
| Вирівнювання | сітка 3×3 (лише якщо є фото) |
| Обертання | ±90° (лише якщо є фото) |
| Вписування | Cover \| Contain (**без** slider масштабу / zoom) |
| Стиль віджета | frame style (Classic / Polaroid / Minimal / Vintage) |
| Форма | прямокутник \| заокруглені кути \| коло |
| Скруглення | slider / контроль лише для «заокруглені кути» |
| Назва | optional text field |
| Клік по віджету | без дії \| відкрити додаток (список) \| відкрити цей віджет |
| Дії | Зберегти · Скасувати |

Модель полів: `WidgetConfig` (`data/WidgetConfig.kt`).  
Прев’ю має візуально відповідати віджету (scale, alignment, rotation+EXIF, shape, frame) — логіка в `WidgetImagePreview` / `AGENTS.md`.

---

## Архітектура входів (контракти)

| Entry | Клас | Роль |
|-------|------|------|
| LAUNCHER | `MainActivity` | Launch → головний shell |
| Клік по віджету → edit | `MainActivity` + `EXTRA_EDIT_WIDGET_ID` | екран 6 для `appWidgetId` |
| Configure з лаунчера | `WidgetConfigureActivity` | той самий екран 6; `RESULT_OK` / `finish` |

Після Save / Reset: `WidgetConfigRepository` + оновлення віджета. Деталі рендеру не чіпати.

---

## Системні поверхні (не малювати як продуктові екрани)

| Що | Нотатка |
|----|---------|
| Photo Picker | системний |
| Pin widget | системний / лаунчер |
| Toast | pin fail, reset done |
| Interstitial після Save | реклама · `planned` |
| Banner slots | in-app only · `partial` |

Bottom sheets — `out_of_scope` v1.

---

## Тема

| | |
|--|--|
| Light + Dark (system) | `done` |
| Warm calm visual direction | канон бренду |
| Dynamic Color | optional пізніше |
| Edge-to-edge / safe area | `done` |

---

## Поза scope v1

- Slider вільного масштабу / zoom фото (лише Cover / Contain)
- Окремі onboarding screens
- Slideshow / кілька фото на віджет
- Lock screen widget, cloud / акаунти
- Default widget settings (глобальні дефолти)
- Freemium / paywall
- Реклама всередині віджета
- Власна галерея замість Photo Picker
- Orphan language switcher на головному

---

## Поточні файли UI (орієнтир для заміни)

```
app/src/main/java/com/photowidget/
├── MainActivity.kt
├── WidgetConfigureActivity.kt
└── ui/
    ├── MainScreen.kt              # 2а / 2б
    ├── WidgetSettingsScreen.kt    # 6
    ├── AppSettingsScreen.kt       # 3 (+ About — новий екран)
    ├── WidgetImagePreview.kt      # логіку EXIF/повороту зберегти
    ├── theme/
    └── components/
```

Launch screen і About — додати при реалізації нового UI.

---

## Changelog

| Дата | Зміна |
|------|--------|
| Jul 2026 | Початковий інвентар; старі design brief / mockups видалені |
| Jul 2026 | Канон екранів дизайну: Launch, Home empty/list (sticky CTA), App settings, Reset dialog, About, Widget settings; без zoom slider |
