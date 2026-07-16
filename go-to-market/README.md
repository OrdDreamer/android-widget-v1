# Go-to-Market — Photo Widget

Документація та процес підготовки до публікації в Google Play і органічного маркетингу.

Не запускай усі промпти одночасно. Пройди етапи послідовно: відповіді попереднього етапу підставляй у наступний.

Повний текст промптів: [prompts.md](prompts.md).

## Порядок

1. [Product Discovery](01-product-discovery/) — позиціонування, аудиторія, USP
2. [Google Play Release](02-play-release/) — технічна й Console-підготовка
3. [ASO & Branding](03-aso-branding/) — сторінка магазину
4. [Growth Marketing](04-growth-marketing/) — перші користувачі без платного бюджету
5. [Final Audit](05-final-audit/) — фінальна перевірка перед релізом

## Статус

| Етап | Статус | Артефакти |
|------|--------|-----------|
| 01 Product Discovery | done | [session-notes](01-product-discovery/session-notes.md), [output](01-product-discovery/output.md) |
| — конкурентний зріз | done | [shared/competitor-research.md](shared/competitor-research.md) |
| 02 Play Release | pending | — |
| 03 ASO & Branding | pending | — |
| 04 Growth Marketing | pending | — |
| 05 Final Audit | pending | — |

## Спільний контекст

Підставляй у промпти замість `[ОПИС]`:

- [shared/product-description.md](shared/product-description.md) — канонічний опис продукту
- [shared/decisions-log.md](shared/decisions-log.md) — закриті й відкриті рішення
- [shared/kpi-metrics.md](shared/kpi-metrics.md) — метрики успіху
- [shared/competitor-research.md](shared/competitor-research.md) — зріз Play, keywords, Store-меседж
- вивід попередніх етапів (напр. `01-product-discovery/output.md` для ASO)

## Як працювати з етапом

1. Відкрий `README.md` відповідної папки.
2. Скопіюй потрібний промпт з [prompts.md](prompts.md).
3. Підстав опис і контекст з `shared/` (+ попередні output).
4. Збережи сесію (уточнення) і фінальний вивід у папці етапу.
5. Онови таблицю статусів у цьому README.
