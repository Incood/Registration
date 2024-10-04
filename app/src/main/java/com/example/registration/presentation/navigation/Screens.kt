package com.example.registration.presentation.navigation

sealed class Screens(val route: String, val name: String = "") {

    data object NoAuthorization : Screens("no_auth", "Авторизация")

    data object Registration : Screens("registration", "Регистрация")

    data object Authorization : Screens("auth", "Авторизация")

    data object ListUsers : Screens("users_screen")
}