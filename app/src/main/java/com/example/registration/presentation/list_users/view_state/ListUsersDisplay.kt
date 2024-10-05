package com.example.registration.presentation.list_users.view_state

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.data.models.User
import com.example.registration.presentation.list_users.view_models.ListUsersEvent
import com.example.registration.presentation.list_users.view_models.ListUsersState
import com.example.registration.presentation.navigation.Screens
import com.example.registration.presentation.utils.formatDateString
import com.example.registration.ui.theme.MainBlack
import com.example.registration.ui.theme.MainBlue
import com.example.registration.ui.theme.MainGray

@Composable
fun ListUsersDisplay(
    state: ListUsersState.Content,
    navHostController: NavHostController,
    onEvent: (ListUsersEvent) -> Unit,
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
                .padding(top = 16.dp, bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.users, key = { it.id }) { user ->
                UserItem(
                    user = user,
                    currentUser = state.currentUser,
                    onDelete = {
                        onEvent(ListUsersEvent.DeleteUser(user.id))
                    }
                )
            }
        }

        Button(
            onClick = {
                onEvent(ListUsersEvent.SaveToken(false))
                navHostController.navigate(Screens.NoAuthorization.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF990000)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Выйти из аккаунта",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.sf_pro_bold)),
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun UserItem(
    user: User,
    currentUser: User?,
    onDelete: () -> Unit
) {
    val canDelete = currentUser != null && user.id != currentUser.id && user.registrationDate > currentUser.registrationDate
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            imageUri.value = it
        }
    }
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
            if (!user.avatarUri.isNullOrEmpty()) {
                AsyncImage(
                    model = user.avatarUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
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
                    text = formatDateString(user.dateOfBirth),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_medium)),
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF666666)
                    )
                )
            }
            Button(
                onClick = {
                    if (canDelete) {
                        onDelete()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canDelete) MainBlue else MainGray
                ),
                enabled = canDelete
            ) {
                Text(
                    text = "Удалить",
                    color = Color.White
                )
            }
        }
    }
}