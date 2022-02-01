
# Lazybones proguard rules #

# noinspection ShrinkerUnresolvedReference

-dontwarn androidx.lifecycle.**
-keep class androidx.lifecycle.** { *; }
-keep class * extends androidx.lifecycle.ViewModel