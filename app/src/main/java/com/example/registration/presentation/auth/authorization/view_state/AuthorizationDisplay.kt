package com.example.registration.presentation.auth.authorization.view_state

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.registration.LoadingAnimation
import com.example.registration.R
import com.example.registration.presentation.auth.authorization.view_models.AuthorizationEvent
import com.example.registration.presentation.auth.authorization.view_models.AuthorizationState
import com.example.registration.presentation.navigation.Screens
import com.example.registration.singleClick
import com.example.registration.ui.theme.MainBlue
import com.example.registration.ui.theme.MainGray
import com.example.registration.ui.theme.SecondGray
import com.example.registration.ui.theme.SideOrange
import com.example.registration.ui.theme.TextLabelGray

@Composable
fun AuthorizationDisplay(
    state: AuthorizationState.Content,
    navHostController: NavHostController,
    onEvent: (AuthorizationEvent) -> Unit
) {
    if (state.successAuthorization) {
        navHostController.navigate(Screens.ListUsers.route) {
            popUpTo(Screens.Authorization.route) { inclusive = true }
        }
        onEvent(AuthorizationEvent.ResetAuthorization)
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
                text = "Авторизация",

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

        Column(modifier = Modifier.align(Center)) {
            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {

                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = "Вход в аккаунт",

                    // SF Pro/Semibold/24
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF0A123E),
                    )

                )


                OutlinedTextField(
                    isError = state.errorUserName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    value = state.userName,
                    onValueChange = { newText ->
                        onEvent(AuthorizationEvent.ChangeUserName(newText))
                    },
                    label = {
                        Text(
                            text = "Имя пользователя",
                            color = if (state.errorUserName) SideOrange else TextLabelGray
                        )
                    },
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
                        onEvent(AuthorizationEvent.ChangePassword(newText))
                    },
                    label = {
                        Text(
                            text = "Пароль",
                            color = if (state.errorPassword) SideOrange else TextLabelGray
                        )
                    },


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
                        imeAction = ImeAction.Done
                    )
                )


                Button(
                    enabled = state.userName.isNotEmpty() && state.password.isNotEmpty(),
                    shape = ShapeDefaults.Small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    onClick = {
                        onEvent.invoke(AuthorizationEvent.Login)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = animateColorAsState(
                            targetValue = if (state.userName.isNotEmpty()
                                    .and(state.password.isNotEmpty())
                            ) MainBlue else MainGray,
                            animationSpec = tween(100, easing = LinearEasing), label = ""
                        ).value

                    )
                ) {

                    if (!state.isLoading) Text(
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
                    else {
                        Spacer(modifier = Modifier.height(37.dp))
                        LoadingAnimation(circleColor = Color.White , circleSize = 12.dp)
                        Spacer(modifier = Modifier.height(37.dp))


                    }                }

                Text(modifier = Modifier
                    .singleClick { }
                    .align(CenterHorizontally)
                    .padding(top = 12.dp),
                    text = "Забыли пароль?",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF85899F),
                        textAlign = TextAlign.Center,
                    )
                )

                Spacer(modifier = Modifier.height(100.dp))


            }

        }
        Button(
            shape = ShapeDefaults.Small,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 32.dp, end = 32.dp, bottom = 32.dp),
            onClick = { navHostController.navigate(Screens.Registration.route) },
            colors = ButtonDefaults.buttonColors(SecondGray)
        ) {

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Зарегистрироваться",

                // SF Pro/Bold/18
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 24.3.sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                    fontWeight = FontWeight(700),
                    color = MainBlue,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.42.sp,
                )
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun test() {
    AuthorizationDisplay(navHostController = rememberNavController()   , onEvent = {} , state = AuthorizationState.Content("" ,""))
}