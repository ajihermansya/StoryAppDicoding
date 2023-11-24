package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_model.ViewModelStory
import com.rumahproduksi.storyappdicoding.activity_preferences.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_remote.message.MessagesUtils
import com.rumahproduksi.storyappdicoding.activity_user.activity_intro.LoginActivity
import com.rumahproduksi.storyappdicoding.databinding.ActivitySettingBinding


class SettingActivity : AppCompatActivity() {


    private val viewModel by viewModels<ViewModelStory> {
        FactoryViewModel.getInstance(this)
    }

   private lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                viewModel.logout()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
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