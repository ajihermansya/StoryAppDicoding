package com.rumahproduksi.storyappdicoding.activity_user.activity_intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import com.rumahproduksi.storyappdicoding.activity_preferences.PreferenceUserManager
import com.rumahproduksi.storyappdicoding.activity_preferences.dataStore
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.MainActivity
import com.rumahproduksi.storyappdicoding.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val preferManager = PreferenceUserManager.getInstance(this.dataStore)

        lifecycleScope.launch {
            delay(2000)
            val intent = if(preferManager.getSession().first().isLogin) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, IntroActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}
