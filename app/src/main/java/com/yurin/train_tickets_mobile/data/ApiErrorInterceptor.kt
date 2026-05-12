package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.domain.entity.error.ApiException
import com.yurin.train_tickets_mobile.domain.entity.error.AppException
import com.yurin.train_tickets_mobile.domain.entity.error.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class ApiErrorInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        runCatching { chain.proceed(chain.request()) }.mapCatching { response ->
            response.takeIf {
                it.isSuccessful
            } ?: throw createException(response)
        }.getOrThrow()

    private fun createException(response: Response): AppException = when (response.code) {
        HTTP_BAD_REQUEST -> {
            val jObject = JSONObject(response.body.string())
            val code = jObject.getInt("code")
            val message = jObject.getString("message")

            when (code) {
                1002 -> UnauthorizedException(message)
                1004 -> UnauthorizedException(message)
                else -> ApiException(message)
            }
        }

        HTTP_UNAUTHORIZED -> {
            UnauthorizedException("Вы не авторизованы.")
        }

        HTTP_INTERNAL_ERROR -> {
            ApiException("Ошибка сервера. Попробуйте позже.")
        }

        else -> ApiException(response.message)
    }

}