/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.di

import it.giovanni.next.rubrica.view.detail.DetailFragment
import dagger.Component
import it.giovanni.next.rubrica.view.master.MasterFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        PresenterModule::class,
        ModelModule::class]
)
interface AppComponent {

    // we need to inject only two fragments: master and detail

    fun inject(target: MasterFragment)

    fun inject(target: DetailFragment)
}