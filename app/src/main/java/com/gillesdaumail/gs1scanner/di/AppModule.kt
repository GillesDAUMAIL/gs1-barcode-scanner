package com.gillesdaumail.gs1scanner.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module for application-wide dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Dependencies are provided via constructor injection
    // No additional providers needed for current implementation
}