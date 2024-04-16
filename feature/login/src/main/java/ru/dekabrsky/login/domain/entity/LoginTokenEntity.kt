package ru.dekabrsky.login.domain.entity

class LoginTokenEntity(
    val accessToken: String,
    val expiresIn: Int,
    val refreshToken: String
)