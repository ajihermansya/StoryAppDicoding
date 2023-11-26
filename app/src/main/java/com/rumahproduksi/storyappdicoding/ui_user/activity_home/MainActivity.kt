package com.rumahproduksi.storyappdicoding.ui_user.activity_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rumahproduksi.storyappdicoding.R
import com.rumahproduksi.storyappdicoding.adapter_activity.AdapterStory
import com.rumahproduksi.storyappdicoding.adapter_activity.AdapterLoading
import com.rumahproduksi.storyappdicoding.view_model.ViewModelStory
import com.rumahproduksi.storyappdicoding.preference_activity.FactoryViewModel
import com.rumahproduksi.storyappdicoding.ui_user.activity_home.story.UploadStoryActivity
import com.rumahproduksi.storyappdicoding.ui_user.activity_maps.MapsActivity
import com.rumahproduksi.storyappdicoding.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){



    private val viewModel by viewModels<ViewModelStory> {
        FactoryViewModel.getInstance(this)
    }

    private lateinit var binding : ActivityMainBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val ItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(ItemDecoration)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            getStories()
        }

        getStories()

        binding.iconSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        binding.uploadStory.setOnClickListener {
            startActivity(Intent(this, UploadStoryActivity::class.java))
        }
        binding.iconMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    private fun getStories() {
        val adapter = AdapterStory()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = AdapterLoading {
                adapter.retry()
            }
        )

        viewModel.getStory.observe(this) {
            adapter.submitData(lifecycle, it)
            swipeRefreshLayout.isRefreshing = false
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getStory
    }

}