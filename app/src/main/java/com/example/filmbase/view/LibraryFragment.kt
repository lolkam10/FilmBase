package com.example.filmbase.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbase.R
import com.example.filmbase.adapters.FilmListAdapter
import com.example.filmbase.adapters.FilmListHolder
import com.example.filmbase.firebase.classes.Film
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LibraryFragment() : Fragment() {

    private lateinit var database:DatabaseReference
    private lateinit var recycler: RecyclerView
    private lateinit var manager: LinearLayoutManager
    private lateinit var adapter : FirebaseRecyclerAdapter<Film,FilmListHolder>
    private lateinit var userID : String
    private lateinit var whichQuery : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "My library"
        val view = inflater.inflate(R.layout.fragment_library,container,false)
        setHasOptionsMenu(true)

        whichQuery = arguments?.getString("which_query")?:"all"

        //database reference
        database = Firebase.database(
            "https://filmbase-71578-default-rtdb.europe-west1.firebasedatabase.app/"
        ).reference

        recycler = view.findViewById<RecyclerView>(R.id.rv_my_films)
        recycler.setHasFixedSize(true)
        // logged user ID
        userID = FirebaseAuth.getInstance().uid?:"0000"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val all = view.findViewById<TextView>(R.id.tv_all)
        val seen = view.findViewById<TextView>(R.id.tv_seen)
        val not_seen = view.findViewById<TextView>(R.id.tv_not_seen)

        manager = LinearLayoutManager(view.context)
        recycler.layoutManager = manager

        val query :Query
        // Set up FirebaseRecyclerAdapter with the Query
        if (whichQuery == "all"){
            query = database.child("users")
                .child(userID)
                .child("films").orderByChild("year")
            all.typeface = Typeface.DEFAULT_BOLD
        }else if (whichQuery == "seen"){
            query = database.child("users")
                .child(userID)
                .child("films").orderByChild("seen").startAt(true)
            seen.typeface = Typeface.DEFAULT_BOLD
        }else{
            query = database.child("users")
                .child(userID)
                .child("films").orderByChild("seen").endAt(false)
            not_seen.typeface = Typeface.DEFAULT_BOLD
        }
        val options = FirebaseRecyclerOptions.Builder<Film>()
            .setQuery(query, Film::class.java)
            .build()

        adapter = FilmListAdapter(options)
        recycler.adapter = adapter
        adapter.startListening()

        // floating action button
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_searchFragment)
        }

        all.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_self)
        }
        seen.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("which_query","seen")
            findNavController().navigate(R.id.action_libraryFragment_self,bundle)
        }
        not_seen.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("which_query","not_seen")
            findNavController().navigate(R.id.action_libraryFragment_self,bundle)
        }

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_logout) {
            AuthUI.getInstance().signOut(this.requireContext())
            startActivity(Intent(activity,LoginActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}