# Design Brief — Photo Widget (v1)

Документ для дизайну UI/UX у зовнішньому інструменті. Зафіксовано Jul 2026 на основі product discovery, competitor research і опитувальника.

**Статус:** ready for design  
**Версія:** 1.0  
**Пов’язані файли:** [product-description.md](product-description.md) · [decisions-log.md](decisions-log.md) · [competitor-research.md](competitor-research.md)

---

## 1. Рекомендований акцент (рішення)

### Обрано: **Emotion-first**

**Чому не stylist-first на v1:**

| Критерій | Emotion-first | Stylist-first |
|----------|---------------|---------------|
| Конкуренція в Play | Менше прямих конкурентів у **copy** (wallpaper-contrast майже відсутній у listings) | Перенаселена ніша: Easy (1M+), ZipoApps, 2026 — aesthetic, shapes, Widgetsmith-vibe |
| ASO / discovery | Підтримує lean Store-меседж: *emotion + wallpaper-contrast* | Змушує конкурувати ключами «aesthetic», «frames», «shapes» |
| Retention (KPI) | Persona «завжди на виду» — сильніший сигнал **widget survival 7/30d** | Persona «композиція столу» — частіше міняє setup, нижча лояльність до одного віджета |
| Продукт уже вміє stylist-фічі | Framing (cover/contain, alignment, форма) показуємо **на скрінах Store**, не робимо це головним character бренду | Потребує вищого visual polish, щоб не виглядати generic |

**Stylist не виключається:** контроли кадру/форми лишаються в UI; змінюються тон copy, empty state, приклади фото, іконографія. Окрема «stylist-копія» продукту — можливий експерiment пізніше, не зараз.

### Brand direction (working)

**Warm calm** — теплий, людяний, але не social (не Locket); спокійний інструмент (не Widgetsmith clone).

- Характер: довіра, простота, «важливе фото поруч зі шпалерами»
- Уникати: неон, overload shapes, «premium aesthetic kit», gamification
- Референс-вектор (не копіювати): Material Photo Widget (простота) + emotional clarity Pic Widget (memories), без їхнього slideshow/ads hell

Фінальна назва в Play — TBD на ASO; для дизайну working name: **Photo Widget**.

---

## 2. Ціль дизайну

Перетворити сирий Material 3 prototype на **полірований v1**, готовий до Play Release:

1. Зрозумілий **перший досвід** (empty state → pin → configure → фото на столі).
2. Читабельний **екран налаштувань** з прев’ю, що відповідає реальному віджету.
3. Стабільний layout з **рекламними зонами** (не у віджеті на home screen).
4. **Light + Dark** (system).
5. UI готовий до **багатомовності** (довгі рядки, RTL-ready де можливо).

---

## 3. Аудиторія і ключове повідомлення

**Primary persona:** «Завжди на виду» — партнер, дитина, pet, спогад; шпалери лишаються окремо.

**Secondary (на скрінах, не в hero-copy):** homescreen stylist — кілька віджетів, форма, кадр.

**One-liner для empty state / Store:**

> Окреме фото на робочому столі — без зміни шпалер.

**Не акцентувати в hero UI v1:** privacy (див. §8), slideshow, albums, premium.

---

## 4. Екрани та scope

### 4.1. Головний екран — список віджетів

**Призначення:** менеджер активних віджетів на home screen.

**Елементи (зберегти за змістом, покращити візуально):**

