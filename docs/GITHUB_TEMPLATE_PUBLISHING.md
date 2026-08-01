# Publish AndroidProjectTemplate as a GitHub Template

This guide takes the existing local repository from its current uncommitted state to a public GitHub template. It also explains how another developer can create a new Android project from it.

Official references:

- [Adding locally hosted code to GitHub](https://docs.github.com/en/migrations/importing-source-code/using-the-command-line-to-import-source-code/adding-locally-hosted-code-to-github)
- [Creating a template repository](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-template-repository)
- [Creating a repository from a template](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template)

## Before you begin

You need:

- A GitHub account with permission to create a repository under the intended user or organization.
- Git installed and authenticated with GitHub over HTTPS or SSH.
- JDK 17 and Android SDK 36 for local verification.
- A repository name. This guide uses `android-project-template`.

The local repository already uses `main` and is already initialized with Git. Do not run `git init` again.

## 1. Verify the template locally

Open Terminal on Ubuntu/macOS, Git Bash on Windows, or PowerShell. Change to the repository root.

On this machine:

```bash
cd /home/rhymezxcode/StudioProjects/personal/AndroidProjectTemplate
```

On another machine:

```bash
cd path/to/AndroidProjectTemplate
```

Run the full verification suite.

Ubuntu or macOS:

```bash
./scripts/verify.sh
```

Windows PowerShell:

```powershell
.\scripts\verify.ps1
```

Then inspect the repository:

```bash
git status --short
git branch --show-current
git remote -v
```

Expected result:

- The branch is `main`.
- The project files are currently untracked before the first commit.
- No remote is configured yet.

## 2. Check for private or generated files

Review what will be published:

```bash
git status --short
```

Do not add any real credentials, `local.properties`, keystore, signing password, API token, or production service configuration. Confirm ignored files with:

```bash
git status --ignored --short
```

The template should keep its generic identity on `main`:

- Project name: `ModularAndroidTemplate`
- Package/namespace: `com.example.modularapp`

Those values are inputs to the initializer and should not be replaced with a real product identity in the template repository.

## 3. Replace the publication placeholders

After choosing the final GitHub owner and repository name, replace `<TEMPLATE_REPOSITORY_URL>` in:

- `docs/MEDIUM_ARTICLE.md`
- `docs/SOCIAL_POSTS.md`

For example:

```text
https://github.com/Aiegs-Global/android-project-template
```

Keep the Medium article's final pre-publication checklist until the link and media have been reviewed. Remove that checklist only from the copy you publish to Medium.

## 4. Create an empty repository on GitHub

1. Sign in to GitHub.
2. Select the **+** menu in the upper-right corner, then **New repository**.
3. Choose the owner: your account or organization.
4. Enter `android-project-template` as the repository name.
5. Add a clear description, for example:

   ```text
   Production-ready modular Android starter with Compose, MVVM, convention plugins, tests, and cross-platform CI/CD.
   ```

6. Choose **Public** if everyone should be able to find and use it.
7. Do **not** initialize it with a README, `.gitignore`, or license. Those files already exist locally; GitHub recommends leaving these options empty when pushing an existing repository.
8. Select **Create repository**.
9. Leave the Quick Setup page open; it displays the HTTPS and SSH remote URLs.

## 5. Create the first local commit

From the local template root:

```bash
git add .
git status
git diff --cached --stat
git commit -m "feat: publish modular Android project template"
```

Before committing, read the staged file list shown by `git status`. If anything is private or machine-specific, unstage it and add an appropriate ignore rule before continuing.

## 6. Connect the GitHub remote

Choose either HTTPS or SSH.

HTTPS:

```bash
git remote add origin https://github.com/<OWNER>/android-project-template.git
```

SSH:

```bash
git remote add origin git@github.com:<OWNER>/android-project-template.git
```

Replace `<OWNER>` with the GitHub user or organization. Verify the result:

```bash
git remote -v
```

If `origin` already exists, update it instead of adding a second remote:

```bash
git remote set-url origin https://github.com/<OWNER>/android-project-template.git
git remote -v
```

## 7. Push `main`

```bash
git push -u origin main
```

The `-u` option records `origin/main` as the upstream, so future pushes can use `git push`.

Refresh the GitHub repository page and confirm the README, source modules, `build-logic`, scripts, documentation, and `.github/workflows` directory are visible.

### Optional GitHub CLI route

If GitHub CLI is installed and authenticated, steps 4, 6, and 7 can be combined after the first local commit:

```bash
gh repo create <OWNER>/android-project-template \
  --public \
  --source=. \
  --remote=origin \
  --push
```

Use either the browser/Git route or this CLI route, not both.

## 8. Enable the template feature

You need admin permission on the GitHub repository.

1. Open the repository on GitHub.
2. Select **Settings** under the repository name. If the tab is hidden, open the repository dropdown and select **Settings**.
3. In **General**, locate the repository options near the top of the page.
4. Select **Template repository**.
5. Return to the repository's **Code** page.
6. Confirm the **Use this template** button appears above the file list.

GitHub templates copy the default branch's directory structure and files into a new repository. Users can optionally include every branch, but the template should keep its reusable baseline on `main`.

GitHub template repositories cannot include files stored with Git LFS. This project does not rely on Git LFS.

## 9. Complete the repository presentation

On the repository page, edit the **About** section and add:

- Description: `Production-ready modular Android starter with Compose, MVVM, convention plugins, tests, and cross-platform CI/CD.`
- Website: the Medium article URL after publishing.
- Topics: `android`, `kotlin`, `jetpack-compose`, `mvvm`, `modularization`, `gradle`, `github-actions`, `android-template`, `cicd`.

Also consider:

- Adding a social preview image under **Settings → General → Social preview**.
- Enabling Issues for bug reports and enhancement requests.
- Enabling Discussions if you want a community Q&A space.
- Confirming Dependabot and security features under **Settings → Security**.

## 10. Confirm GitHub Actions can run

Open the **Actions** tab. The initial push to `main` should start the CI workflow if Actions is allowed by the account or organization policy.

The important checks are:

- `Verify template baseline`
- `Generate on Ubuntu`
- `Generate on macOS`
- `Generate on Windows`

The weekly instrumentation workflow can also be started manually from **Actions → Instrumentation Tests → Run workflow**.

Do not create a `v*` tag yet unless the four Android signing secrets have been configured. Such a tag triggers the release workflow.

## 11. Protect `main`

After the initial CI run creates check names, configure a branch ruleset:

1. Open **Settings → Rules → Rulesets**.
2. Select **New ruleset → New branch ruleset**.
3. Name it `Protect main` and target the default branch.
4. Require a pull request before merging.
5. Require status checks to pass and select the baseline plus three generated-project checks.
6. Block force pushes and branch deletion.
7. Enable the ruleset.

The exact rules available depend on the repository visibility and GitHub plan. At minimum, keep direct force pushes and deletion blocked for `main`.

## 12. Test the public template flow

Test it the same way a reader will use it:

1. Open the template repository's **Code** page.
2. Select **Use this template → Create a new repository**.
3. Choose an owner and a temporary repository name such as `android-template-smoke-test`.
4. Do not select **Include all branches** unless you intentionally want non-default branches copied.
5. Select **Create repository from template**.
6. Clone the new repository.
7. Run exactly one initializer.

Ubuntu or macOS:

```bash
./scripts/init-project.sh "Template Smoke Test" com.example.templatesmoketest
./scripts/verify.sh
```

Windows PowerShell:

```powershell
.\scripts\init-project.ps1 -ProjectName "Template Smoke Test" -PackageName "com.example.templatesmoketest"
.\scripts\verify.ps1
```

8. Inspect `git diff` and confirm the generic package, project name, application class, theme class, and source paths were renamed.
9. Open the generated project in Android Studio and run the debug app.
10. Delete the temporary GitHub repository when the smoke test is complete, if it is no longer needed.

## 13. Publish the article and launch posts

1. Copy `docs/MEDIUM_ARTICLE.md` into a new Medium story.
2. Replace the repository placeholder, add a cover image and alt text, and preview every heading, code block, table, and link.
3. Publish the story and copy its public URL.
4. Add the Medium URL to the repository's **About** website field.
5. Replace `<MEDIUM_ARTICLE_URL>` in `docs/SOCIAL_POSTS.md`.
6. Publish the LinkedIn post and X post/thread.
7. Add the article URL to the README if you want it permanently discoverable from the repository.

## Ongoing maintenance

- Merge dependency updates only after CI passes.
- Test both initializer scripts before every tagged template release.
- Keep the generic package and project identity unchanged on `main`.
- Document breaking template changes in release notes.
- Never commit a real keystore, service configuration, token, or organization credential.
- Re-run a create-from-template smoke test after changing build logic, initialization scripts, or CI.

## Quick command summary

```bash
cd /home/rhymezxcode/StudioProjects/personal/AndroidProjectTemplate
./scripts/verify.sh
git status --short
git add .
git status
git commit -m "feat: publish modular Android project template"
git remote add origin https://github.com/<OWNER>/android-project-template.git
git remote -v
git push -u origin main
```

Then enable **Settings → General → Template repository** on GitHub and verify **Use this template** from the repository's main page.
