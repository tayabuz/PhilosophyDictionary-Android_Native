package com.philosophyDictionary.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.philosophyDictionary.Term

@Dao

interface TermDao {
    @Query("SELECT * from Terms")
    fun getAll(): List<Term>

    @Insert(onConflict = REPLACE)
    fun insert(term: Term)

    @Query("DELETE from Terms")
    fun deleteAll()

    @Delete
    fun delete(term: Term)

    @Query("SELECT * FROM Terms WHERE definition = :Definition")
    fun getTerm(Definition: String): Term?

    @Transaction
    fun insertIfExistDelete(term: Term){
        val termInDatabase = getTerm(term.Definition)
        if(termInDatabase == null){
            insert(term)
        }
        else{
            delete(term)
        }
    }

}