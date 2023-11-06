package com.rumahproduksi.storyappdicoding.activity_user.activity_intro

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server.ApiClient
import com.rumahproduksi.storyappdicoding.activity_user.model_user.LoginRegisterModel
import com.rumahproduksi.storyappdicoding.activity_utils.database.MessagesUtils
import com.rumahproduksi.storyappdicoding.activity_utils.model_view.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private val binding : ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private lateinit var loginregisterModel: LoginRegisterModel
    private var regisJobs: Job = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        Animation()
        val classRepository = RepositoryClass(ApiClient.getInstance())
        loginregisterModel =
            ViewModelProvider(this, FactoryViewModel(classRepository))[LoginRegisterModel::class.java]

        regisAccount()

        binding.tvMasuk.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.iconBack.setOnClickListener {
            finish()
        }
    }


    @Suppress("DEPRECATION")
    private fun regisAccount() {
        binding.apply {
            bottonRegister.setOnClickListener {
                val name = nameEditText.text.toString().trim()
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                if (isValidInput(name, email, password)) {
                    performRegistration(name, email, password)
                } else {
                    MessagesUtils.setMessage(this@RegisterActivity, getString(R.string.warning_input))
                }
            }
        }
    }

    //clean code -> percobaan
    private fun isValidInput(name: String, email: String, password: String): Boolean {
        return !(name.isEmpty() || email.isEmpty() || password.isEmpty())
    }

    private fun performRegistration(name: String, email: String, password: String) {
        showLoading(true)
        lifecycle.coroutineScope.launchWhenResumed {
            if (regisJobs.isActive) regisJobs.cancel()
            regisJobs = launch {
                loginregisterModel.register(name, email, password).collect { result ->
                    when (result) {
                        is NetworkResults.Success -> {
                            showLoading(false)
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            MessagesUtils.setMessage(this@RegisterActivity, getString(R.string.success_register))
                            finish()
                        }
                        is NetworkResults.Loading -> {
                            showLoading(true)
                        }
                        is NetworkResults.Error -> {
                            MessagesUtils.setMessage(this@RegisterActivity, resources.getString(R.string.error_register))
                            showLoading(false)
                        }
                    }
                }
            }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}