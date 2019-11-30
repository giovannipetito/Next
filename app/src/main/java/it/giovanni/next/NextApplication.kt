package it.giovanni.next

import android.app.Application

class NextApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        /*
        val ac: ApplicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent = ac
        */
    }
}