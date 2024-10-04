package com.example.registration.presentation.list_users.view_state

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.registration.R
import com.example.registration.data.User
import com.example.registration.presentation.list_users.view_models.ListUsersEvent
import com.example.registration.presentation.list_users.view_models.ListUsersState
import com.example.registration.ui.theme.MainBlack
import com.example.registration.ui.theme.MainBlue

@Composable
fun ListUsersDisplay(
    state: ListUsersState.Content,
    navHostController: NavHostController,
    onEvent: (ListUsersEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(ListUsersEvent.ListUsers)
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "Список пользователей",
            style = TextStyle(
                fontSize = 28.sp,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                fontWeight = FontWeight.Bold,
                color = MainBlack
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = 16.dp,
                    bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.users, key = { it.id }) { user ->
                UserItem(user = user, onDelete = {
                    if (user.dateOfBirth > (state.currentUser?.dateOfBirth ?: "")) {
                        onEvent(ListUsersEvent.DeleteUser(user.id))
                    } else {
                        Toast.makeText(context, "Вы не можете удалить этого пользователя", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}

@Composable
fun UserItem(user: User, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_medium)),
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF000000)
                    )
                )
                Text(
                    text = user.dateOfBirth,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_medium)),
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF666666)
                    )
                )
            }
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
            ) {
                Text(
                    text = "Удалить",
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListUsersPREV() {
    ListUsersDisplay(
        state = ListUsersState.Content(users = listOf(User(1, "Иван Иванов","asd","01.01.1990", ""))),
        navHostController = rememberNavController(),
        onEvent = {}
    )
}