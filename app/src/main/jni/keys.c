#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_foragentss_MainActivity_getNativeKey(JNIEnv *env, jobject instance) {

 return (*env)->  NewStringUTF(env, "HFKhzcLVAZsCTOOF/bU0vxMsl7PONClk");
}