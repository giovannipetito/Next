/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    // for whom needs a context injected (model)

    @Provides
    @Singleton
    fun provideContext(): Context = app
}