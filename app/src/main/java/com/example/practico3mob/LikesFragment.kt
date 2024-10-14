package com.example.practico3mob

import PersonViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class LikesFragment : Fragment() {

    private val viewModel: PersonViewModel by activityViewModels()
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_likes, container, false)

        listView = view.findViewById(R.id.list_view)

        viewModel.likedPersons.observe(viewLifecycleOwner) { liked ->
            val likedList = liked.toList()
            listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, likedList)
        }
        return view
    }
}
