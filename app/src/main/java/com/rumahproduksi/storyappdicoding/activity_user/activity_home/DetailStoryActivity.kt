package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rumahproduksi.storyappdicoding.R

class DetailStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ITEM = "extra_item"
    }
}