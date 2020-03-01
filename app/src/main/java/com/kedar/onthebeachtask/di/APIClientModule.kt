package com.kedar.onthebeachtask.di

import com.kedar.onthebeachtask.data.APIClient
import com.kedar.onthebeachtask.data.APIClientInterface
import dagger.Binds
import dagger.Module

@Module
abstract class APIClientModule{

    @Binds
    abstract fun provideApiClient(apiClient:APIClient):APIClientInterface
}