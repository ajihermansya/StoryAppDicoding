package com.rumahproduksi.storyappdicoding.activity_remote_server.locals.dao_story

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {

    @Query("SELECT * FROM db_story")
    fun getAllStories(): PagingSource<Int, StoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStories(storyList: List<StoryEntity>)

    @Query("DELETE FROM db_story")
    suspend fun deleteAllStories()

}