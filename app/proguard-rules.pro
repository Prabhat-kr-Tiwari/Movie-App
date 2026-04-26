# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep custom NavType objects
#-keep class com.prabhat.movieapp.navigation.customNavType { *; }
-keep class com.prabhat.movieapp.navigation.customNavType$* { *; }

# Keep MovieTag and MovieCategory for serialization
-keep class com.prabhat.movieapp.presentation.screen.home.MovieTag { *; }
-keep class com.prabhat.movieapp.presentation.screen.home.MovieCategory { *; }

# Keep all NavType subclasses
-keep class * extends androidx.navigation.NavType { *; }

# Kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keep,includedescriptorclasses class com.prabhat.movieapp.**$$serializer { *; }
-keepclassmembers class com.prabhat.movieapp.** {
    *** Companion;
}
-keepclasseswithmembers class com.prabhat.movieapp.** {
    kotlinx.serialization.KSerializer serializer(...);
}