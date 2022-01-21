package com.demo.moviebag.data

import com.demo.moviebag.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory((MoshiConverterFactory.create()))
            .client(client)
            .build()
    }

    val moviesAPI: MoviesAPI by lazy {
        retrofit.create(MoviesAPI::class.java)
    }
}