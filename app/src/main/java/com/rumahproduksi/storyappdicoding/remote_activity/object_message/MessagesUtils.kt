package com.rumahproduksi.storyappdicoding.remote_activity.object_message

import android.content.Context
import android.widget.Toast

object MessagesUtils {
    fun setMessage(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}