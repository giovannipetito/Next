/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import android.app.Application
import android.app.Instrumentation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import it.giovanni.next.rubrica.core.App
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppInstrumentationTests {

    @Test
    fun appIsNotNull() {
        val application: Application = Instrumentation.newApplication(
            App::class.java,
            InstrumentationRegistry.getInstrumentation().targetContext
        )
        Assert.assertNotNull(application)
        Assert.assertTrue(application is App)
        val app: App = application as App
        Assert.assertNotNull(app)
        Assert.assertNotNull(App.component)
    }
}