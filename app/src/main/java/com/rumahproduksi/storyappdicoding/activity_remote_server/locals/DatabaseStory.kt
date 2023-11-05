package com.rumahproduksi.storyappdicoding.activity_remote_server.locals

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rumahproduksi.storyappdicoding.activity_remote_server.locals.dao_story.StoryDao
import com.rumahproduksi.storyappdicoding.activity_remote_server.locals.dao_story.StoryEntity

@Database(
    entities =[StoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseStory: RoomDatabase() {

    abstract fun getStoryDao(): StoryDao

}