package com.philosophyDictionary

import android.content.Context
import org.simpleframework.xml.core.Persister


object ContentManager {
    var position = -1

    private var terms: ArrayOfTerm? = null
    fun getInstance(context: Context): ArrayOfTerm? {
        if(terms == null) {
            val serializer = Persister()
            val inputXml = context.resources.openRawResource(R.raw.dictionary_resourse)
            terms = serializer.read(ArrayOfTerm::class.java, inputXml)
        }
        return terms
    }
}