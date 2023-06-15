package com.c23ps422.reclothes.api

import android.content.Context
import com.c23ps422.reclothes.BuildConfig
import com.c23ps422.reclothes.api.ApiConfig.Companion.getApiService
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.screen.login.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class is responsible for configuring and providing an instance of the ApiService,
 * which handles all API calls in the application.
 *
 * The class makes use of Retrofit to construct the ApiService. OkHttpClient is used with
 * an authentication and logging interceptors. The authentication interceptor injects the
 * "Authorization" header into all outgoing requests, taking the token from the saved preferences.
 * The logging interceptor logs the details of the HTTP request and response.
 * It's active only when the application is in debug mode.
 *
 * @property getApiService a companion object function that takes a Context as an argument
 * and returns an instance of the ApiService. The context is used to fetch the
 * authentication token stored in the ReClothesPreference (which uses DataStore).
 */
class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val pref = ReClothesPreference.getInstance(context.dataStore)

            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                runBlocking(Dispatchers.IO) {
                    val token = pref.getToken().firstOrNull()
                    val requestHeaders = req.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(requestHeaders)
                }
            }

            val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}