package com.rumahproduksi.storyappdicoding.ui_user.activity_intro.custome_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.rumahproduksi.storyappdicoding.R


class MyButtonClass : AppCompatButton {

    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // Tambahkan override untuk onTouchEvent
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Saat tombol ditekan, set latar belakang ke warna merah
                background = ContextCompat.getDrawable(context, R.drawable.bg_button)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Saat tombol dilepaskan atau peristiwa pencetan dibatalkan, kembalikan latar belakang ke normal
                background = if (isEnabled) enabledBackground else disabledBackground
            }
        }
        return super.onTouchEvent(event)
    }


    private fun init() {
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
    }
}
