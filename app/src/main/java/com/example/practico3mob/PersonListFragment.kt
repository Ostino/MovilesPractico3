package com.example.practico3mob

import PersonViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels



class PersonListFragment : Fragment() {

    private val viewModel: PersonViewModel by activityViewModels()
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)

        listView = view.findViewById(R.id.list_view)

        viewModel.loadInitialPersonList()

        /*viewModel.personList.observe(viewLifecycleOwner) { persons ->

            viewModel.likedPersons.observe(viewLifecycleOwner) { liked ->
                val filteredList = persons.filterNot { liked.contains(it) }
                listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList)
            }
        }*/
        viewModel.personList.observe(viewLifecycleOwner) { persons ->
            val likedPersons = viewModel.likedPersons.value ?: emptySet()
            val dislikedPersons = viewModel.dislikedPersons.value ?: emptySet()

            val filteredList = persons.filterNot { likedPersons.contains(it) || dislikedPersons.contains(it) }

            listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, filteredList)
        }

        val buttonMyLikes: Button = view.findViewById(R.id.button_my_likes)
        buttonMyLikes.setOnClickListener {
            val likesFragment = LikesFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, likesFragment)
                .addToBackStack(null)
                .commit()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedPerson = viewModel.personList.value?.get(position)
            if (selectedPerson != null) {
                val photos = viewModel.getPersonPhotos(selectedPerson)

                val personDetailFragment = PersonDetailFragment.newInstance(selectedPerson, photos)

                personDetailFragment.setInteractionListener(object : PersonDetailFragment.PersonInteractionListener {
                    override fun onLike(person: String) {
                        viewModel.likePerson(person)
                    }

                    override fun onDislike(person: String) {
                        viewModel.dislikePerson(person)
                    }
                })

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, personDetailFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Log.e("PersonListFragment", "Persona seleccionada es nula")
            }
        }
        return view
    }
}
