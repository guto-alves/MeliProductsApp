package com.example.meliproducts.di

import android.content.Context
import com.example.meliproducts.data.repository.LocalProductRepository
import com.example.meliproducts.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProductRepository(
        @ApplicationContext context: Context
    ): ProductRepository = LocalProductRepository(context)
}

