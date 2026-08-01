# Build Android Projects Faster with a Production-Ready Modular Template

> A practical Android foundation with modular MVVM, Jetpack Compose, convention plugins, layered tests, and CI that runs on Ubuntu, macOS, and Windows.

**Suggested Medium topics:** Android, Kotlin, Jetpack Compose, Software Architecture, CI/CD

**Canonical project link:** Replace `<TEMPLATE_REPOSITORY_URL>` before publishing.

**Cover image idea:** A clean diagram showing `Compose → ViewModel → Use Case → Repository → Data Source`, surrounded by Ubuntu, macOS, Windows, Android, Kotlin, and GitHub Actions symbols.

---

Starting a new Android app takes minutes. Building a foundation that still feels clear after the fifth feature, the third developer, and the first production incident takes much longer.

Android Studio can generate an app module and a screen, but a real product also needs architectural boundaries, repeatable Gradle configuration, dependency injection, tests, code quality checks, release signing, and continuous integration. When every project invents those pieces again, teams spend their first sprint rebuilding infrastructure instead of validating the product.

I created **AndroidProjectTemplate** to make that work reusable.

It is not a sample app disguised as an architecture. It is a working starting point that demonstrates a complete feature path, enforces useful module boundaries, and verifies that a renamed project still builds on Linux, macOS, and Windows.

Repository: **<TEMPLATE_REPOSITORY_URL>**

## What the template gives you

The project starts with an intentionally small set of modules:

```text
app
 ├── feature:home
 └── core:data ──implements──> core:domain ──> core:model
      feature:home ───────────> core:domain
      feature:home ───────────> core:designsystem

core:testing ──> shared rules and fakes used only by tests
build-logic  ──> reusable Gradle convention plugins
```

Each module has one main reason to change:

| Module | Responsibility |
|---|---|
| `:app` | Application entry point, dependency graph assembly, and top-level navigation |
| `:feature:home` | Compose UI, ViewModel, state, actions, effects, and feature tests |
| `:core:model` | Framework-free shared models |
| `:core:domain` | Repository contracts and use cases |
| `:core:data` | Repository implementations and data sources |
| `:core:designsystem` | Material 3 theme and shared UI foundations |
| `:core:testing` | Coroutine rules, fakes, and shared test helpers |
| `build-logic` | Android, Kotlin, Compose, Hilt, feature, and JVM conventions |

The goal is not to maximize the number of modules. The goal is to make dependency direction visible and keep changes local as the product grows.

## MVVM with real boundaries

“MVVM” can easily become three folders with most of the business logic still inside an Activity or Composable. This template treats MVVM as a flow of responsibilities:

```text
Compose UI
    ↓ typed actions
ViewModel
    ↓
Use case
    ↓
Repository contract
    ↑ implemented by
Repository implementation
    ↓
Data source
```

The UI renders immutable state and sends typed actions. The ViewModel owns presentation behavior, invokes use cases, and exposes state through `StateFlow`. Long-lived state travels through `Flow`; transient work such as snackbars or navigation is modeled as a one-shot effect.

The route collects state with `collectAsStateWithLifecycle`, keeping lifecycle behavior explicit. Activities remain hosts for Compose and top-level navigation instead of becoming business-logic containers.

This separation creates useful replacement points. A database, network client, or in-memory fake can change behind a domain repository contract without leaking implementation details into the screen.

The included greeting feature is deliberately simple, but it executes the entire path:

```text
Compose → HomeViewModel → use cases → GreetingRepository → local data source
```

An architecture diagram is only a promise. A compiling feature and tests make it executable.

## Build logic is part of the architecture

Large Android projects often accumulate subtly different configuration across modules: one compiles with a different Java version, another forgets Compose metrics, and every feature repeats the same dependency setup.

AndroidProjectTemplate puts shared decisions in included-build convention plugins under `build-logic`:

- `starter.android.application`
- `starter.android.library`
- `starter.android.compose`
- `starter.android.hilt`
- `starter.android.feature`
- `starter.jvm.library`

Those plugins own SDK levels, Java and Kotlin targets, Compose configuration, Hilt/KSP setup, and common test dependencies. Third-party versions live in `gradle/libs.versions.toml`.

As a result, a feature build file mostly describes identity and intentional dependency edges. That makes reviews easier: when a module adds a dependency, the change is visible instead of buried among repeated Gradle boilerplate.

The stack is intentionally mainstream:

- Kotlin
- Jetpack Compose and Material 3
- Navigation Compose
- Hilt with KSP
- Coroutines, `Flow`, and `StateFlow`
- Gradle version catalogs and convention plugins
- JUnit and Compose instrumentation tests
- Detekt and ktlint
- Kover coverage reporting and verification

## Tests are architecture clients

The test strategy favors fast feedback and reserves devices for behavior that genuinely needs Android:

| Layer | Purpose |
|---|---|
| Pure JVM tests | Use cases, repositories, models, mapping, and failure behavior |
| ViewModel JVM tests | State transitions, coroutines, and one-shot effects |
| Compose/device tests | Semantics, interaction, rendering, and Android integration |
| App/device tests | Top-level navigation and integration as the product grows |

The `:core:testing` module contains reusable fakes and a main-dispatcher rule. Production modules never depend on it.

The template also includes an aggregate Kover line-coverage gate of 70%. Generated Compose and dependency-injection glue is excluded so the number focuses on behavior. Coverage is not a substitute for good assertions, but a reasonable gate prevents tested code from quietly disappearing as the project evolves.

