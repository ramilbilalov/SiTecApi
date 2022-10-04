package com.bilalov.testforsitec.connectionChecker

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager


object TelemetryCheckerImpl : ITelemetryChecker {

    override fun getImei(context: Context): String {

        val deviceId: String = if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            val mTelephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
        return deviceId

    }
}