package com.philosophyDictionary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.philosophyDictionary.Term

@Database(entities = [Term::class], version = 1)
abstract class TermDatabase : RoomDatabase(){

    abstract fun termDao(): TermDao



    companion object {
        private var INSTANCE: TermDatabase? = null

        fun getInstance(context: Context): TermDatabase? {
            if (INSTANCE == null) {
                synchronized(TermDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TermDatabase::class.java, "favouriteTerms.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}