package com.rumahproduksi.storyappdicoding.activity_adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryList
import com.rumahproduksi.storyappdicoding.databinding.CardStoryBinding


class StoryAdapter(private val context: Context, private val clickListener: OnItemClickAdapter) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var listStory = ArrayList<StoryList>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<StoryList>) {
        var diffResult = DiffUtil.calculateDiff(StoryDiffCallback(listStory, newList))
        listStory = newList as ArrayList<StoryList>
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoryViewHolder(
        CardStoryBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    inner class StoryViewHolder(private val binding: CardStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listStory: StoryList) {
            with(binding) {
                usernameStory.text = listStory.name
                descriptionStory.text = listStory.description
                Glide.with(context)
                    .load(listStory.photoUrl)
                    .into(imgPhoto)
                root.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(binding.imgPhoto, "image"),
                        Pair(binding.usernameStory, "username"),
                        Pair(binding.descriptionStory, "description")
                    )
                    clickListener.onItemClicked(listStory, optionsCompat)
                }
            }
        }
    }

    interface OnItemClickAdapter {
        fun onItemClicked(listStory: StoryList, optionsCompat: ActivityOptionsCompat)
    }
}