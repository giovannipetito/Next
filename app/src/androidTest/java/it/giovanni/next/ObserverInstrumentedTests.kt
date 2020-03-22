/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.giovanni.next.rubrica.extensions.observeOnce
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ObserverInstrumentedTests {

    // to execute each task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun checkOnceObserver() {

        // we initialize a MutableLiveData on an Int, with value 0

        val value: MutableLiveData<Int> = MutableLiveData(0)

        // we register a "once time observer", expecting an equality with 0
        // this observer will be called instantly with value = 0

        value.observeOnce {
            Assert.assertEquals(it, 0)
        }

        // will be called again with value = 1, or is it a real "once-time-observer"?

        value.value = 1
    }
}