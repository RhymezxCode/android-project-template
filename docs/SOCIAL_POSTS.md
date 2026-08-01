# Social Launch Posts

Replace these placeholders before publishing:

- `<TEMPLATE_REPOSITORY_URL>`
- `<MEDIUM_ARTICLE_URL>`

## LinkedIn — launch post

Starting an Android project is easy. Building a foundation that still works when the product, codebase, and team grow is the difficult part.

I built **AndroidProjectTemplate**, a reusable Android foundation designed to remove that repeated setup work.

It includes:

✅ Modular MVVM with clear dependency direction  
✅ Jetpack Compose, Material 3, Hilt, Coroutines, and Flow  
✅ Gradle convention plugins and a version catalog  
✅ JVM, ViewModel, repository, and Compose tests  
✅ Detekt, ktlint, Kover reports, and a 70% coverage gate  
✅ GitHub Actions for Ubuntu, macOS, and Windows  
✅ Bash and PowerShell project initializers  
✅ A secure signed-AAB workflow with no credentials committed

The important part is not the number of modules or libraries. It is that the starter demonstrates a complete, executable path:

`Compose → ViewModel → Use Case → Repository → Data Source`

Developers can select **Use this template**, create an independent repository, run one initialization command, and start building product features with the architecture, tests, and delivery baseline already in place.

Template: <TEMPLATE_REPOSITORY_URL>

I also wrote a detailed walkthrough covering the architecture decisions, testing strategy, convention plugins, cross-platform CI/CD, and trade-offs:

Article: <MEDIUM_ARTICLE_URL>

I would love feedback from Android engineers: what is the one capability you always add to a new project?

#AndroidDevelopment #Kotlin #JetpackCompose #SoftwareArchitecture #MVVM #Gradle #GitHubActions #OpenSource

## LinkedIn — shorter version

I open-sourced **AndroidProjectTemplate**, a production-oriented starting point for Android teams.

It combines modular MVVM, Jetpack Compose, Hilt, Flow, Gradle convention plugins, layered tests, code-quality gates, and CI that generates and builds renamed projects on Ubuntu, macOS, and Windows.

Use the repository as a GitHub template, run one Bash or PowerShell initializer, and begin with a tested foundation instead of rebuilding project infrastructure.

Repository: <TEMPLATE_REPOSITORY_URL>

Architecture walkthrough: <MEDIUM_ARTICLE_URL>

Feedback and contributions are welcome.

#Android #Kotlin #JetpackCompose #Architecture #CICD #OpenSource

## X — single post

I built AndroidProjectTemplate: modular MVVM + Compose, Hilt, convention plugins, tests, quality gates, and CI on Ubuntu/macOS/Windows. Use the GitHub template, run one initializer, and start building. <TEMPLATE_REPOSITORY_URL> #AndroidDev #Kotlin

> Check the final character count after replacing the URL. If it exceeds 280 characters, remove `quality gates, and `.

## X — article post

A good Android starter should help with feature 50, not only screen 1. I wrote how AndroidProjectTemplate handles modular MVVM, Compose state, build logic, tests, and cross-platform CI/CD. <MEDIUM_ARTICLE_URL> #AndroidDev #JetpackCompose

> Check the final character count after replacing the URL. If it exceeds 280 characters, remove `cross-platform `.

## X — launch thread

**1/7**

Starting an Android app takes minutes. Rebuilding architecture, Gradle conventions, tests, and CI for every project takes days.

I created AndroidProjectTemplate to make that foundation reusable. 🧵

<TEMPLATE_REPOSITORY_URL>

**2/7**

The dependency direction is explicit:

Compose → ViewModel → Use Case → Repository contract ← Data implementation

Features depend on stable domain contracts, not on storage or networking details.

**3/7**

The starter includes Compose + Material 3, Hilt/KSP, Coroutines/Flow, Navigation Compose, and a real feature that exercises the full path from UI to data source.

It is executable architecture, not just a folder diagram.

**4/7**

Shared Gradle decisions live in convention plugins. Dependency versions live in one version catalog.

Feature build files stay focused on module identity and intentional dependency edges.

**5/7**

Testing covers ViewModels, repository behavior, failure paths, and Compose semantics.

Detekt + ktlint enforce quality, while Kover produces reports and enforces a 70% aggregate line-coverage gate.

**6/7**

CI verifies the baseline and generates renamed projects on Ubuntu, macOS, and Windows.

The repo includes Bash + PowerShell initializers, emulator tests, and a secret-backed signed-AAB workflow.

**7/7**

Use it from GitHub, run one initializer, and start with your own app identity:

<TEMPLATE_REPOSITORY_URL>

Full architecture walkthrough:
<MEDIUM_ARTICLE_URL>

What would you add to the foundation? #AndroidDev #Kotlin

## Suggested reply after launch

The repository includes architecture, testing, CI/CD, contribution, security, and publishing guides. If you use it for a project, please share what worked and what you changed—the feedback will help shape the next template release.
