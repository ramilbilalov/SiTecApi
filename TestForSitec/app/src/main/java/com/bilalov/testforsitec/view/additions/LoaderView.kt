package com.bilalov.testforsitec.view.additions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoaderView(
    isLoading: Boolean,
    content: @Composable () -> Unit,
) = if (isLoading
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
        ) {
            CircularProgressIndicator()
            Text(text = "Загрузка...", modifier = Modifier.padding(7.dp))
        }
    }
} else {
    content()
}
