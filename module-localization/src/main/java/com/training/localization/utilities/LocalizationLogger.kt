package com.training.trainingmodule.localization.utilities

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.training.trainingmodule.localization.BuildConfig
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeoutException

class LocalizationLogger private constructor() {
    companion object {
        private val TAG = "Localization"
        private var isdebug = BuildConfig.DEBUG

        fun handle(e: Exception) {
            try {
                if (e.message != null) {
                    debug(
                        "LocalizationExceptionHandler",
                        "handle() called with: " + "e = [" + e.message + "]"
                    )
                    if (isExceptionOccured(e))
                        FirebaseCrashlytics.getInstance().recordException(e)
                }
            } catch (e1: java.lang.Exception) {
            }
        }

        private fun isExceptionOccured(e: Exception): Boolean {
            return (!(e is UnknownHostException || e is CancellationException || e is TimeoutException || e is SocketTimeoutException))
        }

        fun toastMessage(context: Context, msg: String) {
            if (isdebug) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }

        fun debug(tag: String, msg: String) {
            if (isdebug) {
                Log.d(tag, msg)
            }
        }

        fun error(tag: String, msg: String) {
            if (isdebug) {
                Log.e(tag, msg)
            }
        }

        fun info(tag: String, msg: String) {
            if (isdebug) {
                Log.i(tag, msg)
            }
        }

        fun debug(string: String) {
            if (isdebug) {
                Log.d(TAG, string)
            }
        }
    }
}
