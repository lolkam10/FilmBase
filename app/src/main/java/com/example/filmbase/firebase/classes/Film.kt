package com.example.filmbase.firebase.classes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Film(
    val id:String? = null,
    val title:String? = null,
    val year:Int? = null,
    val genre:String? = null,
    val director: String? = null,
    val actors:String? = null,
    val plot:String? = null,
    val poster:String? = null,
    val seen:Boolean? = null
)
