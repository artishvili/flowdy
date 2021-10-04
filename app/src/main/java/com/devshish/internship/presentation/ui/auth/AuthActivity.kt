package com.devshish.internship.presentation.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devshish.internship.databinding.ActivityAuthBinding
import com.devshish.internship.presentation.ui.main.utils.viewBinding
import com.devshish.internship.presentation.ui.web.WebActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAuthBinding::inflate)
    private val viewModel: AuthViewModel by viewModel()

    private var link: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnSignUp.setOnClickListener { viewModel.requestAuthentication() }
        }

        with(viewModel) {
            requestAuth.observe(this@AuthActivity) {
                link = it
            }

            onAuthenticateClick.observe(this@AuthActivity) {
                val browserIntent = Intent(this@AuthActivity, WebActivity::class.java)
                browserIntent.putExtra("LINK", link.toString())
                startActivity(browserIntent)
            }
        }
    }
}
