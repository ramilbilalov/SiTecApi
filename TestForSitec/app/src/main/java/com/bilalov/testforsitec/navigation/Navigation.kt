package com.bilalov.testforsitec.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bilalov.testforsitec.connectionChecker.TelemetryCheckerImpl
import com.bilalov.testforsitec.view.DropdownDemo
import com.bilalov.testforsitec.viewModel.MainViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    context: Application?,
    viewModel: MainViewModel,
    connection: TelemetryCheckerImpl
) {
    NavHost(navController = navController, startDestination = Screen.Main.screenName) {
        composable(route = Screen.Main.screenName) {
            if (context != null) {
                DropdownDemo(viewModel = viewModel, context = context, connection = connection)
            }
        }
    }
}



