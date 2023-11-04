package com.rumahproduksi.storyappdicoding.activity_user

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import com.rumahproduksi.storyappdicoding.MainActivity
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val preferManager = PreferManager(this)

        lifecycleScope.launch {
            delay(2000)
            val intent = if(preferManager.exampleBoolean) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, IntroActivity::class.java)
            }
            startActivity(intent)
            finish()
        }



    }

}