package com.yurin.train_tickets_mobile.data

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthTokenInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addAuthTokenIfExist()
            .build()

        return chain.proceed(request)
    }

    private fun Request.Builder.addAuthTokenIfExist(): Request.Builder {
        val token = runBlocking { DataStoreManager.getStringValue(context) }
        return if (token != null) {
            this.addHeader("authorization", "Bearer $token")
        } else {
            this
        }
    }

}