| Елемент | Поведінка |
|---------|-----------|
| Top app bar | Назва додатку + іконка **⚙** (або ⋮) → App settings |
| Primary CTA | «Додати на робочий стіл» → system pin flow (`requestPinAppWidget`) |
| Секція «Активні віджети» | Список усіх pinned instances |
| Рядок віджета | Мініатюра (52dp, форма як у config) + назва (або «Віджет #N») + розмір у клітинках (напр. `2x2`) |
| Дії на рядку | «Редагувати» · «Скинути» (reset config, віджет лишається на столі) |
| Empty state | Розширений блок замість одного рядка тексту (див. §5) |
| Ad zone | Banner внизу або під списком (fixed/sticky — на розсуд дизайнера, але не перекривати CTA) |

**Не додавати в v1:** swipe delete з лаунчера, drag reorder, folders, default settings screen.

### 4.2. Empty state (onboarding substitute)

**Рішення:** без окремих onboarding-екранів; **розширений empty state** на головному, коли `widgetIds.isEmpty()`.

**Має містити:**

1. Короткий headline (emotion): напр. «Тримайте важливе фото на виду».
2. Підзаголовок (wallpaper-contrast): «Окремо від шпалер — одне фото на віджет».
3. Візуальна підказка: ілюстрація або mock «шпалери + віджет з фото» (не обов’язково фотореалізм).
4. Numbered steps (3 кроки max):
   - Натисніть «Додати на робочий стіл»
   - Розмістіть віджет на home screen
   - Оберіть фото та збережіть
5. Primary CTA — та сама кнопка pin (дубль OK у empty state).

**Fallback copy:** якщо лаунcher не підтримує pin — коротке пояснення + «довгий тап на home screen → Widgets».

### 4.3. Екран налаштувань віджета

**Два входи, один макет:**

- In-app: Edit зі списку (є Cancel).
- Launcher: `APPWIDGET_CONFIGURE` після додавання віджета (Save; Cancel — system).

**Структура: single scroll** — прев’ю зверху, далі блоки контролів.

**Порядок блоків (рекомендований):**

1. **Preview** — квадрат 1:1, форма як у віджета (rect / rounded / circle), placeholder «Фото не обрано».
2. **Фото** — кнопка «Обрати фото» (Photo Picker).
3. **Назва** — optional, max 40 символів (лише для списку в додатку).
4. **Поворот** — ±90°, показ градусів (лише якщо фото обрано).
5. **Позиціонування** — сітка 3×3 (FilterChip / icon grid).
6. **Масштабування** — Cover | Contain (segmented).
7. **Форма** — Прямокутник | Закруглений | Коло (segmented).
8. **Радіус кутів** — Slider 0–48 dp (лише для «Закруглений»).
9. **Info note** — «Launcher може додатково округляти зовнішні кути віджета» (secondary container).
10. **Натискання на віджет** — Без дії | Відкрити додаток | Відкрити цей віджет (segmented, 3 колонки, можливі 2 рядки тексту).
11. **Save** — primary full width.
12. **Cancel** — outlined (лише in-app flow).
13. **Ad zone** — banner над або під Save (не ховати Save за рекламою).

**Після Save (product, не layout):** повноекранна реклама (interstitial) — закласти UX-pause / не блокувати feedback «збережено».

### 4.4. Діалог «Скинути віджет»

- Title + пояснення: очищає фото та settings, віджет лишається на столі.
- Confirm / Cancel.

### 4.5. App settings (app-level, не widget settings)

**Рішення:** невеликий розділ **на майбутнє**, не orphan-перемикач мови на головному.

| Елемент | v1 |
|---------|-----|
| Вхід | ⚙ або ⋮ у TopAppBar головного екрана |
| Екран | Короткий список «Settings» / «App settings» |
| **Language** | Є зараз: список мов **або** системний Android 13+ per-app language picker |
| Default | Мова системи |
| Майбутнє (рядки можна ховати, доки порожні) | About, Privacy, (опційно) ads / support |

**Не робити:** language chip / switcher на empty state чи в списку віджетів; мову всередині widget settings (photo/shape); великий Settings hub (акаунти, sync).

Це **окремо** від екрана налаштувань віджета (§4.3).

### 4.6. Системні / поза scope дизайну v1

- Немає: Default widget settings, Paywall, Account, cloud.
- About / Privacy — лише як майбутні пункти в App settings (можна не малювати контент у v1).
- Widget на home screen — **не** part of in-app UI design (RemoteViews); але прев’ю в додатку має **візуально збігатися**.

---

## 5. Реклама (layout constraints)

**Модель v1:** free + ads **тільки in-app**, never у віджеті на home screen.

| Placement | Тип | Примітка |
|-----------|-----|----------|
| Головний екран | Banner | Завжди, коли є контент або empty state |
| Екран налаштувань | Banner | Так |
| Після «Зберегти» | Interstitial (fullscreen) | Після успішного save |
| Configure з лаунчера | Banner + interstitial після save | Як на settings (користувач обрав «банер усюди») |

**Design rules:**

- Safe area для banner: типова висота adaptive banner ~50–90dp; не ламати scroll до Save.
- CTA «Додати на стіл» і «Зберегти» — завжди доступні без scroll через рекламу (реклама не перекриває).
- Візуально відділити ad container (subtle border / muted surface), не імітувати primary buttons.
- Interstitial — поза макетами екранів; достатньо нотатки в brief для dev.

---

## 6. Тема та візуальна система

### 6.1. Theme

- **Light + Dark**, follow system (`isSystemInDarkTheme`).
- **Dynamic Color (Material You):** optional v1.1; не блокер, якщо базова пара light/dark швидша.
- База: **Material 3** (Compose).

### 6.2. Поточний стан (не ціль)

- Default M3 purple primary `#6750A4`, light-only.
- Hardcoded grays у preview/thumbnail (`#E0E0E0`, `#2A2A2A`).
- Стандартна launcher icon (template).

### 6.3. Очікування від дизайнера

Deliverables:

- Color tokens (light/dark): primary, surface, on-surface, secondary container (info blocks), error (майбутнє).
- Typography scale: titleMedium для секцій, label для chips — M3-aligned.
- Corner radii: cards, buttons, preview frame.
- Icon set direction (outlined vs filled) — consistent.
- **App icon** concept (adaptive, foreground/background) — emotion-first, не generic gallery icon.
- Spacing grid: 8dp base; screen horizontal padding 16dp (можна 20dp для polish).

### 6.4. Preview / thumbnail

- Preview background: neutral surface variant, не pure `#E0E0E0` — має працювати в dark mode.
- Thumbnail у списку: 52dp min, shape відповідає widget shape.
- Contain mode: прозорі або surface кути (як на реальному віджеті).

---

## 7. Локалізація UI

**Рішення:** максимальна підтримка **основних мов на старті** (окремо від Store listing, де EN primary).

**Вибір мови в UI:** через **App settings → Language** (§4.5), не окремим перемикачем на головному. Перевага — Android 13+ system per-app locales API.

**Мови v1 (рекомендований мінімум для дизайну — перевірити layout на довгих рядках):**

| Tier | Мови |
|------|------|
| Must | English (default), Ukrainian |
| High priority | Spanish, Portuguese (Brazil), German, French, Italian, Polish, Turkish, Indonesian, Hindi |
| Medium | Japanese, Korean, Arabic (RTL), Vietnamese, Thai, Dutch, Russian |

**Design implications:**

- Кнопки: prefer не фіксовану width для текстових CTA; min height 48dp.
- Segmented controls (3× click behavior): допускати **2 рядки** label або icon+short label.
- Alignment grid: prefer **icons** або icon+tooltip замість довгих «Верх. лівий» — сильно різна довжина в DE/FR.
- RTL: mirror де потрібно (rotation icons, alignment grid).
- Не змішувати EN/UA в одному locale (зараз у strings є Cover/Contain англійською в UA файлі — виправити при i18n).

**Copy source of truth:** англійські ключі в `strings.xml`; переклади — окремі `values-xx`.

---

## 8. Privacy у UI

**Рішення v1:** **не акцентувати** в hero UI; достатньо **Store listing** one-liner («Photo Picker, no full storage access»).

**Опційно (low priority):** той самий стиль info block, що й «Launcher rounding note», один рядок під «Обрати фото» — лише якщо не перевантажує екран.

**Обґрунтування:** privacy leadership зайнятий Material; наш wedge — emotion + wallpaper-contrast, не privacy-first brand.

---

## 9. Стани та edge cases

| Стан | Очікувана UX |
|------|----------------|
| Немає віджетів | Empty state §4.2 |
| Pin не підтримується | Toast + підказка в empty state |
| Фото не обрано | Preview placeholder; rotation/alignment hidden |
| Save без фото | Дозволити (placeholder widget) — не блокувати |
| Loading photo in preview | Skeleton або subtle progress у preview box |
| Photo load error | Inline message в preview + retry через «Обрати фото» |
| Reset widget | Dialog §4.4 |
| Багато віджетів (5+) | Scroll list; banner sticky bottom OK |

**Accessibility (v1 baseline):**

- Touch targets ≥ 48dp.
- Content descriptions для icon-only controls.
- Contrast WCAG AA для text on surface.

---

## 10. Конкурентний контекст (для дизайнера)

**Не копіювати вигляд:** Photo Widget Easy, ZipoApps, 2026 (aesthetic overload).

**Відрізнитися:**

- Менше visual noise, менше shapes/frames decoration.
- Чіткий preview + framing controls на скрінах Store.
- Calm ad placement (не aggressive popups кожну дію — лише post-save interstitial).

Деталі: [competitor-research.md](competitor-research.md).

---

## 11. Deliverables checklist (дизайн-інструмент)

- [ ] Головний екран — список + empty state + ad zone (+ gear у app bar)
- [ ] Головний екран — 2+ віджети в списку
- [ ] App settings — Language (+ місце під майбутні пункти)
- [ ] Налаштування віджета — no photo / with photo
- [ ] Налаштування віджета — rounded rect + slider visible
- [ ] Dark mode variants (key screens)
- [ ] Dialog reset
- [ ] App icon (adaptive)
- [ ] Design tokens (color, type, spacing)
- [ ] Optional: one Store screenshot mock (emotion + widget on homescreen)

---

## 12. Поточні скріни додатку — чи прикріплювати?

**Коротка відповідь: не обов’язково для візуального референсу; корисно опційно як wireframe.**

| Прикріплювати? | Навіщо |
|----------------|--------|
| **Ні (достатньо цього brief)** | Поточний UI сирий, default M3 — **не** visual target |
| **Так (опційно)** | Швидко показати **inventory контролів** і **навігацію** (2 екрани + dialog), щоб дизайнер не пропустив alignment grid / 3 click options |

**Рекомендація:** починати дизайн **з цього документа**. Якщо в інструменті зручно — додати 2–3 скріни **без polish** лише з підписом «structure reference, not visual direction».

---

## 13. Поза scope v1 (не малювати)

- Slideshow, albums, multiple photos per widget
- Lock screen widget
- Cloud sync, accounts
- Default widget settings screen
- Freemium paywall
- In-widget ads
- Custom gallery (лише system Photo Picker)
- Orphan language switcher на головному / у empty state

---

## Changelog

| Дата | Зміна |
|------|-------|
| Jul 2026 | v1.0 — опитувальник + recommendation emotion-first |
| Jul 2026 | v1.1 — App settings + Language (не orphan switcher) |
