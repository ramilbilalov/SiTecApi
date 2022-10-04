package com.bilalov.testforsitec.navigation

sealed class Screen(val screenName: String) {

    object Main : Screen("main")

}