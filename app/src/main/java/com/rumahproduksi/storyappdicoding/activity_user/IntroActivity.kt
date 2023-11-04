package com.rumahproduksi.storyappdicoding.activity_user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.databinding.ActivityIntroBinding
import com.rumahproduksi.storyappdicoding.databinding.ActivitySplashBinding

class IntroActivity : AppCompatActivity() {
    lateinit var binding : ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        Animation()

        binding.bottonMulai.setOnClickListener {
            Toast.makeText(this, "uy", Toast.LENGTH_SHORT).show()
        }


    }

    private fun Animation() {
        ObjectAnimator.ofFloat(binding.imageAdaptability, View.TRANSLATION_X, -40f, 40f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.imageAdaptability, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.textTitle, View.ALPHA, 1f).setDuration(100)
        val desc = ObjectAnimator.ofFloat(binding.textDescripstion, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(title, desc, login)
            start()
        }
    }
}