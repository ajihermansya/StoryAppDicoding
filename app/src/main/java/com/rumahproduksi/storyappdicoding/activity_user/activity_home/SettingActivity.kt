package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryList
import com.rumahproduksi.storyappdicoding.activity_user.activity_intro.LoginActivity
import com.rumahproduksi.storyappdicoding.activity_utils.database.MessagesUtils
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.ActivitySettingBinding


class SettingActivity : AppCompatActivity() {
    private val binding : ActivitySettingBinding by lazy {
        ActivitySettingBinding.inflate(layoutInflater)
    }
    private lateinit var prefersManager: PreferManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
         prefersManager = PreferManager(this)
        val name = binding.root.findViewById<TextView>(R.id.userName)
        val email = binding.root.findViewById<TextView>(R.id.email_card)
        val language = binding.root.findViewById<Button>(R.id.botton_language)


        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }


        binding.tvLogout.setOnClickListener(logout())

        binding.iconBack.setOnClickListener { finish() }


    }

    private fun logout(): View.OnClickListener {
        return View.OnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle(resources.getString(R.string.log_out))
            dialog.setMessage(getString(R.string.are_you_sure))
            dialog.setPositiveButton(getString(R.string.yes)) { _, _ ->
                prefersManager.clear()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                MessagesUtils.setMessage(this, getString(R.string.log_out_warning))
            }
            dialog.setNegativeButton(getString(R.string.no)) { _, _ ->
                MessagesUtils.setMessage(this, getString(R.string.not_out))
            }
            dialog.show()
        }
    }



}