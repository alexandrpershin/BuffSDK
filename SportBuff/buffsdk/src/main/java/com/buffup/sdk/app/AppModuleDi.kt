package com.buffup.sdk.app

import com.buffup.sdk.api.BackendApiFactory
import com.buffup.sdk.api.createApiService
import com.buffup.sdk.api.repository.BuffRepository
import com.buffup.sdk.api.repository.repositoryImpl.BuffRepositoryImpl
import com.buffup.sdk.api.service.BuffService
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Provides app dependencies with [Koin]
 * */

 val appModule: Module = module {

    //Retrofit
    single<Retrofit> { BackendApiFactory().provideRetrofit(get()) }

    //Repository
    single<BuffRepository> { BuffRepositoryImpl(get()) }

    //API service
    single<BuffService> { createApiService<BuffService>(get()) }
}