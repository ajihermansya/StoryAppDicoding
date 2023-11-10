package com.rumahproduksi.storyappdicoding.activity_user.activity_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahproduksi.storyappdicoding.activity_adapter.StoryAdapter
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_remote_server.locals.dao_story.StoryEntity
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server.ApiClient
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryList
import com.rumahproduksi.storyappdicoding.activity_user.model_user.StoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.model_view.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow


class MainActivity : AppCompatActivity(), StoryAdapter.OnItemClickAdapter {


    private  val binding : ActivityMainBinding by lazy {
      ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var prefersManager: PreferManager
    private lateinit var viewModel: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        prefersManager = PreferManager(this)
        storyAdapter = StoryAdapter(this, this)

        val dataRepository = RepositoryClass(ApiClient.getInstance())
        viewModel = ViewModelProvider(this, FactoryViewModel(dataRepository))[StoryViewModel::class.java]





        fetchData(prefersManager.token)

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            fetchData(prefersManager.token)
        }

        binding.btnTry.setOnClickListener {
            setLoadingState(true)
            fetchData(prefersManager.token)
        }

        binding.iconSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        binding.uploadStory.setOnClickListener {
            startActivity(Intent(this, UploadStoryActivity::class.java))
        }

    }

    private fun fetchData(auth: String) {
        binding.rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = storyAdapter
        }
        viewModel.apply {
            setLoadingState(true)
            fetchListStory(auth)
            responseListStory.observe(this@MainActivity) {
                when(it) {
                    is NetworkResults.Success -> {
                        if(it.data?.listStory != null) {
                            storyAdapter.setData(it.data.listStory)
                            binding.btnTry.visibility = View.GONE
                        } else {
                            binding.btnTry.visibility = View.GONE
                            binding.rvStory.visibility = View.GONE
                            binding.tvNotFound.visibility = View.VISIBLE
                        }
                        binding.tvError.visibility = View.GONE
                        setLoadingState(false)
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is NetworkResults.Loading -> {
                        setLoadingState(true)
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is NetworkResults.Error -> {
                        setLoadingState(false)
                        binding.rvStory.visibility = View.GONE
                        binding.tvNotFound.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.btnTry.visibility = View.VISIBLE
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }

        }
    }


    override fun onItemClicked(listStory: StoryList, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra(DetailStoryActivity.EXTRA_ITEM, listStory)
        startActivity(intent, optionsCompat.toBundle())
    }

    private fun setLoadingState(loading: Boolean) {
        when(loading) {
            true -> {
                binding.rvStory.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            false -> {
                binding.rvStory.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

}