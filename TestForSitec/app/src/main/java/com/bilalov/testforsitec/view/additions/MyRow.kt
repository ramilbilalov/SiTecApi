package com.bilalov.testforsitec.view.additions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bilalov.testforsitec.data.ResponseUserProfile

@Composable
fun MyRow(info: ResponseUserProfile?, model: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        // .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                ,border = BorderStroke(1.dp, Color.Black)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                    .padding(4.dp)
                ) {
                    info?.Users?.ListUsers?.get(model)?.let {
                        Text(text = it.User)
                        Text(text = it.Uid)
                        Text(text = it.Language)
                    }
                }
            }
        }
    }
}
