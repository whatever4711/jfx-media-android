/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_sun_prism_es2_MacGLFactory */

#ifndef _Included_com_sun_prism_es2_MacGLFactory
#define _Included_com_sun_prism_es2_MacGLFactory
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_sun_prism_es2_MacGLFactory
 * Method:    nInitialize
 * Signature: ([I)J
 */
JNIEXPORT jlong JNICALL Java_com_sun_prism_es2_MacGLFactory_nInitialize
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_sun_prism_es2_MacGLFactory
 * Method:    nGetAdapterOrdinal
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_sun_prism_es2_MacGLFactory_nGetAdapterOrdinal
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_sun_prism_es2_MacGLFactory
 * Method:    nGetAdapterCount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_sun_prism_es2_MacGLFactory_nGetAdapterCount
  (JNIEnv *, jclass);

/*
 * Class:     com_sun_prism_es2_MacGLFactory
 * Method:    nGetIsGL2
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_sun_prism_es2_MacGLFactory_nGetIsGL2
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
