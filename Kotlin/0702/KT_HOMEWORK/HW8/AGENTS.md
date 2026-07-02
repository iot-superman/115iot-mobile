# AGENTS.md

## Project Snapshot
- This is a small IntelliJ Kotlin homework project with two standalone console programs in `src/`.
- There is no Gradle/Maven build file; project metadata is in `HW8.iml`.
- The module depends on `KotlinJavaRuntime` (`HW8.iml`).

## Codebase Layout
- `src/EX1.kt`: student-score exercise (`Student`, `ScoreManager`, `main`).
- `src/EX2.kt`: library-book exercise (`Book`, `Library`, `main`).
- `HW8.iml`: module config (`src` marked as source root).

## Architecture and Data Flow
- Each file is an independent entry point with its own `main()`; there is no shared package/module layer.
- Pattern used in both exercises:
  - `data class` defines domain object (`Student`, `Book`).
  - Manager class wraps a `mutableListOf<...>()` for in-memory state.
  - `main()` seeds hardcoded sample data, calls manager methods, prints results.
- Data never leaves process memory (no DB/file/network integration).

## Project-Specific Coding Patterns
- Keep examples simple and local to each file (no cross-file abstraction).
- Query logic is Kotlin-idiomatic collection processing (example: `filter { it.author == author }` in `src/EX2.kt`).
- Console output includes Traditional Chinese text; preserve UTF-8 and existing wording style.
- Methods exposed by manager classes are small and task-focused:
  - `addStudent`, `listPass`, `printInfo` in `src/EX1.kt`.
  - `addBook`, `findByAuthor`, `printInfo` in `src/EX2.kt`.

## Build / Run Workflow
- Preferred: run each file directly in IntelliJ (each has a `main()`).
- CLI fallback (if `kotlinc` is available):

```powershell
kotlinc src/EX1.kt -include-runtime -d EX1.jar
java -jar EX1.jar
kotlinc src/EX2.kt -include-runtime -d EX2.jar
java -jar EX2.jar
```

- No automated tests are present; verification is manual via console output.

## Agent Guardrails for This Repo
- Treat `EX1.kt` and `EX2.kt` as separate exercises unless asked to refactor.
- Do not assume Gradle tasks (`gradle test`, `./gradlew run`) exist.
- When adding logic, follow existing style: compact classes, direct list operations, printed demo in `main()`.
- If adding files, keep structure minimal and compatible with IntelliJ module layout.

## Existing AI/Project Instructions Scan
- Checked for guidance files (`.github/copilot-instructions.md`, `AGENT.md`, `AGENTS.md`, `CLAUDE.md`, `.cursorrules`, `.windsurfrules`, `.clinerules`, and related rule directories, plus `README.md`).
- No existing instruction/rules files were found at generation time.

