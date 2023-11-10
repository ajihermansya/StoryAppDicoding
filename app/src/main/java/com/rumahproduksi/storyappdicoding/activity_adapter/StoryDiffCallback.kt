package com.rumahproduksi.storyappdicoding.activity_adapter

import androidx.recyclerview.widget.DiffUtil
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryList

class StoryDiffCallback(
    private val oldList: List<StoryList>,
    private val newList: List<StoryList>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}


