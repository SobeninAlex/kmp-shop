package org.example.kmp_shop.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    declaration: KoinAppDeclaration? = null
) {
    startKoin {
        declaration?.invoke(this)
        modules(
            shareModule,
            platformModule,
        )
    }
}