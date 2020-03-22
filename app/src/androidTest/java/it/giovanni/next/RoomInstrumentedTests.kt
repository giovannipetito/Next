/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import it.giovanni.next.rubrica.db.AppDatabase
import it.giovanni.next.rubrica.db.Contact
import it.giovanni.next.rubrica.extensions.observeOnce
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomInstrumentedTests {

    private lateinit var db: AppDatabase

    // to execute each task synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        db = AppDatabase.getTestInstance(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    @Throws(Error::class)
    fun checkDatabaseOperations() {

        var ozzy = Contact(
            0,
            "Ozzy",
            "Osbourne",
            "+66 66 666666"
        )

        // checking room size is zero at the beginning

        db.contacts().getAll().observeOnce {
            Assert.assertEquals(it.size, 0)
        }

        // checking insertions are working

        Assert.assertNotEquals(db.contacts().insertOrUpdate(ozzy), -1)

        // checking room size respects the insert

        db.contacts().getAll().observeOnce {
            Assert.assertNotNull(it)
            Assert.assertEquals(it.size, 1)
            ozzy = it[0]
        }

        // checking updates are working

        ozzy.last = "Schwarzenegger"

        db.contacts().insertOrUpdate(ozzy)

        // checking room size respects the update

        db.contacts().getAll().observeOnce {
            Assert.assertEquals(it.size, 1)
        }
    }
}