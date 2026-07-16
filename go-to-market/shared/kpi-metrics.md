# KPI та proxy-метрики

Зафіксовано Jul 2026. **Downloads** лишаються базовою метрикою; нижче — додаткові сигнали «чи продукт живий».

## Primary (v1)

| Метрика | Що вимірює | Примітка |
|---------|------------|----------|
| **Installs** | Завантаження з Play | Базова метрика успіху на старті |
| **Store conversion** | Відвідування listing → install | Play Console; сигнал ASO/упаковки |

## Retention / engagement (proxy)

| Метрика | Що вимірює | Примітка |
|---------|------------|----------|
| **Widget survival** | Віджет лишився на столі через 7 / 30 днів | Потребує аналітики або косвенного proxy (напр. повторне відкриття без зміни конфігу). Ідеальний сигнал для photo widget |
| **Return to edit** | Повторне відкриття додатку D7 / D30 | Редагування = зацікавленість; не плутати з ads-driven opens |
| **Rating + reviews** | Середній рейтинг, кількість відгуків | Secondary; якісний фідбек для ASO і позиціонування |

## Не в пріоритеті на v1

- DAU/MAU як окрема ціль (віджет часто «поставив і забув» — це OK)
- ≥2 віджети на користувача — nice-to-have, не KPI на старті

## Інструменти (вирішити на 02 Play Release)

- Play Console: installs, store listing conversion, crashes, ratings
- In-app analytics (якщо додавати): Firebase / Play Vitals / мінімальний custom event для `app_open`, `widget_configured`, `widget_edited`

## Обмеження ads-моделі

Реклама в додатку може піднімати opens без реальної цінності — при інтерпретації **return to edit** дивитись разом із **widget survival** і відгуками, не лише на sessions.
