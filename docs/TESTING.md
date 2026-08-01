# Testing strategy

The test pyramid keeps most feedback fast and reserves device tests for Android integration and UI
behavior.

| Layer | Location | Purpose |
|---|---|---|
| Pure JVM | `core/*/src/test` | Models, use cases, repositories, mappers, and failure handling |
| ViewModel JVM | `feature/*/src/test` | State transitions, coroutine behavior, and one-shot effects |
| Compose/device | `feature/*/src/androidTest` | Semantics, interaction, rendering, and Android integration |
| App/device | `app/src/androidTest` | Navigation and application-level integration when added |

Shared fakes and `MainDispatcherRule` live in `:core:testing`; production code must never depend on
that module.

## Commands

Fast focused checks:

```bash
./gradlew :core:data:testDebugUnitTest
./gradlew :feature:home:testDebugUnitTest
```

Complete local verification:

```bash
./scripts/verify.sh
```

Windows:

```powershell
.\scripts\verify.ps1
```

Connected tests with a running emulator or device:

```bash
./gradlew connectedCheck
```

Coverage:

```bash
./gradlew koverHtmlReport koverXmlReport
```

Open `build/reports/kover/html/index.html`. Generated Compose, DI, app entry points, and design-only
classes are excluded so coverage focuses on behavior. Coverage should not replace assertion quality.
The `koverVerify` quality gate requires at least 70% aggregate line coverage.

## Expectations for changes

- ViewModels: loading/success/error plus each one-shot effect.
- Repositories: delegation, mapping, empty data, and source failures.
- Mappers: boundary values, missing optional fields, and malformed inputs.
- UI: meaningful text, touch behavior, content descriptions, and state-specific rendering.
