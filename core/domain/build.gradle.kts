plugins {
    id("starter.jvm.library")
}

dependencies {
    api(project(":core:model"))
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
}
