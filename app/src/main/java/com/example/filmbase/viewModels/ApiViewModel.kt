package com.example.filmbase.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmbase.api.Repo
import com.example.filmbase.api.dataClasses.FilmLong
import com.example.filmbase.api.dataClasses.FilmShort
import com.example.filmbase.api.dataClasses.FilmShortList
import kotlinx.coroutines.launch


const val MY_KEY="bfe80e30"

class MyViewModel : ViewModel() {

    private val _films : MutableLiveData<List<FilmShort>> = MutableLiveData()
    val films : LiveData<List<FilmShort>>
        get() {
            return _films
        }

    private val _currentFilm : MutableLiveData<FilmLong> = MutableLiveData()
    val currentFilm : LiveData<FilmLong>
        get() {
            return _currentFilm
        }

    fun get_films(title: String){
        viewModelScope.launch {
            val filmsList = Repo.getListOfFilms(MY_KEY,title)?.Search
            if (filmsList != null){
                _films.value = filmsList!!
            }
        }
    }

    fun get_film(id:String){
        viewModelScope.launch {
            val film = Repo.getFilm(MY_KEY,id)
            if (film != null){
                _currentFilm.value = film!!
            }
        }
    }
}