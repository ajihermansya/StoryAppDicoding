package com.rumahproduksi.storyappdicoding.ui_user.activity_intro

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.coroutineScope
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.view_model.ViewModelSignUp
import com.rumahproduksi.storyappdicoding.preference_activity.FactoryViewModel
import com.rumahproduksi.storyappdicoding.remote_activity.object_message.MessagesUtils
import com.rumahproduksi.storyappdicoding.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {


    private val signUpViewModel by viewModels<ViewModelSignUp> {
        FactoryViewModel.getInstance(this)
    }


    private val binding : ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        Animation()
        //ActionSetups()

        registerAccount()

        binding.tvMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.iconBack.setOnClickListener {
            finish()
        }
    }

    private fun Animation() {
        ObjectAnimator.ofFloat(binding.imageAdaptability, View.TRANSLATION_Y, -20f, 20f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleAnim = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(1000)
        val textnameAnim = ObjectAnimator.ofFloat(binding.name, View.ALPHA, 1f).setDuration(1000)
        val nameTitleAnim = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(1000)
        val messageAnim = ObjectAnimator.ofFloat(binding.texttitleRegister, View.ALPHA, 1f).setDuration(1000)
        val emailTitleAnim = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 1f).setDuration(1000)
        val emailLayoutAnim = ObjectAnimator.ofFloat(binding.emailInput, View.ALPHA, 1f).setDuration(1500)
        val passwordTitleAnim = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(1000)
        val passwordLayoutAnim = ObjectAnimator.ofFloat(binding.textInputLayout, View.ALPHA, 1f).setDuration(1500)
        val registerButtonAnim = ObjectAnimator.ofFloat(binding.bottonRegister, View.ALPHA, 1f).setDuration(1000)
        val daftarButtonAnim = ObjectAnimator.ofFloat(binding.masukLayout, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(registerButtonAnim, daftarButtonAnim)
        }

        AnimatorSet().apply {
            playSequentially(
                titleAnim,
                messageAnim,
                emailTitleAnim,
                emailLayoutAnim,
                passwordTitleAnim,
                daftarButtonAnim,
                passwordLayoutAnim,
                textnameAnim,
                nameTitleAnim,
                registerButtonAnim,
                together
            )
            startDelay = 100
        }.start()
    }


    private fun registerAccount() {
        binding.apply {
            bottonRegister.setOnClickListener {
                val name = nameEditText.text.toString().trim()
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                if (isValidInput(name, email, password)) {
                    performRegistration(name, email, password)
                } else {
                    displayInputWarning()
                }
            }
        }
    }

    private fun isValidInput(name: String, email: String, password: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    private fun performRegistration(name: String, email: String, password: String) {
        showLoading(true)
        lifecycle.coroutineScope.launchWhenResumed {
            try {
                val response = signUpViewModel.register(name, email, password)
                if (response.error == false) {
                    showLoading(false)
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    MessagesUtils.setMessage(this@RegisterActivity, getString(R.string.success_register))
                    finish()
                } else {
                    response.message?.let { MessagesUtils.setMessage(this@RegisterActivity, it) }
                    showLoading(false)
                }
            } catch (e: Exception) {
                displayErrorMessage(getString(R.string.error_register))
                showLoading(false)
            }
        }
    }


    private fun displayInputWarning() {
        MessagesUtils.setMessage(this@RegisterActivity, getString(R.string.warning_input))
    }

    private fun displayErrorMessage(message: String) {
        MessagesUtils.setMessage(this@RegisterActivity, message)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}