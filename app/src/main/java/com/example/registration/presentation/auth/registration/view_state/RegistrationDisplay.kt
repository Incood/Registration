package com.example.registration.presentation.auth.registration.view_state

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.presentation.auth.registration.view_models.RegistrationEvent
import com.example.registration.presentation.auth.registration.view_models.RegistrationState
import com.example.registration.presentation.navigation.Screens
import com.example.registration.presentation.utils.DateVisualTransformation
import com.example.registration.presentation.utils.singleClick
import com.example.registration.ui.theme.LightBlue
import com.example.registration.ui.theme.MainBlue
import com.example.registration.ui.theme.MainGray
import com.example.registration.ui.theme.SideOrange

@Composable
fun RegistrationDisplay(
    state: RegistrationState.Content,
    navHostController: NavHostController,
    event: (RegistrationEvent) -> Unit,
) {
    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                Log.d("URI RESULT", "$it")

                val resolver = context.contentResolver
                try {
                    resolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    Log.e("Permission Error", "Failed to take persistable URI permission", e)
                }

                event(RegistrationEvent.ChangeAvatarUri(it.toString()))
            }
        }
    )

    if (state.successRegistration) {
        navHostController.navigate(Screens.ListUsers.route) {
            popUpTo(Screens.Authorization.route) { inclusive = true }
        }
        event(RegistrationEvent.ResetAuthorization)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 14.dp)
    ) {


        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .padding(start = 16.dp)
                .singleClick { navHostController.popBackStack() }) {
                Image(painter = painterResource(id = R.drawable.back), contentDescription = "")
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Регистрация",

                // SF Pro/Bold/18
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 24.3.sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF0A123E),
                    letterSpacing = 0.42.sp,
                )
            )

        }

        Column(modifier = Modifier.align(Alignment.Center)) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {

                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    state.avatarUri?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        onClick = { singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                        colors = ButtonDefaults.buttonColors(LightBlue),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Выбрать аватарку", color = Color.White)
                    }
                }


                OutlinedTextField(
                    isError = state.errorUsername,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    value = state.userName,
                    onValueChange = { newText ->
                        if (newText.split(" ").size <= 2) {
                            event(RegistrationEvent.ChangeUserName(newText))
                        }
                    },
                    label = { Text(text = "Имя пользователя") },
                    placeholder = { Text(text = "Иван Иванов") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        errorBorderColor = SideOrange,
                        errorLabelColor = SideOrange,
                        focusedBorderColor = MainBlue,

                        unfocusedBorderColor = MainGray,
                        disabledLabelColor = Color(0x80E7E8EC),
                        focusedLabelColor = MainBlue,
                        unfocusedLabelColor = Color(0xFF85899F),
                        disabledBorderColor = Color(0x80E7E8EC),
                        backgroundColor = Color(0x80E7E8EC),
                        cursorColor = MainBlue,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    isError = state.errorPassword,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = state.password,
                    onValueChange = { newText ->
                        event(RegistrationEvent.ChangePassword(newText))
                    },
                    label = { Text(text = "Пароль") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        errorBorderColor = SideOrange,
                        errorLabelColor = SideOrange,
                        focusedBorderColor = MainBlue,
                        unfocusedBorderColor = MainGray,
                        disabledLabelColor = Color(0x80E7E8EC),
                        focusedLabelColor = MainBlue,
                        unfocusedLabelColor = Color(0xFF85899F),
                        disabledBorderColor = Color(0x80E7E8EC),
                        backgroundColor = Color(0x80E7E8EC),
                        cursorColor = MainBlue,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = state.dateOfBirth,
                    onValueChange = { newText ->
                        if (newText.length <= 8) {
                            event(RegistrationEvent.ChangeDate(newText))
                        }
                    },
                    label = { Text(text = "Дата рождения") },
                    placeholder = { Text(text = "01.01.2000") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        errorBorderColor = SideOrange,
                        errorLabelColor = SideOrange,
                        focusedBorderColor = MainBlue,

                        unfocusedBorderColor = MainGray,
                        disabledLabelColor = Color(0x80E7E8EC),
                        focusedLabelColor = MainBlue,
                        unfocusedLabelColor = Color(0xFF85899F),
                        disabledBorderColor = Color(0x80E7E8EC),
                        backgroundColor = Color(0x80E7E8EC),
                        cursorColor = MainBlue,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = DateVisualTransformation()
                )

                Button(
                    enabled = state.userName.isNotEmpty() && state.password.isNotEmpty(),
                    shape = ShapeDefaults.Small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    onClick = {
                        event(RegistrationEvent.SignUp)
                    },
                    colors = ButtonDefaults.buttonColors(MainBlue)
                ) {

                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Войти в аккаунт",

                        // SF Pro/Bold/18
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 24.3.sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.42.sp,
                        )
                    )
                }

                Text(modifier = Modifier
                    .singleClick {
                        navHostController.popBackStack()
                    }
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp),
                    text = "У меня уже есть аккаунт",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF85899F),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegistrationPREV() {
    RegistrationDisplay(
        navHostController = rememberNavController(),
        event = {},
        state = RegistrationState.Content("", "")
    )
}