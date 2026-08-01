# Template publishing checklist

- Mark the GitHub repository as a **Template repository** in Settings.
- Keep `com.example.modularapp` and `ModularAndroidTemplate` unchanged on the template's `main` branch.
- Run CI on Ubuntu, macOS, and Windows before tagging a template release.
- Run both initializer scripts in throwaway copies and build the generated projects.
- Never add a real keystore, service configuration, API token, or organization-specific credential.
- Review dependency update pull requests monthly and publish changes in release notes.
- Follow [`docs/GITHUB_TEMPLATE_PUBLISHING.md`](docs/GITHUB_TEMPLATE_PUBLISHING.md) for the first push and GitHub settings.
- Replace the repository and article URL placeholders in the Medium and social copy before publishing.
- Link [`docs/MEDIUM_ARTICLE.md`](docs/MEDIUM_ARTICLE.md) when publishing the companion tutorial.
