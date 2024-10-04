package com.example.registration.presentation.auth.no_authorizated

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.registration.R
import com.example.registration.presentation.navigation.Screens
import com.example.registration.ui.theme.MainBlack
import com.example.registration.ui.theme.MainBlue

@Composable
fun NoAuthScreen(navHostController: NavHostController) {



    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Вы не авторизированы",

                // SF Pro/Semibold/24
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                    fontWeight = FontWeight(600),
                    color = MainBlack,
                    textAlign = TextAlign.Center,
                )
            )
            Button(
                onClick = { navHostController.navigate(Screens.Registration.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    MainBlue
                ), shape = ShapeDefaults.Small
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
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.42.sp,
                    )
                )
            }
            Button(
                onClick = { navHostController.navigate(Screens.Authorization.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.White
                ), shape = ShapeDefaults.Small, elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Войти",

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
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ZenCar.tech",

                // SF Pro/Medium/16
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF0A123E),
                )
            )
            Divider(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun noAuthPREV() {

    NoAuthScreen(navHostController = rememberNavController())
}


