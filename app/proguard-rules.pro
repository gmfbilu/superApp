#四大组件、Fragment、自定义控件不需要添加混淆规则，因为这些默认是不会被混淆的，所以网上很多四大组件的混淆规则是没必要添加的
#含有Keep关键字的含义（移除是指在压缩时(Shrinking)是否会被删除，需要开启压缩）



#保留该包下的类名不会被混淆，但是该包的子包的类名还是会被混淆
-keep class pageName.*

#保留该包及其子包的类名不会被混淆
-keep class pageName.**

#保留类名及其该类的内容不会被混淆（包括变量名，方法名等）
-keep class pageName.* {*;}

#不保留类名只保留该类的方法名、变量名等不会被混淆
-keepclassmembers class pageName.*{*;}

#保留所有继承某类的子类不会被混淆（implement同理）
-keep public class * extends android.app.Activity

#-keepclassmembers class pageName$内部类名 {   //"$"的含义是保留某类的内部类不会被混淆
#   public *;
#} 保留该内部类中所有的public方法名、变量名不会被混淆

#-keep class pageName {
#    public <init>;  //保留所有的public的构造方法不会被混淆
#}

#-keep class pageName {
#    public <init>（java.lang.String）; //保留所有的public的构造方法并且参数是String对象，不会被混淆
#}

#-keep class pageName {   //保留所有的private的方法名不会被混淆
#    private <methods>;
#}



#-keepclasseswithmembernames class * { 本地的native方法（JNI）
#    native <methods>;
#}

#-keep class pageName{*;} 反射(该pageName是被反射类的包名)

#-keepattributes Signature JavaBean中的泛型。如果使用了Gson进行解析Json字符串，就需要添加JavaBean的混淆规则，因为Gson使用了反射的原理














#============================================== app内实体类====================================
-keep class org.exmaple.request.** { *; } #org.exmaple.request包中所有的类
-keep class org.exmaple.http.HttpResult{ *; } #org.exmaple.http包中的HttpResult类





#===============================================基本不用动区域========================================
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
     native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
     public void *(android.view.View);
}
-keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
     *** get*();
     void set*(***);
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
}
-keepclassmembers class * {
     void *(**On*Event);
}

 # 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
 # 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
     ... *JNI*(...);
}

-keepclasseswithmembernames class * {
 	... *JRI*(...);
}

-keep class **JNI* {*;}

#注解
-keepattributes *Annotation*

#R文件下面的资源
-keep class **.R$* {*;}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#Parcelable序列化和Creator静态成员变量
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#Serializable序列化
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
    public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
     public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView, jav.lang.String);
}
-keep class android.webkit.JavascriptInterface {*;}
#与JS交互
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepclassmembers class pageName$内部类名 {
    public *;
}






#============================================= 第三方库 begin ==============================================
#---------------------------------------------ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-dontwarn javax.lang.model.element.**
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider
#---------------------------------------------Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
#---------------------------------------------EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#---------------------------------------------Push
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**
-keepattributes *Annotation*
-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}
-keep public class **.R$*{
   public static final int *;
}
#----------------------------------------------------Retrofit
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-----------------------------------------------------okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
#-----------------------------------------------------glide
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule