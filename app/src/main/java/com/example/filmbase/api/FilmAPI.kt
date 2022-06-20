package com.example.filmbase.api

import com.example.filmbase.api.dataClasses.FilmLong
import com.example.filmbase.api.dataClasses.FilmShort
import com.example.filmbase.api.dataClasses.FilmShortList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmAPI {
    @GET("?type=movie")
    fun getListOfFilms( @Query("apikey") key : String, @Query("s") title : String) : Call<FilmShortList>

    @GET("?type=movie&plot=full")
    fun getFilm( @Query("apikey") key : String, @Query("i") imdbID : String) : Call<FilmLong>
}