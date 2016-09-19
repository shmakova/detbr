# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keep class ru.yandex.detbr.** { *; }
-keepclassmembers class ru.yandex.detbr.** { *; }

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

-dontwarn com.roughike.bottombar.**

-keep class org.chromium.base.** { *; }
-dontwarn org.chromium.base.**

-keep class java.nio.file.** { *; }
-dontwarn java.nio.file.**

-keep class org.codehaus.mojo.** { *; }
-dontwarn org.codehaus.mojo.**

-keep class javax.annotation.** { *; }
-keepclassmembers class javax.annotation.** { *; }
-dontwarn javax.annotation.**

-keep class org.xwalk.core.** { *; }
-keep class org.chromium.** { *; }
-keepattributes **


-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Configuration for Fabric Twitter Kit
# See: https://dev.twitter.com/twitter-kit/android/integrate

-dontwarn com.squareup.okhttp.**
-dontwarn com.google.appengine.api.urlfetch.**
-dontwarn rx.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

-keeppackagenames org.jsoup.nodes

## Retrolambda specific rules ##

# as per official recommendation: https://github.com/evant/gradle-retrolambda#proguard
-dontwarn java.lang.invoke.*

 -keep class org.sqlite.** { *; }
 -keep class org.sqlite.database.** { *; }

 # Dagger ProGuard rules.
 # https://github.com/square/dagger

 -dontwarn dagger.internal.codegen.**
 -keepclassmembers,allowobfuscation class * {
     @javax.inject.* *;
     @dagger.* *;
     <init>();
 }

 -keep class dagger.* { *; }
 -keep class javax.inject.* { *; }
 -keep class * extends dagger.internal.Binding
 -keep class * extends dagger.internal.ModuleAdapter
 -keep class * extends dagger.internal.StaticInjection

 # OkHttp
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class okhttp3.** { *; }
 -keep interface okhttp3.** { *; }
 -dontwarn okhttp3.**



########--------Retrofit + RxJava--------#########

-dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepattributes Signature
 -keepattributes Exceptions

 -keepclasseswithmembers class * {
     @retrofit2.http.* <methods>;
 }

-dontwarn sun.misc.Unsafe
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient

-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
   long producerNode;
   long consumerNode;
}


 -keep public class android.support.v7.widget.** { *; }
 -keep public class android.support.v7.internal.widget.** { *; }
 -keep public class android.support.v7.internal.view.menu.** { *; }

 -keep public class * extends android.support.v4.view.ActionProvider {
     public <init>(android.content.Context);
 }

 # http://stackoverflow.com/questions/29679177/cardview-shadow-not-appearing-in-lollipop-after-obfuscate-with-proguard/29698051
 -keep class android.support.v7.widget.RoundRectDrawable { *; }

 -keep class rx.schedulers.Schedulers {
     public static <methods>;
 }
 -keep class rx.schedulers.ImmediateScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.TestScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.Schedulers {
     public static ** test();
 }

 -dontwarn java.lang.invoke.* #retrolambda

 -dontwarn sun.misc.**

 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
 }

 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }

 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
     }
 #region retorfit
    # Platform calls Class.forName on types which do not exist on Android to determine platform.
    -dontnote retrofit2.Platform
    # Platform used when running on RoboVM on iOS. Will not be used at runtime.
    -dontnote retrofit2.Platform$IOS$MainThreadExecutor
    # Platform used when running on Java 8 VMs. Will not be used at runtime.
    -dontwarn retrofit2.Platform$Java8
    # Retain generic type information for use by reflection by converters and adapters.
    -keepattributes Signature
    # Retain declared checked exceptions for use by a Proxy instance.
    -keepattributes Exceptions
 #endregion
 -dontwarn okio.**
 -dontwarn rx.**