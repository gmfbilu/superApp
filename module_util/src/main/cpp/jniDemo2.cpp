#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
/*完整路径：Java_包名_类名_方法名 记得用下划线代替. */
Java_org_gmfbilu_superapp_module_1util_jni_JniFragment_getJniString(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello  JNI";
    return env->NewStringUTF(hello.c_str());
}