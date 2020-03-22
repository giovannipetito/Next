/*
 * Copyright (c) 2019 Davide Avagliano
 */

package it.giovanni.next.rubrica.db

import androidx.lifecycle.LiveData
import androidx.room.*
import it.giovanni.next.rubrica.db.Contact

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contact: Contact): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(contact: Contact)

    @Transaction
    fun insertOrUpdate(contact: Contact) {
        val id: Long = insert(contact)
        if (id == -1L) {
            update(contact)
        }
    }
}