import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")
        configureAndroid(extensions.getByType<ApplicationExtension>())

        extensions.getByType<ApplicationExtension>().defaultConfig {
            targetSdk = libs.intVersion("target-sdk")
        }

        pluginManager.apply("starter.android.compose")
        pluginManager.apply("starter.android.hilt")
    }
}
