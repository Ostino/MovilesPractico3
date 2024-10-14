package com.example.practico3mob

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2


class PersonDetailFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var photos: List<Int>
    private var personInteractionListener: PersonInteractionListener? = null

    companion object {
        private const val ARG_PERSON_NAME = "person_name"
        private const val ARG_PHOTOS = "photos"

        fun newInstance(personName: String, photos: List<Int>): PersonDetailFragment {
            val fragment = PersonDetailFragment()
            val args = Bundle()
            args.putString(ARG_PERSON_NAME, personName)
            args.putIntegerArrayList(ARG_PHOTOS, ArrayList(photos))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_detail, container, false)

        viewPager = view.findViewById(R.id.view_pager_photo)

        val personName = arguments?.getString(ARG_PERSON_NAME)
        photos = arguments?.getIntegerArrayList(ARG_PHOTOS) ?: emptyList()
        if (photos.isNotEmpty()) {
            viewPager.adapter = PhotoPagerAdapter(photos)
        } else {
            Log.e("PersonDetailFragment", "No se encontraron fotos para esta persona")
        }

        viewPager.adapter = PhotoPagerAdapter(photos)

        val buttonLike: Button = view.findViewById(R.id.button_like)
        val buttonDislike: Button = view.findViewById(R.id.button_dislike)

        buttonLike.setOnClickListener {
            personInteractionListener?.onLike(personName ?: "")
        }

        buttonDislike.setOnClickListener {
            personInteractionListener?.onDislike(personName ?: "")
        }

        return view
    }

    fun setInteractionListener(listener: PersonInteractionListener) {
        this.personInteractionListener = listener
    }

    interface PersonInteractionListener {
        fun onLike(person: String)
        fun onDislike(person: String)
    }
}
