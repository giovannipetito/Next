/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.di

import it.giovanni.next.rubrica.view.detail.DetailPresenter
import it.giovanni.next.rubrica.view.master.MasterPresenter
import it.giovanni.next.rubrica.view.master.MasterPresenterImpl
import dagger.Module
import dagger.Provides
import it.giovanni.next.rubrica.model.Model
import it.giovanni.next.rubrica.view.detail.DetailPresenterImpl

@Module
class PresenterModule {

    // presenters-specific injections

    @Provides
    fun provideMasterPresenter(model: Model): MasterPresenter =
        MasterPresenterImpl(model)

    @Provides
    fun provideDetailPresenter(model: Model): DetailPresenter =
        DetailPresenterImpl(model)
}