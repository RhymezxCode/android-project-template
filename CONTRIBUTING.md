# Contributing

1. Create a short-lived branch from `main`.
2. Keep dependency direction consistent with `docs/ARCHITECTURE.md`.
3. Add focused tests for changed behavior and failure paths.
4. Run `./scripts/verify.sh` or `.\scripts\verify.ps1`.
5. Open a pull request describing the affected modules, dependency changes, verification, and UI
   screenshots when presentation changes.

Use conventional commit prefixes such as `feat:`, `fix:`, `refactor:`, `test:`, `docs:`, and `build:`.
Do not commit generated build output, local SDK paths, credentials, or signing material.
