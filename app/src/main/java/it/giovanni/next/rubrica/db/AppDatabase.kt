/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contacts(): ContactsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        // we assure the instance will not be requested from multiple threads in the same moment
        private val LOCK = Any()

        // instance getter

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "talkalot-contacts-room"
                ).build().also { instance = it }
            }
        }

        // instance getter for testing

        fun getTestInstance(context: Context): AppDatabase {
            return instance ?: Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase::class.java
            ).allowMainThreadQueries().build().also { instance = it }
        }
    }
}