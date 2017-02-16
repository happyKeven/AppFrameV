# 代码迭代优化的次数，取值范围0~7,默认为5
-optimizationpasses 5
 # 混淆时不使用大小写混合的方式，这样混淆后都是小写字母的组合
-dontusemixedcaseclassnames
# 混淆时是否做预校验,由前面的介绍可以知道，预校验是Proguard四大功能之一，在Android中一般不需要预校验，这样可以加快混淆的速度
-dontpreverify
 # 混淆时记录日志，同时会生成映射文件，Android Studio中，生成的默认映射文件是'build/outputs/mapping/release/mapping.txt'
-verbose
# 指定生成的映射文件的路径和名称
-printmapping build/outputs/mapping/release/mymapping.txt

# 混淆时所采用的算法，参数是Google 官方推荐的过滤器算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 如果项目中使用到注解，需要保留注解属性
-keepattributes *Annotation*

# 不混淆泛型
-keepattributes Signature

# 保留代码行号，这在混淆后代码运行中抛出异常信息时，有利于定位出问题的代码
-keepattributes SourceFile,LineNumberTable

# 保持Android SDK相关API类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.google.vending.licensing.ILicensingService

# 保留R类
-keep class **.R$* {
    *;
}

# 保持native 方法不做混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保持自定义控件类不被混淆
 -keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
 }

 -keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
 }

#保持Activity中参数是View类型的函数，保证在XML文件中配置的onClick属性的值能够正常调用到
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#保持枚举类型中的函数
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持 Parcelable 类中的 CREATOR 不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# 保持Serializable序列化类相关方法和字段不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# 保持自定义控件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    *** get* ();
}

# 引入LoganSquare开源库所需增加的混淆配置
-keep class com.bluelinelabs.logansquare.** { *; }
-keep @com.bluelinelabs.logansquare.annotation.JsonObject class *
-keep class **$$JsonObjectMapper { *; }


-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}




-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

# ormlite数据库
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.j256.ormlite.** { *; }


# 引入picasso
-dontwarn com.squareup.okhttp.**
