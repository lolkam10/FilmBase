package com.example.filmbase.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmbase.R
import com.example.filmbase.adapters.FilmSearchListAdapter
import com.example.filmbase.viewModels.MyViewModel


class SearchFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var manager: LinearLayoutManager
    private lateinit var adapter : FilmSearchListAdapter
    private lateinit var vm: MyViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Search new films"
        val view = inflater.inflate(R.layout.fragment_search,container,false)

        searchView = view.findViewById(R.id.searchView)

        vm= ViewModelProvider(this).get(MyViewModel::class.java)

        recycler = view.findViewById<RecyclerView>(R.id.rv_search_films)
        recycler.setHasFixedSize(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manager = LinearLayoutManager(view.context)
        recycler.layoutManager = manager

        adapter = FilmSearchListAdapter(vm.films)
        recycler.adapter = adapter

        vm.films.observe(viewLifecycleOwner,{
            adapter.notifyDataSetChanged()
        })

        //vm.get_films("Jack")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!TextUtils.isEmpty(query)){
                    vm.get_films(query!!)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

}