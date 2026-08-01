import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        val androidExtension: CommonExtension<*, *, *, *, *, *> =
            when {
                pluginManager.hasPlugin("com.android.application") ->
                    extensions.getByType<ApplicationExtension>()
                pluginManager.hasPlugin("com.android.library") ->
                    extensions.getByType<LibraryExtension>()
                else -> error("Compose convention requires an Android plugin")
            }

        androidExtension.buildFeatures.compose = true

        dependencies {
            val bom = platform(libs.findLibrary("compose-bom").get())
            "implementation"(bom)
            "androidTestImplementation"(bom)
            "implementation"(libs.findLibrary("compose-material3").get())
            "implementation"(libs.findLibrary("compose-ui").get())
            "implementation"(libs.findLibrary("compose-ui-tooling-preview").get())
            "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            "debugImplementation"(libs.findLibrary("compose-ui-test-manifest").get())
            "androidTestImplementation"(libs.findLibrary("compose-ui-test-junit4").get())
        }
    }
}
