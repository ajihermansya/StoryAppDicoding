package com.rumahproduksi.storyappdicoding.activity_remote.message

import android.content.Context
import android.widget.Toast

object MessagesUtils {
    fun setMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}