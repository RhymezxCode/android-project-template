# From Empty Project to Reusable Android Foundation

Starting an Android product is easy; keeping its fifth and fiftieth feature maintainable is the hard
part. This foundation treats architecture, build logic, tests, and CI as one system instead of four
later clean-up projects.

## 1. Begin with dependency direction

The app is a composition root. Feature modules own presentation. Domain owns stable contracts. Data
implements those contracts. Models remain framework-free. That direction lets a team replace an API,
database, or fake source without pulling implementation details through every screen.

```text
UI → ViewModel → use case → repository contract ← repository implementation → data source
```

This is MVVM with an explicit domain and data boundary, not a folder called `mvvm` containing every
class in the application.

## 2. Make the happy path executable

An architecture diagram is only a promise. The sample feature compiles and exercises the whole path:
a lifecycle-aware Compose route collects immutable state, sends typed actions, displays transient
effects through a snackbar, and relies on constructor-injected use cases. Tests prove initial and
failure behavior.

## 3. Centralize Gradle decisions

Convention plugins configure Java 17, SDK levels, Compose BOM alignment, Hilt/KSP, and common tests.
The version catalog is the dependency inventory. A feature build file therefore explains what the
feature depends on instead of repeating boilerplate.

## 4. Treat tests as architecture clients

Framework-free modules use JVM tests. ViewModels use a shared main-dispatcher rule and fake repository.
Repositories test data-source coordination. Compose instrumentation checks semantics and interaction.
Kover reports behavioral coverage while generated UI and DI glue stay outside the denominator.

## 5. Make every supported host real

The repository includes both Gradle wrappers, LF/CRLF rules, Bash and PowerShell verification scripts,
and CI jobs for Ubuntu, macOS, and Windows. Emulator tests remain on Ubuntu, where hosted virtualization
is the best fit. Cross-platform support is a continuously checked property, not a README claim.

## 6. Keep delivery secure by default

The starter has no production service configuration. Backups remain disabled until data is classified.
Signing material enters only through CI secrets, and the release job produces an artifact without
automatically publishing it. Deployment permissions can be added deliberately when the product is ready.

## Closing checklist

- Are module boundaries visible in Gradle dependencies?
- Can a feature be tested without an emulator?
- Does CI execute the same commands developers run?
- Do all supported operating systems use checked-in wrappers and scripts?
- Can the release build run without committing a credential?
- Does the sample code demonstrate failure handling, not only success?

When these answers are yes on day one, feature work starts on solid ground.
