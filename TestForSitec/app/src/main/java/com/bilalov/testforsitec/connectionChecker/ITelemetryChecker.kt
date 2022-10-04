package com.bilalov.testforsitec.connectionChecker

import android.content.Context

interface ITelemetryChecker {

    fun getImei(context: Context): String

}