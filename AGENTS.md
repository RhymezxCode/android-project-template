# Starter engineering guide

- Use Kotlin, Jetpack Compose, Material 3, Hilt, Coroutines, Flow, and Navigation Compose.
- Keep module dependencies flowing inward: UI depends on domain contracts; data implements them.
- Keep business logic out of Activities and Composables. Expose ViewModel state with `StateFlow`.
- Use `collectAsStateWithLifecycle` and model transient UI work as one-shot effects.
- Add focused tests for ViewModels, use cases, repositories, mappers, and failure paths.
- Use the version catalog and convention plugins instead of repeating Gradle configuration.
- Never commit credentials, signing files, API keys, tokens, or production service configuration.
- Run `./scripts/verify.sh` (or `scripts/verify.ps1`) before review.
