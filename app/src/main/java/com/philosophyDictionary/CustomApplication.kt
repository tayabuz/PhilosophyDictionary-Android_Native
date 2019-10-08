package com.philosophyDictionary

import android.app.Application
import com.philosophyDictionary.database.TermDao
import com.philosophyDictionary.database.TermDatabase

class CustomApplication: Application() {


    override fun onCreate() {
        fullTerms = ContentManager.getInstance(this)!!.terms!!
        termDao = TermDatabase.getInstance(this)!!.termDao()
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var fullTerms: List<Term>
        lateinit var termDao: TermDao
        lateinit var instance: CustomApplication
    }
}