package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.fragment.CameraFragment
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.fragment.SettingFragment
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.fragment.StoryFragment
import com.rumahproduksi.storyappdicoding.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainMenuBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        bottomNavigationView = binding.BottomNavMenu
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.camera -> {
                    replaceFragment(CameraFragment())
                    true
                }

                R.id.story -> {
                    replaceFragment(StoryFragment())
                    true
                }

                R.id.setting -> {
                    replaceFragment(SettingFragment())
                    true
                }
                else -> {
                    Toast.makeText(this, "Item yang dipilih tidak dikenali.", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }


        replaceFragment(CameraFragment())
    }
    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}