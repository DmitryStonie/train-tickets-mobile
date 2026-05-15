package com.yurin.train_tickets_mobile.data

import android.content.Context
import com.yurin.train_tickets_mobile.data.model.Login
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class AuthRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val apiService: ApiService,
) {

    suspend fun login(username: String, password: String) {
        val response = apiService.login(Login(username, password))
        DataStoreManager.saveValue(context, response.accessToken)
    }

    suspend fun checkUserLogin(): Boolean {
        return DataStoreManager.getStringValue(context) != null
    }

    suspend fun setBaseUrl(url: String) {
        DataStoreManager.setBaseUrl(context,url)
    }
}