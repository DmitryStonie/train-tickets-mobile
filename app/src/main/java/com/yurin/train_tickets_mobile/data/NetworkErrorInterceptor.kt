package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.domain.entity.error.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = try {
        chain.proceed(chain.request())
    } catch (e: Exception) {
        throw when (e) {

            is UnknownHostException -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }

            is ConnectionShutdownException -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }

            is ConnectException -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }

            is ProtocolException -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }

            is IOException -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }

            is IllegalStateException -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }

            else -> {
                NetworkException("Ошибка подключения. Попробуйте позже.")
            }
        }
    }
}