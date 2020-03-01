package com.kedar.onthebeachtask.di

import com.kedar.onthebeachtask.data.APIRetrofitInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class RetrofitModule {

    @Provides
    fun providesRetrofitClient():APIRetrofitInterface{
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://pastebin.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(APIRetrofitInterface::class.java)
    }

}