/**
 * 对于传统的JNI编程来说，JNI方法跟Java类方法的名称之间有一定的对应关系，要遵循一定的命名规则，如下所示：
 * 1.前缀： Java_
 * 2.类的全限定名，用下划线进行分隔（_）：org_gmfbilu_superapp_module_util_jni_JniDemoLib
 * 3.方法名：getString
 * 4.JNI函数指定第一个参数： JNIEnv *
 * 5.JNI函数指定第二个参数： jobject
 * 6.实际Java参数： jstring, jint ….
 * 7.对于包名和方法名中含有下划线的情况下在下划线后面添加1
 *
 * 所以对于在Java类 org_gmfbilu_superapp_module_util_jni_JniDemoLib类的一个方法：
 * public native String getString()
 * 其对应的jni层的方法如下：
 * jstring Java_org_gmfbilu_superapp_module_util_jni_JniDemoLib_getString(JNIEnv * e, jobject clazz);
 *
 * JNI 数据类型
 * 我们知道Java的数据类型是跟C/C++的数据类型是不一样的，而JNI是处于Java和Native本地库（大部分是用C/C++写的）中间的一层，JNI对于两种不同的数据类型之间必须做一种转换，所以在JNI跟Java之间就会有数据类型的对应关系。 在JNI中，提供了以下各种数据类型，可以分为原生类型和引用类型： 对于原生类型有：jchar, jbyte, jshort, jint, jlong, jfloat, jdouble, jboolean
 * java	    jni
 * char	    jchar
 * byte	    jbyte
 * short    jshort
 * int	    jint
 * long	    jlong
 * float    jfloat
 * double	jdouble
 * boolean	jboolean
 * 对于引用类型则有：jobject, jstring, jthrowable, jclass, jarray, 以及继承于jarray，对应于其原生类型的8种jarray和jobjectarray
 */













#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
/*完整路径：Java_包名_类名_方法名 记得用下划线代替. */
Java_org_gmfbilu_superapp_module_1util_jni_JniDemoLib_getString(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from JNI";
    return env->NewStringUTF(hello.c_str());
}
