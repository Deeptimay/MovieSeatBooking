package com.example.testassignmentgitrepo.util

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.test.core.app.ApplicationProvider
import com.example.testassignmentgitrepo.presentation.util.APP_FONT_CACHE
import com.example.testassignmentgitrepo.presentation.util.getTypeface
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test
import org.mockito.Mockito.mock

class FontCacheUtilTest {

    @Test
    fun getTypeface_returns_null_for_invalid_fontId() {
        val context = mock(Context::class.java)
        val fontId = 0 // Invalid fontId
        val fontName = "regular" // Assuming "regular" font

        val typeface = getTypeface(fontName, fontId, context)

        assertNull(typeface)
    }

    @Test
    fun getTypeface_returns_cached_Typeface() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val fontId = com.example.testassignmentgitrepo.R.font.roboto_bold // Valid fontId
        val fontName = "roboto_bold" // Assuming "my_font" font

        val typeface = getTypeface(fontName, fontId, context)

        assertNotNull(typeface)
        assertSame(typeface, APP_FONT_CACHE[fontName])
    }

    @Test
    fun getTypeface_loads_Typeface_from_ResourcesCompat() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val fontId = com.example.testassignmentgitrepo.R.font.roboto_bold // Valid fontId
        val fontName = "roboto_bold" // Assuming "my_font" font

        val typeface = getTypeface(fontName, fontId, context)

        assertNotNull(typeface)
        assertEquals(typeface, ResourcesCompat.getFont(context, fontId))
    }
}
