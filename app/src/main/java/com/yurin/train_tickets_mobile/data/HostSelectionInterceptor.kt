package com.yurin.train_tickets_mobile.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HostSelectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val reqUrl: String = request.url.host

        val host = runBlocking { DataStoreManager.getBaseUrl(context) }
        if (host != null) {
            val newUrl: HttpUrl = request.url.newBuilder()
                .host(host)
                .build()
            Log.d("Hosst", newUrl.toString())
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(request)
    }
}