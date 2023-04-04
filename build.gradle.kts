// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.HILT) version Versions.HILT apply false
    id(Plugins.NAVIGATION_SAFE_ARGS) version Versions.NAVIGATION apply false
    id(Plugins.PROTO_DATASTORE) version Versions.PROTO_DATASTORE_PLUGIN apply false
}