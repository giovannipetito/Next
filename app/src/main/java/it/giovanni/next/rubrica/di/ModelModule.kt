/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.di

import android.content.Context
import dagger.Module
import dagger.Provides
import it.giovanni.next.rubrica.model.Model
import javax.inject.Singleton

@Module
class ModelModule {

    // for whom needs a model injected (presenters)

    @Provides
    @Singleton
    fun provideModel(context: Context): Model = Model(context)
}