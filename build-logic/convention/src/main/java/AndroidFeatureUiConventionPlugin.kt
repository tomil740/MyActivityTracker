import com.android.build.api.dsl.LibraryExtension
import com.tomiappdevelopment.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

/*
each presentation layer in a feature will use :
* compose and android
* our design module and the shared ui components from the ui module
* and a dependencies for dependency injection to be used in our vm
 */

class AndroidFeatureUiConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("myactivitytracker.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}