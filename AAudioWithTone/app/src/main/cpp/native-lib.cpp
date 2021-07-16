#include <jni.h>
#include <string>
#include <aaudio/AAudio.h>
#include <android/log.h>
#include "AAudioEngine.h"

static AAudioEngine *aAudioEngine = nullptr;
static bool streamStarted = false;
extern "C" {
JNIEXPORT jstring JNICALL
Java_com_example_aaudiowithtone_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jboolean JNICALL
Java_com_example_aaudiowithtone_MainActivity_initAudioEngine(JNIEnv *env, jobject thiz) {
    // TODO: implement initAudioEngine()
    if(aAudioEngine == nullptr){
        __android_log_print(ANDROID_LOG_ERROR,"JNI","Creating AAudioEngine");
        aAudioEngine = new AAudioEngine();
    }
    return (aAudioEngine != nullptr);
}

/******** Synthesis of Tone ********/
JNIEXPORT aaudio_result_t JNICALL
Java_com_example_aaudiowithtone_MainActivity_createPlayBack(JNIEnv *env, jobject thiz,
                                                            jint m_format) {
    // TODO: implement createPlayBack()
    if(aAudioEngine == nullptr){
        __android_log_print(ANDROID_LOG_ERROR,"JNI","Failed to create tone");
        return AAUDIO_ERROR_NULL;
    }
    return aAudioEngine->createPlayback(m_format);
}

JNIEXPORT jboolean JNICALL
Java_com_example_aaudiowithtone_MainActivity_stopPlayBack(JNIEnv *env, jobject thiz) {
    // TODO: implement stopPlayBack()
    if(aAudioEngine == nullptr)
        return false;
    return aAudioEngine->stopPlayBack();
}
}
