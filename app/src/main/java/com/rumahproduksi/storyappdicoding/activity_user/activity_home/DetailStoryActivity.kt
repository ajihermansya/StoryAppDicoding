package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryList
import com.rumahproduksi.storyappdicoding.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private val binding: ActivityDetailStoryBinding by lazy {
        ActivityDetailStoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        detailData()
        binding.iconBack.setOnClickListener {
            finish()
        }

    }

    @Suppress("DEPRECATION")
    private fun detailData() {
        val detail = intent.getParcelableExtra<StoryList>(EXTRA_ITEM)
        binding.apply {
            nameTolbar.text = detail?.name
            tvName.text = detail?.name
            tvDescription.text = detail?.description
            Glide.with(this@DetailStoryActivity)
                .load(detail?.photoUrl)
                .into(imgPhotos)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ITEM = "extra_item"
    }
}