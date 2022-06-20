package com.example.filmbase.api.dataClasses

data class FilmShortList(
    val Search : List<FilmShort>,
    val totalResults : Int,
    val Response : Boolean
)
