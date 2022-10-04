package com.bilalov.testforsitec.view

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bilalov.testforsitec.connectionChecker.TelemetryCheckerImpl
import com.bilalov.testforsitec.view.additions.LoaderView
import com.bilalov.testforsitec.view.additions.MyRow
import com.bilalov.testforsitec.viewModel.MainViewModel


@Composable
fun DropdownDemo(viewModel: MainViewModel, context: Application, connection:TelemetryCheckerImpl) {
    var isLoading by remember { mutableStateOf(true) }

    val listItem by viewModel.allUserInfo.observeAsState()
    val statusApiAuth by viewModel.statusApiAuth.observeAsState()
    val responseApiAuth by viewModel.allSearchItems.observeAsState()

    viewModel.statusApiFirst.observeForever{
        if (viewModel.statusApiFirst.value.toBoolean()) {
            isLoading = false
        }
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember {
        mutableStateOf("Выберите пользователя")
    }
    var selectedUid by remember {
        mutableStateOf("")
    }
    var textInputSearch by remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false)  }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(selectedIndex, modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { expanded = true }
                )
                .background(
                    Gray
                )
                .padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                LoaderView(isLoading = isLoading) {}
                listItem?.Users?.ListUsers?.size?.let {
                    listItem!!.Users.ListUsers.forEachIndexed { model, listItem2 ->
                        DropdownMenuItem(onClick = {
                            selectedIndex = listItem2.User + "\n" + listItem2.Uid
                            expanded = false
                            selectedUid = listItem2.Uid
                        }) {
                            MyRow(model = model, info = listItem)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val focusManager = LocalFocusManager.current

                OutlinedTextField(
                    value = textInputSearch,
                    onValueChange = { newText ->
                        textInputSearch = newText.trimStart { it == '0' }
                    },
                    singleLine = true,
                    label = {
                        Text("Введите пароль")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Black,
                        disabledTextColor= Black,
                        cursorColor= Black,
                        errorCursorColor= Black,
                        focusedBorderColor= Black,
                        unfocusedBorderColor= Black,
                        disabledBorderColor= Black,
                        trailingIconColor= Black,
                        disabledTrailingIconColor = Black,
                        errorTrailingIconColor= Black,
                        focusedLabelColor= Black,
                        unfocusedLabelColor= Black,
                        disabledLabelColor= Black,
                        errorLabelColor= Black,
                        placeholderColor= Black,
                        disabledPlaceholderColor= Black
                    )
                    ,
                    modifier = Modifier
                        .padding(2.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        if (textInputSearch.isNotEmpty() && selectedUid.isNotEmpty()){
                            viewModel.getRepository(
                                password = textInputSearch,
                                uid = selectedUid,
                                application = context,
                                imei = connection.getImei(context)
                            )
                            focusManager.clearFocus()
                            openDialog.value = true
                        } else {
                            Toast.makeText(
                                context,
                                "Проверьте корректность авторизации",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }),
                )
                Button(
                    onClick = {
                        if (textInputSearch.isNotEmpty() && selectedUid.isNotEmpty()){
                            viewModel.getRepository(
                                password = textInputSearch,
                                uid = selectedUid,
                                application = context,
                                imei = connection.getImei(context)
                            )
                            openDialog.value = true
                        } else {
                            Toast.makeText(
                                context,
                                "Проверьте корректность авторизации",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }, modifier = Modifier
                        .padding(4.dp)
                ) {
                    Text(text = "Войти"
                        ,textAlign = TextAlign.Center
                        ,modifier = Modifier
                        ,letterSpacing = 1.sp
                        ,style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
    if (statusApiAuth?.isNotEmpty() == true && openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Поздравляю!")
            },
            text = {
                Text("Ответ API, Code:" + responseApiAuth?.Code.toString() +"\nСтатус запроса:"+ statusApiAuth)
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Принять")
                }
            }
        )
    }
}
