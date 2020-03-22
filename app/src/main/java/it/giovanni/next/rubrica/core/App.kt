/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.core

import android.app.Application
import it.giovanni.next.rubrica.di.*

class App : Application() {

    companion object {

        // global app component definition
        lateinit var component: AppComponent
    }

    // our app component gets initialized here
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .presenterModule(PresenterModule())
            .modelModule(ModelModule())
            .build()
    }
}