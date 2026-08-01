import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("starter.android.library")
        pluginManager.apply("starter.android.compose")
        pluginManager.apply("starter.android.hilt")

        dependencies {
            "implementation"(libs.findLibrary("androidx-lifecycle-runtime-compose").get())
            "implementation"(libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
            "implementation"(libs.findLibrary("androidx-hilt-lifecycle-viewmodel-compose").get())
            "implementation"(libs.findLibrary("androidx-hilt-navigation-compose").get())
            "implementation"(libs.findLibrary("kotlinx-coroutines-core").get())
        }
    }
}
