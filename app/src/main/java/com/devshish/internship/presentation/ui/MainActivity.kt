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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.devshish.internship.NavGraphDirections
import com.devshish.internship.R
import com.devshish.internship.databinding.ActivityMainBinding
import com.devshish.internship.domain.model.Song
import com.devshish.internship.presentation.ui.player.PlayerViewModel
import com.devshish.internship.presentation.ui.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val mainViewModel: MainViewModel by viewModel()
    private val playerViewModel: PlayerViewModel by viewModel()

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
            setSupportActionBar(toolbar)

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.searchFragment,
                    R.id.libraryFragment,
                    R.id.profileFragment
                )
            )

            toolbar.setupWithNavController(navController, appBarConfiguration)
            bottomNavView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNavView.isVisible = when (destination.id) {
                    R.id.splashFragment,
                    R.id.authFragment,
                    R.id.webFragment,
                    R.id.playerFragment -> false
                    else -> true
                }

                appBarLayout.isVisible = when (destination.id) {
                    R.id.splashFragment,
                    R.id.authFragment,
                    R.id.webFragment,
                    R.id.profileFragment -> false
                    else -> true
                }
            }

            layoutPlayerBar.ivToggle.setOnClickListener { playerViewModel.toggle() }
            layoutPlayerBar.root.setOnClickListener { mainViewModel.onPlayerClick() }
        }

        with(mainViewModel) {
            navigationEvent.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let {
                    val action = NavGraphDirections.actionGlobalPlayerFragment()
                    navController.navigate(action)
                }
            }
        }

        with(playerViewModel) {
            isPlayerBarVisible.observe(this@MainActivity) { isVisible ->
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    binding.layoutPlayerBar.root.isVisible = when (destination.id) {
                        R.id.splashFragment,
                        R.id.authFragment,
                        R.id.webFragment,
                        R.id.playerFragment -> false
                        else -> isVisible
                    }
                }
            }

            songToPlay.observe(this@MainActivity) { song ->
                setupPlayerBar(song)
            }

            isPlaying.observe(this@MainActivity) { isPlaying ->
                binding.layoutPlayerBar.ivToggle.setImageResource(
                    if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                )
            }

            currentPosition.observe(this@MainActivity) { position ->
                binding.layoutPlayerBar.progressIndicator.setProgressCompat(position, true)
            }
        }
    }

    private fun setupPlayerBar(song: Song) {
        binding.layoutPlayerBar.apply {
            tvTitle.text = song.title
            tvArtist.text = song.artist
            progressIndicator.max = song.duration

            Glide.with(this@MainActivity)
                .load(song.imageUri)
                .placeholder(R.color.black)
                .into(ivCover)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.connect()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.unregister()
    }
}
