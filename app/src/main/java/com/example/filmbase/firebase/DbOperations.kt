package com.example.filmbase.firebase


import com.example.filmbase.firebase.classes.Film
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DbOperations {
    private val database : DatabaseReference = Firebase.database(
        "https://filmbase-71578-default-rtdb.europe-west1.firebasedatabase.app/"
    ).reference

    // adding film to personal list of user (also changing seen/unseen)
    fun addFilmToUser(userId: String,film: Film){
        database.child("users")
            .child(userId)
            .child("films")
            .child(film.id.toString())
            .setValue(film)
    }

    fun changeSeen(userId: String, filmId : String, bool: Boolean){
        database.child("users")
            .child(userId)
            .child("films")
            .child(filmId)
            .child("seen")
            .setValue(bool)
    }

    fun deleteFilm(userId: String,filmId: String){
        database.child("users")
            .child(userId)
            .child("films")
            .child(filmId)
            .removeValue()
    }

}