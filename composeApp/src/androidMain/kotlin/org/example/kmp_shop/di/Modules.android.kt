package org.example.kmp_shop.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.example.kmp_shop.data.database.AppDatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }

        single<AppDatabaseFactory> {
            AppDatabaseFactory(
                context = androidApplication()
            )
        }
    }
