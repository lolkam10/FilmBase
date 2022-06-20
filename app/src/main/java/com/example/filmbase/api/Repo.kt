package com.example.filmbase.api

import com.example.filmbase.api.dataClasses.FilmLong
import com.example.filmbase.api.dataClasses.FilmShort
import com.example.filmbase.api.dataClasses.FilmShortList
import retrofit2.awaitResponse

class Repo {
    companion object{
        suspend fun getListOfFilms(apikey:String,title:String):FilmShortList?{
            return RetrofitInstance.api.getListOfFilms(apikey,title).awaitResponse().body()
        }
        suspend fun getFilm(apikey:String,imdbID:String):FilmLong?{
            return RetrofitInstance.api.getFilm(apikey,imdbID).awaitResponse().body()
        }
    }

}