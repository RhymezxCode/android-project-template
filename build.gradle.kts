plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp) apply false
}

subprojects {
    if (buildFile.exists()) {
        apply(plugin = "io.gitlab.arturbosch.detekt")
        apply(plugin = "org.jmailen.kotlinter")
        apply(plugin = "org.jetbrains.kotlinx.kover")
        extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
            buildUponDefaultConfig = true
            parallel = true
            config.setFrom(rootProject.files("config/detekt/detekt.yml"))
        }
    }
}

dependencies {
    kover(project(":core:model"))
    kover(project(":core:domain"))
    kover(project(":core:data"))
    kover(project(":feature:home"))
}

kover {
    reports {
        total {
            verify {
                rule("Aggregate line coverage must remain at least 70%") {
                    minBound(70)
                }
            }
        }
        filters {
            excludes {
                annotatedBy("androidx.compose.runtime.Composable")
                annotatedBy("dagger.Module")
                annotatedBy("dagger.internal.DaggerGenerated")
                classes(
                    "hilt_aggregated_deps.*",
                    "*.di.*",
                    "*Application",
                    "*Activity",
                    "*ComposableSingletons*",
                    "*_Factory",
                    "*_Factory$*",
                    "*_HiltModules*",
                    "*Hilt_*",
                    "*BuildConfig",
                    "*.designsystem.*",
                )
            }
        }
    }
}
