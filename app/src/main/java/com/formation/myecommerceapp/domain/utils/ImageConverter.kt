package com.formation.myecommerceapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap

object ImageConverter {

    fun getBitMapFromDrawable(drawableResourceId: Int, context: Context): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableResourceId)!!

        val bitmap = createBitmap(
            width = drawable.intrinsicWidth,
            height = drawable.intrinsicHeight,
        )

        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)

        drawable.draw(canvas)

        return bitmap
    }
}