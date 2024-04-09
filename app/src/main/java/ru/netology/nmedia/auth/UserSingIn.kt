package ru.netology.nmedia.auth

import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import java.io.IOException

class UserSingIn {
    suspend fun getAuth(login: String, pass: String) {
        try {
            val response = PostsApi.service.updateUser(login, pass)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            AppAuth.getInstance().setAuth(body.id, body.token.toString())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}