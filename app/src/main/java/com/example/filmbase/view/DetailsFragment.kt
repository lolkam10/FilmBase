package com.example.filmbase.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbase.R
import com.example.filmbase.adapters.FilmListHolder
import com.example.filmbase.firebase.DbOperations
import com.example.filmbase.firebase.classes.Film
import com.example.filmbase.viewModels.MyViewModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class DetailsFragment: Fragment() {

    private lateinit var vm:MyViewModel
    private lateinit var database: DatabaseReference
    private lateinit var userID : String
    private lateinit var filmID : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Details"
        filmID = arguments?.getString("filmID")?:"brakID"
        vm = ViewModelProvider(this).get(MyViewModel::class.java)
        vm.get_film(filmID)

        database = Firebase.database(
            "https://filmbase-71578-default-rtdb.europe-west1.firebasedatabase.app/"
        ).reference

        userID = FirebaseAuth.getInstance().uid?:"0000"

        return inflater.inflate(R.layout.fragment_details,container,false)
    }

    //@SuppressLint("ResourceAsColor")
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val poster = view.findViewById<ImageView>(R.id.iv_poster)
        val actors = view.findViewById<TextView>(R.id.tv_actors)
        val title = view.findViewById<TextView>(R.id.tv_title)
        val year = view.findViewById<TextView>(R.id.tv_year)
        val genre = view.findViewById<TextView>(R.id.tv_genre)
        val plot = view.findViewById<TextView>(R.id.tv_plot)
        val status = view.findViewById<TextView>(R.id.tv_status)
        val bt_change_status = view.findViewById<Button>(R.id.bt_change_status)
        val bt_delete = view.findViewById<Button>(R.id.bt_delete)

        //var if_seen :Boolean = false

        database.child("users")
            .child(userID)
            .child("films")
            .child(filmID)
            .child("seen").get().addOnSuccessListener {
                if (it.value as Boolean){
                    status.text = "seen"
                    status.setTextColor(ContextCompat.getColor(requireContext(),R.color.seen))
                }
            }



        vm.currentFilm.observe(viewLifecycleOwner) { film ->
            actors.text = film.Actors
            title.text = film.Title
            year.text = film.Year.toString()
            genre.text = film.Genre
            plot.text = film.Plot

            //setting poster
            Picasso.get().load(film.Poster).into(poster)
        }

        //changing status
        bt_change_status.setOnClickListener {
            if (vm.currentFilm.value != null){
                if (status.text == "seen"){
                    status.text = "not seen"
                    //status.setTextColor(R.color.seen)
                    status.setTextColor(ContextCompat.getColor(requireContext(),R.color.not_seen))
                    DbOperations().changeSeen(userID,vm.currentFilm.value!!.imdbID,false)
                }else{
                    status.text = "seen"
                    status.setTextColor(ContextCompat.getColor(requireContext(),R.color.seen))
                    DbOperations().changeSeen(userID,vm.currentFilm.value!!.imdbID,true)
                }
            }
        }

        //deleting from library
        bt_delete.setOnClickListener {
            if (vm.currentFilm.value != null){
                DbOperations().deleteFilm(userID,vm.currentFilm.value!!.imdbID)
                findNavController().navigateUp()
            }
        }
    }
}