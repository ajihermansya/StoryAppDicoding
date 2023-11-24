package com.rumahproduksi.storyappdicoding.activity_user.activity_home.story

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.rumahproduksi.storyappdicoding.activity_adapter.AdapterStory
import com.rumahproduksi.storyappdicoding.activity_remote.response.ListStoryItem
import com.rumahproduksi.storyappdicoding.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: AdapterStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterStory()

        val detailStory = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY) as ListStoryItem
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY) as ListStoryItem
        }
        storyData(detailStory)

        binding.iconBack.setOnClickListener {
            finish()
        }
    }


    private fun storyData(storyItem: ListStoryItem) {
        binding.nameTolbar.text = storyItem.name
        binding.tvName.text = storyItem.name
        binding.tvDescription.text = storyItem.description
        binding.imgPhotos.setImageResource(0)
        Glide.with(binding.imgPhotos).load(storyItem.photoUrl).into(binding.imgPhotos)
    }

    companion object {
        private const val TAG = "DetailActivity"
        const val DETAIL_STORY = "detail_story"
    }

}