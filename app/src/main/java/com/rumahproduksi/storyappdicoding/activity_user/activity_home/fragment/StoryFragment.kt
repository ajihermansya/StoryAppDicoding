package com.rumahproduksi.storyappdicoding.activity_user.activity_home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rumahproduksi.storyappdicoding.activity_adapter.StoryAdapter
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server.ApiClient
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryList
import com.rumahproduksi.storyappdicoding.activity_user.model_user.StoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.model_view.FactoryViewModel
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import com.rumahproduksi.storyappdicoding.activity_utils.preferences.PreferManager
import com.rumahproduksi.storyappdicoding.databinding.FragmentStoryBinding

class StoryFragment : Fragment(), StoryAdapter.OnItemClickAdapter {

    private val binding: FragmentStoryBinding by lazy {
        FragmentStoryBinding.inflate(layoutInflater)
    }

    private lateinit var prefersManager: PreferManager
    private lateinit var storyModel: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefersManager = PreferManager(requireActivity())
        storyAdapter = StoryAdapter(requireContext(), this)

        val dataRepository = RepositoryClass(ApiClient.getInstance())
        storyModel = ViewModelProvider(this, FactoryViewModel(dataRepository))[StoryViewModel::class.java]

        fetchData(prefersManager.token)



        binding.btnTry.setOnClickListener {
            setLoadingState(true)
            fetchData(prefersManager.token)
        }

        return binding.root
    }

    private fun fetchData(auth: String) {
        binding.rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = storyAdapter
        }
        storyModel.apply {
            setLoadingState(true)
            fetchListStory(auth)
            responseListStory.observe(requireActivity()) {
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
//        val intent = Intent(requireContext(), DetailStoryActivity::class.java)
//        intent.putExtra(DetailStoryActivity.EXTRA_ITEM, listStory)
//        startActivity(intent, optionsCompat.toBundle())
    }

    private fun setLoadingState(loading: Boolean) {
        when (loading) {
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
