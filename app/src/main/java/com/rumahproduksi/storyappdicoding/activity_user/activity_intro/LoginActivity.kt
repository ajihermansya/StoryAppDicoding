package com.rumahproduksi.storyappdicoding.activity_user.activity_intro

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.rumahproduksi.storyappdicoding.activity_model.ViewModelLogin
import com.rumahproduksi.storyappdicoding.activity_preferences.ModelUsers
import com.rumahproduksi.storyappdicoding.activity_preferences.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.Results
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.MainActivity
import com.rumahproduksi.storyappdicoding.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<ViewModelLogin> {
        FactoryViewModel.getInstance(this)
    }

    private lateinit var binding : ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        Animation()
        Actionsetups()


        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


    }


    private fun Animation() {
        ObjectAnimator.ofFloat(binding.imageAdaptability, View.TRANSLATION_Y, -20f, 20f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleAnim = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(1000)
        val messageAnim = ObjectAnimator.ofFloat(binding.texttitleLogin, View.ALPHA, 1f).setDuration(1000)
        val emailTitleAnim = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 1f).setDuration(1000)
        val emailLayoutAnim = ObjectAnimator.ofFloat(binding.emailInput, View.ALPHA, 1f).setDuration(1500)
        val passwordTitleAnim = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(1000)
        val passwordLayoutAnim = ObjectAnimator.ofFloat(binding.textInputLayout, View.ALPHA, 1f).setDuration(1500)
        val loginButtonAnim = ObjectAnimator.ofFloat(binding.bottonLogin, View.ALPHA, 1f).setDuration(1000)
        val orTextAnim = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(1000)
        val googleButtonAnim = ObjectAnimator.ofFloat(binding.bottonGoogle, View.ALPHA, 1f).setDuration(1000)
        val registerButtonAnim = ObjectAnimator.ofFloat(binding.registerLayout, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(googleButtonAnim, registerButtonAnim)
        }

        AnimatorSet().apply {
            playSequentially(
                titleAnim,
                messageAnim,
                emailTitleAnim,
                emailLayoutAnim,
                orTextAnim,
                passwordTitleAnim,
                passwordLayoutAnim,
                loginButtonAnim,
                together
            )
            startDelay = 100
        }.start()
    }


    private fun Actionsetups() {
        binding.bottonLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is Results.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Results.Success -> {
                        val data = result.result.loginResult
                        data?.let {
                            viewModel.saveSession(ModelUsers(it.name.toString(), it.userId.toString(), it.token.toString()))
                            binding.progressBar.visibility = View.GONE
                            Intent(this, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(this)
                                finishAffinity()
                            }
                        }
                    }
                    is Results.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}