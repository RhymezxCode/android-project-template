# Architecture

## Design goals

The foundation optimizes for independent features, testable business behavior, consistent builds,
and the ability to replace storage or networking without rewriting UI.

## Dependency direction

Dependencies point toward stable contracts:

```text
Android entry points (:app)
          ↓
Feature presentation (:feature:*)
          ↓
Domain contracts and use cases (:core:domain)
          ↓
Framework-free models (:core:model)

Data implementations (:core:data) ──implement──> domain contracts
Design system (:core:designsystem) ──used by──> feature presentation
```

Feature modules must not depend directly on implementation classes in `:core:data`. The `:app`
module includes data modules so Hilt can assemble their bindings at the application boundary.

## UI and state flow

1. A Composable sends a typed action to its ViewModel.
2. The ViewModel invokes a use case and updates an immutable `StateFlow` state model.
3. Use cases depend on repository interfaces in `:core:domain`.
4. A repository implementation coordinates one or more data sources.
5. Persistent state returns through `Flow`; transient messages and navigation use one-shot effects.
6. The route collects state with `collectAsStateWithLifecycle`.

Activities only host the Compose tree and top-level navigation. Composables never perform network,
database, file, or heavy CPU work.

## Adding a feature

1. Add `include(":feature:<name>")` to `settings.gradle.kts`.
2. Apply `starter.android.feature` and declare a unique namespace.
3. Depend only on the core contracts and UI modules the feature actually needs.
4. Define an immutable state model, typed actions, and one-shot effects.
5. Add ViewModel success/failure tests and at least one accessibility-oriented UI test.
6. Add the feature destination at the `:app` navigation boundary.

## Build logic

Convention plugins own SDK levels, Java/Kotlin targets, Compose BOM alignment, Hilt/KSP, and common
test dependencies. Module build files should contain only their identity and intentional dependency
edges. Third-party versions belong in `gradle/libs.versions.toml`.

## Security boundaries

- Contracts validate data crossing trust boundaries.
- Credentials arrive through local untracked files or CI secrets.
- Sensitive user data must not be logged.
- Android backup is disabled until a reviewed data classification and backup policy exists.
- Release builds use shrinking and require CI-provided signing properties.
