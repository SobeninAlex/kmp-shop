package org.example.kmp_shop.di

import io.ktor.client.HttpClient
import org.example.kmp_shop.data.network.HttpClientFactory
import org.example.kmp_shop.data.network.repository_impl.ShoppingRepositoryImpl
import org.example.kmp_shop.domain.repository.ShoppingRepository
import org.example.kmp_shop.presentation.home.HomeViewModel
import org.example.kmp_shop.presentation.product.ProductDetailViewModel
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

    /** предоставить реализацию интерфейса*/
//    single<ShoppingRepository> {
//        ShoppingRepositoryImpl(
//            httpClient = get()
//        )
//    }
    /** если все павраметры может предоставить koin, то можно сделать короче */
    singleOf(::ShoppingRepositoryImpl).bind<ShoppingRepository>()

    /** реализация viewModel */
//    viewModel {
//        HomeViewModel(
//            repository = get()
//        )
//    }
    /** то же самое т.к. все может предоставить koin то можно короче */
    viewModelOf(::HomeViewModel)

    /** так как productId надо предоставлять самому то вот так */
    viewModel { (productId: Int) ->
        ProductDetailViewModel(
            repository = get(),
            productId = productId,
        )
    }
}
