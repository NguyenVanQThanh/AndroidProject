pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = "https://jitpack.io")
        jcenter()
    }
}

rootProject.name = "ProjectHK1_2023-2024"
include(":app")
include(":myapplication")
