package com.rumahproduksi.storyappdicoding.activity_adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rumahproduksi.storyappdicoding.activity_remote.response.ListStoryItem
import com.rumahproduksi.storyappdicoding.activity_user.activity_home.story.DetailActivity
import com.rumahproduksi.storyappdicoding.databinding.CardStoryBinding

class AdapterStory :
    PagingDataAdapter<ListStoryItem, AdapterStory.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    inner class MyViewHolder(val binding: CardStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem) {
            binding.usernameStory.text = story.name
            binding.descriptionStory.text = story.description
            binding.imgPhoto.setImageResource(0)
            Glide.with(binding.imgPhoto).load(story.photoUrl).into(binding.imgPhoto)
            itemView.setOnClickListener {
                done
                val position = Intent(itemView.context, DetailActivity::class.java)
                position.putExtra(DetailActivity.DETAIL_STORY, story)
                itemView.context.startActivity(position)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}