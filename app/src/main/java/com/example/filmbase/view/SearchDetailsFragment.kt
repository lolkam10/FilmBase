package com.example.filmbase.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.filmbase.R
import com.example.filmbase.firebase.DbOperations
import com.example.filmbase.firebase.classes.Film
import com.example.filmbase.viewModels.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SearchDetailsFragment :Fragment() {

    private lateinit var vm: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Details"
        val filmID = arguments?.getString("filmID")?:"brakID"
        vm = ViewModelProvider(this).get(MyViewModel::class.java)
        vm.get_film(filmID)
        return inflater.inflate(R.layout.fragment_search_details,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val poster = view.findViewById<ImageView>(R.id.iv_posterS)
        val actors = view.findViewById<TextView>(R.id.tv_actorsS)
        val title = view.findViewById<TextView>(R.id.tv_titleS)
        val year = view.findViewById<TextView>(R.id.tv_yearS)
        val genre = view.findViewById<TextView>(R.id.tv_genreS)
        val plot = view.findViewById<TextView>(R.id.tv_plotS)
        val bt_add_to_library = view.findViewById<Button>(R.id.bt_add_to_library)

        vm.currentFilm.observe(viewLifecycleOwner) { film ->
            actors.text = film.Actors
            title.text = film.Title
            year.text = film.Year.toString()
            genre.text = film.Genre
            plot.text = film.Plot
            //setting poster
            Picasso.get().load(film.Poster).into(poster)
        }
        bt_add_to_library.setOnClickListener {
            if(vm.currentFilm.value != null){
                val film = Film(
                    vm.currentFilm.value?.imdbID,
                    vm.currentFilm.value?.Title,
                    vm.currentFilm.value?.Year,
                    vm.currentFilm.value?.Genre,
                    vm.currentFilm.value?.Director,
                    vm.currentFilm.value?.Actors,
                    vm.currentFilm.value?.Plot,
                    vm.currentFilm.value?.Poster,
                    false
                )
                DbOperations().addFilmToUser(FirebaseAuth.getInstance().uid!!,film)
                findNavController().navigateUp()
            }else{
                Log.i("My_warning","Something gone wrong")
            }
        }
    }
}