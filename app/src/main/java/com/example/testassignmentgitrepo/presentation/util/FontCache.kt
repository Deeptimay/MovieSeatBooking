@file:JvmName("FontCache")

package com.example.testassignmentgitrepo.presentation.util

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

val APP_FONT_CACHE = HashMap<String, Typeface>()

fun getTypeface(fontName: String, fontId: Int, context: Context): Typeface? {
    var typeface = APP_FONT_CACHE[fontName]

    if (typeface == null) {
        try {
            typeface = ResourcesCompat.getFont(context, fontId)
        } catch (e: Exception) {
            return null
        }

        typeface?.let { APP_FONT_CACHE[fontName] = it }
    }

    return typeface
}