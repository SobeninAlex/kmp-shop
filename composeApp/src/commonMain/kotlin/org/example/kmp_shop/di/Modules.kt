package org.example.kmp_shop.di

import io.ktor.client.HttpClient
import org.example.kmp_shop.data.HttpClientFactory
import org.example.kmp_shop.data.repository_impl.ShoppingRepositoryImpl
import org.example.kmp_shop.domain.repository.ShoppingRepository
import org.example.kmp_shop.presentation.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val shareModule = module {
    single<HttpClient> {
        HttpClientFactory.create(
            httpClientEngine = get()
        )
    }

//    single<ShoppingRepository> {
//        ShoppingRepositoryImpl(
//            httpClient = get()
//        )
//    }
    singleOf(::ShoppingRepositoryImpl).bind<ShoppingRepository>()

//    viewModel {
//        HomeViewModel(
//            repository = get()
//        )
//    }
    viewModelOf(::HomeViewModel)
}
