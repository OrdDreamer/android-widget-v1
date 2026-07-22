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

## Єдиний App Shell (layout по всьому додатку)

Усі **продуктові екрани** використовують один і той самий каркас. Контент екрана міняється всередині; chrome однаковий.

```
┌─────────────────────────────┐
│ Header                      │
│ [← back?]  Title    [action?]│
├─────────────────────────────┤
│                             │
│     Content (scroll)        │
│                             │
├─────────────────────────────┤
│ [optional sticky CTA]       │  ← напр. «Додати віджет» на головному зі списком
├─────────────────────────────┤
│ Ad banner (pinned bottom)   │  ← завжди найнижчий шар chrome
└─────────────────────────────┘
```

| Зона | Правила |
|------|---------|
| **Header** | Назва додатку або розділу. Опційно **назад** зліва. Опційно **дія справа** (зазвичай settings / меню). |
| **Content** | Єдиний scrollable контент екрана. |
| **Sticky CTA** | За потреби над банером (не замість банера). |
| **Ad banner** | Закріплений **внизу екрана** на всіх shell-екранах. Не імітує primary-кнопки. Лише in-app, не у віджеті. |

**Винятки (без shell):**

| Поверхня | Чому |
|----------|------|
| Launch screen | fullscreen brand, без chrome |
| Reset dialog | modal поверх shell |
| Photo Picker / pin / Toast / interstitial | системні / рекламні overlays |

Мапінг header по екранах:

| Екран | Title | Back | Right action |
|-------|-------|------|--------------|
| Головний (empty / list) | назва додатку | — | App settings |
| App settings | App settings / Налаштування | ← | — |
| About | About | ← | — |
| Налаштування віджета | Configure / Налаштування віджета | ← (якщо in-app) | — (або меню, якщо з’явиться) |

---

### 1. Launch screen · `planned`

**Без App Shell.** Чисто стилістичний екран на кілька секунд при запуску.

- Title + subtitle
- Теплий декор у стилі додатку
- Без кнопок, header і банера

---

### 2а. Головний — empty · `done` (макет оновити під канон)

**App Shell:** title = додаток; right = settings; banner внизу.

- Hero-зображення
- Коротка інструкція, як додати віджет
- Кнопка «Додати віджет» → `requestPinAppWidget` (у контенті або над банером)
- Fallback copy / Toast, якщо pin не підтримується

---

### 2б. Головний — список віджетів · `done` (макет оновити під канон)

**App Shell:** title = додаток; right = settings; banner внизу.

- Список віджетів (content)
- Sticky «Додати віджет» **над** ad banner
- Ad banner — найнижчий

Кожен рядок списку:

- маленьке зображення (thumbnail; placeholder, якщо фото немає)
- назва (`displayName` або `Віджет #N`)
- підзаголовок / мета (напр. розмір у клітинках · стиль)
- кнопка редагування → екран 6
- кнопка reset → dialog 4

**Не робити в v1:** swipe-delete, drag reorder, folders.

---

### 3. App settings · `partial`

**App Shell:** title = settings; back; banner внизу.

Вхід: ⚙ з головного. Стандартний розділ налаштувань **додатку** (не віджета).

- Language (вибір мови додатку)
- About → екран 5
- Privacy (пункт / лінк; без акценту в UI)

Мову **не** дублювати на головному, empty чи в налаштуваннях віджета.

---

### 4. Reset widget (dialog) · `done`

**Без власного shell** — діалог поверх поточного екрана (зазвичай головний зі списком).

- Заголовок
- Підзаголовок
- Підтвердити
- Скасувати

Поведінка: очищає фото й налаштування; віджет **лишається** на робочому столі.

---

### 5. About · `planned`

**App Shell:** title = About; back; banner внизу.

- Мінімалістично, тепло, у стилі додатку
- Типовий вміст: назва, короткий опис, версія, за потреби ліцензія / посилання
- Опційно: блок підтримки автора

---

### 6. Налаштування віджета · `done` (макет оновити під канон)

**App Shell:** title = налаштування віджета; back (in-app); banner внизу.  
Той самий макет для Edit і launcher Configure (`WidgetConfigureActivity`).

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
| Дії | Зберегти · Скасувати (у контенті / над банером, не під ним) |

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
| Ad banner | частина App Shell · pinned bottom · `partial` |

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
| Jul 2026 | Єдиний App Shell: header (back? / title / action?) + content + optional sticky CTA + ad banner pinned bottom; винятки — Launch і dialogs |