Run focused tests while developing:

```bash
./gradlew :core:data:testDebugUnitTest
./gradlew :feature:home:testDebugUnitTest
```

Run the complete local verification suite on Ubuntu or macOS:

```bash
./scripts/verify.sh
```

On Windows PowerShell:

```powershell
.\scripts\verify.ps1
```

For device or emulator tests:

```bash
./gradlew connectedCheck
```

For coverage reports:

```bash
./gradlew koverHtmlReport koverXmlReport koverVerify
```

## One template, three desktop operating systems

Cross-platform support often means “the Gradle build is probably portable.” This project tests more than that.

It checks in both Gradle wrappers, uses repository line-ending rules, provides Bash and PowerShell scripts, and runs generated-project checks in GitHub Actions on:

- `ubuntu-latest`
- `macos-14`
- `windows-latest`

Each matrix job starts with the generic template, runs the relevant initializer, and then builds and tests the renamed output. This catches failures in package replacement, source-directory movement, shell syntax, PowerShell behavior, and Gradle configuration.

Instrumentation tests run on an Ubuntu Android emulator, where hosted virtualization is a predictable fit. A separate workflow can build a signed, shrunk Android App Bundle from repository secrets.

CI is most useful when it exercises the same entry points developers use locally. Here, `verify.sh` and `verify.ps1` are not documentation-only wrappers; they are part of the development and CI contract.

## Turn the template into your app

Once the repository is marked as a GitHub template, select **Use this template**, choose **Create a new repository**, and clone the generated repository. GitHub creates an independent repository rather than a fork with inherited history. See GitHub's [repository-template documentation](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template) for the current flow.

Then run the initializer once.

Ubuntu or macOS:

```bash
./scripts/init-project.sh "My Application" com.example.myapplication
```

Windows PowerShell:

```powershell
.\scripts\init-project.ps1 -ProjectName "My Application" -PackageName "com.example.myapplication"
```

The initializer updates the project name, display name, namespace, application ID, Kotlin packages and source paths, application class, theme class, and artifact names.

Review the generated changes and make them the first product-specific commit:

```bash
git status
git add .
git commit -m "chore: initialize My Application"
```

Now the generic starter identity is gone, while the architecture, build logic, tests, and delivery workflows remain.

## CI/CD and secure release defaults

The template ships with three GitHub Actions workflows:

1. **CI** runs unit tests, lint, Detekt, formatting checks, coverage verification, debug assembly, and generated-project checks across all three desktop operating systems.
2. **Instrumentation Tests** runs weekly or on demand using an Android emulator.
3. **Release Bundle** creates a signed, minified AAB from a `v*` tag or a manual dispatch.

Signing material is never committed. The release workflow expects these GitHub Actions secrets:

- `ANDROID_KEYSTORE_BASE64`
- `RELEASE_STORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`

The workflow restores the keystore only on the ephemeral runner and uploads the generated bundle as an artifact. It intentionally does not publish to Google Play. Store deployment should be added only after the team defines account ownership, approval environments, release tracks, and rollback responsibility.

That is an important template principle: automate the safe, repeatable baseline, but do not hide product-specific policy inside generic starter code.

## Security starts with what is absent

The project contains no production credentials or service configuration. Machine-specific `local.properties`, keystores, and common credential files are ignored. Android backup is disabled until the application has a deliberate data-classification and backup policy.

Before publishing a project created from the template, still review staged files carefully:

```bash
git status
git diff --cached
```

Never commit API tokens, signing keys, passwords, or real service configuration. A template makes good defaults reusable; it does not remove the need for product-specific threat modeling.

## Modularity without ceremony

There is a real trade-off here. Modules add Gradle configuration, dependency decisions, and navigation boundaries. For a one-screen prototype that will be deleted next week, this foundation may be more than you need.

For a product expected to grow, the initial structure pays back by making ownership and dependency direction explicit. The included modules are a starting point, not a quota. Add a feature module when it represents a coherent product capability. Split core modules when their responsibilities genuinely diverge. Avoid creating a new module for every class or implementation detail.

The most important rule is simple: stable contracts point inward, replaceable implementations stay outward, and the UI does not know where data comes from.

## What I would add for a specific product

A reusable template should stop before it guesses the product. Depending on the app, the next additions might include:

- Retrofit or Ktor for networking
- Room or DataStore for persistence
- A dedicated navigation module for a larger graph
- Baseline Profiles and Macrobenchmark
- Screenshot testing
- Product flavors and environment configuration
- Play Console publishing with approval gates
- Observability, crash reporting, and privacy-aware analytics

Those choices belong to the product. The template provides the seams where they can be integrated cleanly.

## Closing thought

The best Android template is not the one with the most libraries. It is the one that shortens the path to the first feature without creating a maintenance trap for the fiftieth.

AndroidProjectTemplate gives you a tested starting point for modular MVVM, Compose, Gradle build logic, quality gates, and cross-platform CI/CD. Use it, challenge its boundaries, and adapt it to the constraints of your team.

If it saves you setup time—or you see a way to improve it—open the repository, create a project from the template, and share what you build:

**<TEMPLATE_REPOSITORY_URL>**

---

**Pre-publication checklist:** Replace both `<TEMPLATE_REPOSITORY_URL>` placeholders, add a cover image and alt text, verify every command against the tagged template version, and remove this checklist.
