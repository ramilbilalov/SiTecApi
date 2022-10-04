package com.bilalov.testforsitec

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bilalov.testforsitec.connectionChecker.TelemetryCheckerImpl
import com.bilalov.testforsitec.ui.theme.TestForSitecTheme
import com.bilalov.testforsitec.view.RequestPermission
import com.bilalov.testforsitec.view.additions.LottieBackground
import com.bilalov.testforsitec.viewModel.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val connection = TelemetryCheckerImpl

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestForSitecTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MainViewModel>()

                RequestPermission(
                    permission = Manifest.permission.READ_PHONE_STATE,
                    navController = navController,
                    context = application,
                    viewModel = viewModel,
                    connection = connection
                )

            }
            val isLoadingAnim by remember {
                mutableStateOf(true)
            }
            LottieBackground(isLoading = isLoadingAnim) {}
        }
    }
}