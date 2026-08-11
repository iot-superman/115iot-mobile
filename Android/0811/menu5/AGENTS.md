# AGENTS.md – Menu5 Android Project

## Project Overview
A minimal Android app (Kotlin, single-Activity) that renders two `NumberPicker` widgets (range 0–100, default 50) and a `TextView` that reflects the current selections in real time.

- **Package**: `com.example.menu5`
- **Min SDK**: 28 (Android 9) · **Target/Compile SDK**: 36 (Android 15, minorApiLevel 1)
- **AGP version**: 9.2.1 (uses the new `compileSdk { version = release(36) { ... } }` DSL)
- **No Fragments, no ViewModel, no Navigation component** – all logic lives in `MainActivity`.

## Key Files
| File | Purpose |
|------|---------|
| `app/src/main/java/com/example/menu5/MainActivity.kt` | Single entry-point; binds pickers, wires listeners, updates result |
| `app/src/main/res/layout/activity_main.xml` | `ConstraintLayout` with `textView_result`, `NumberPicker` (id `NumberPicker`), `numberPicker_1` |
| `app/src/main/res/values/strings.xml` | `result_format` string (`%1$d, %2$d`) drives `updateResult()` output |
| `app/build.gradle.kts` | App-level build config; all deps referenced via version catalog |
| `gradle/libs.versions.toml` | Single source of truth for all library/plugin versions |

## Architecture & Data Flow
```
NumberPicker (0-100) ──┐
                        ├─ setOnValueChangedListener → updateResult()
NumberPicker1 (0-100) ─┘         │
                                  ▼
                         textView_result.text = getString(R.string.result_format,
                                                  numberPicker.value, numberPicker1.value)
```
`updateResult()` is called once in `onCreate` (initial state) and on every value change.

## Build & Run
```powershell
# Debug APK
.\gradlew assembleDebug

# Install on connected device/emulator
.\gradlew installDebug

# Unit tests
.\gradlew test

# Instrumented tests (requires device/emulator)
.\gradlew connectedAndroidTest
```
Configuration cache is **enabled** (`gradle.properties`). If you change build scripts, run `.\gradlew --rerun-tasks` or delete `.gradle/configuration-cache` to bust the cache.

## Dependency Management
All versions live in `gradle/libs.versions.toml`. To add a library:
1. Add a `[versions]` entry.
2. Add a `[libraries]` entry referencing it.
3. Reference it in `app/build.gradle.kts` as `libs.<alias>`.

Current runtime deps: `core-ktx`, `appcompat`, `constraintlayout`, `activity-ktx`, `material`.

## UI Conventions
- Edge-to-edge display enabled via `enableEdgeToEdge()` + `ViewCompat.setOnApplyWindowInsetsListener` on `R.id.main`.
- Views are bound with `findViewById` (no View Binding or Compose).
- Layouts use `ConstraintLayout`; pickers are horizontally chained (`packed`) with `layout_marginEnd="32dp"` between them.
- Note the inconsistent ID casing: first picker is `@+id/NumberPicker` (capital N), second is `@+id/numberPicker_1` — match exactly when referencing in code or tests.

## Theme
- Day: `res/values/themes.xml` extends `Theme.Material3.DayNight.NoActionBar`
- Night: `res/values-night/themes.xml` (override)

