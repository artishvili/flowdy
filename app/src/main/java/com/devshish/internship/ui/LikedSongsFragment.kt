package com.devshish.internship.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devshish.internship.R
import com.devshish.internship.adapter.ItemSongAdapter
import com.devshish.internship.databinding.FragmentLikedSongsBinding
import com.devshish.internship.model.Song

class LikedSongsFragment : Fragment(R.layout.fragment_liked_songs) {

    private val itemSongAdapter by lazy { ItemSongAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLikedSongsBinding.bind(view)

        binding.rvSongs.apply {
            adapter = itemSongAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val songs = listOf(
            Song("Tell Em", "Cochise, SNOT"),
            Song("Spaceship", "Playboi Carti"),
            Song("Water Glass", "Cannon"),
            Song("Ms. Jackson", "Outkast"),
            Song("Ninety", "Jaden"),
            Song("Dunno", "Mac Miller"),
            Song("DR. WHOEVER", "Amine"),
            Song("Well Travelled", "Masego"),
            Song("Been Around", "Cordae"),
            Song("Trust", "Brent Faiyaz"),
            Song("YEAH RIGHT", "Joji"),
            Song("RIVER ROAD", "Jack Harlow")
        )

        itemSongAdapter.setData(songs)
    }
}
