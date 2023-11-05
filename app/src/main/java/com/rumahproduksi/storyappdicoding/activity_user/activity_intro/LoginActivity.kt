package com.rumahproduksi.storyappdicoding.activity_user.activity_intro

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.rumahproduksi.storyappdicoding.MainActivity
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server.ApiClient
import com.rumahproduksi.storyappdicoding.activity_user.model_user.LoginRegisterModel
import com.rumahproduksi.storyappdicoding.activity_utils.database.MessagesUtils
import com.rumahproduksi.storyappdicoding.activity_utils.model_view.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.ActivityIntroBinding
import com.rumahproduksi.storyappdicoding.databinding.ActivityLoginBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val binding : ActivityLoginBinding  by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var loginregisterModel: LoginRegisterModel
    private lateinit var prefersManager: PreferManager
    private var loginJobs: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        Animation()

        Loginhome()

        binding.iconBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        prefersManager = PreferManager(this)
        val dataRepository = RepositoryClass(ApiClient.getInstance())
        loginregisterModel = ViewModelProvider(
            this,
            FactoryViewModel(dataRepository)
        )[LoginRegisterModel::class.java]


    }

    @Suppress("DEPRECATION")
    private fun Loginhome() {
        binding.bottonLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                MessagesUtils.setMessage(this, getString(R.string.alert_warning_input))
            } else {
                showLoading(true)
                lifecycle.coroutineScope.launchWhenResumed {
                    if (loginJobs.isActive) loginJobs.cancel()
                    loginJobs = launch {
                        loginregisterModel.login(email, password).collect{ result ->
                            when (result) {
                                is NetworkResults.Success -> {
                                    prefersManager.exampleBoolean = !result.data?.error!!
                                    prefersManager.token = result.data.result.token
                                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                    showLoading(false)

                                }
                                is NetworkResults.Loading -> {
                                    showLoading(true)
                                }

                                is NetworkResults.Error -> {
                                    MessagesUtils.setMessage(this@LoginActivity, resources.getString(R.string.check_email))
                                    showLoading(false)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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




}