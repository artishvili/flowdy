package com.devshish.internship.presentation.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.devshish.internship.R
import com.devshish.internship.databinding.ActivityMainBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModel()
    private lateinit var navController: NavController

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.entries.forEach {
                Timber.i("Permission ${it.key} granted: ${it.value}")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissionLauncher.launch(
            arrayOf(
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE
            )
        )

        with(binding) {
            ivToggle.setOnClickListener { viewModel.toggle() }

            clPlayerBar.setOnClickListener { viewModel.onPlayerClick() }

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()

            binding.bottomNavView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splashFragment,
                    R.id.authFragment,
                    R.id.webFragment,
                    R.id.playerFragment -> binding.bottomNavView.isVisible = true
                    else -> binding.bottomNavView.isVisible = true
                }
            }
        }

        with(viewModel) {
            songToPlay.observe(this@MainActivity) { song ->
                setupPlayerBar(song)
                binding.progressIndicator.max = song.duration
            }

            isPlaying.observe(this@MainActivity) { isPlaying ->
                if (isPlaying) {
                    binding.ivToggle.setImageResource(R.drawable.ic_pause)
                } else {
                    binding.ivToggle.setImageResource(R.drawable.ic_play)
                }
            }

            currentPosition.observe(this@MainActivity) { position ->
                binding.progressIndicator.setProgressCompat(position.toInt(), true)
            }

            navigationEvent.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let {
                    navController.navigate(R.id.action_global_playerFragment)
                }
            }

            playerBarVisibility.observe(this@MainActivity) { visibility ->
                binding.clPlayerBar.isVisible = visibility
            }
        }
    }

    private fun setupPlayerBar(song: Song) {
        binding.apply {
            tvTitle.text = song.title
            tvArtist.text = song.artist

            Glide.with(this@MainActivity)
                .load(song.imageUri)
                .placeholder(R.color.black)
                .into(ivSongCover)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.connect()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregister()
    }
}
