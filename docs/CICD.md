# CI/CD

## Workflows

| Workflow | Trigger | Responsibility |
|---|---|---|
| `CI` | Pull requests, `main`, manual | Tests, lint, Detekt, formatting, coverage, debug assembly, and host compatibility |
| `Instrumentation Tests` | Weekly and manual | Compose/instrumentation tests on an Ubuntu Android emulator |
| `Release Bundle` | `v*` tags and manual | Creates a shrunk, signed Android App Bundle |
| Dependabot | Monthly | Gradle and GitHub Actions update pull requests |

The generated-project matrix runs the Bash initializer on `ubuntu-latest` and `macos-14`, runs the
PowerShell initializer on `windows-latest`, then builds and tests each renamed project. Android
emulator tests run on Ubuntu because Linux virtualization is the most predictable hosted setup.

## Release secrets

Configure these GitHub Actions repository secrets:

- `ANDROID_KEYSTORE_BASE64`: base64-encoded upload keystore
- `RELEASE_STORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`

Create the encoded value without adding a newline:

Ubuntu:

```bash
base64 -w 0 upload.keystore
```

macOS:

```bash
base64 < upload.keystore | tr -d '\n'
```

Windows PowerShell:

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("upload.keystore"))
```

The workflow restores the keystore only inside the ephemeral runner and passes passwords as Gradle
properties. Keystores and credential files are ignored by Git.

## Branch protection

Protect `main`, require pull requests, require the Ubuntu verification and three host checks, dismiss
stale approvals, and block force pushes. Keep release environments approval-gated when Play Store
publishing is introduced.

## Future deployment

The release workflow deliberately produces an AAB but does not upload it to Google Play. Add Play
publishing only after service-account ownership, environment approvals, track strategy, and rollback
responsibilities have been agreed.
