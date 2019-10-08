package com.philosophyDictionary

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "ArrayOfTerm", strict = false)
data class ArrayOfTerm @JvmOverloads constructor(
    @field:ElementList(entry = "Term", inline = true)
    @param:ElementList(entry = "Term", inline = true)
    val terms: List<Term>? = null)

@Entity(tableName = "Terms", primaryKeys = ["definition"])
@Root(name = "Term", strict = false)
data class Term  @JvmOverloads constructor(
    @ColumnInfo(name = "definition")
    @field:Element(name = "Definition")
    @param:Element(name = "Definition")
    var Definition: String = "",

    @field:Element(name = "Explanation")
    @param:Element(name = "Explanation")
    @ColumnInfo(name = "explanation")
    var Explanation: String = ""
)


