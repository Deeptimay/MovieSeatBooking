package com.example.testassignmentgitrepo.presentation.util

import org.junit.Assert.assertEquals
import org.junit.Test

class FontTypeTest {

    @Test
    fun `test FontType from value`() {
        val fontTypeLight = FontType.from(0)
        val fontTypeRegular = FontType.from(1)
        val fontTypeMedium = FontType.from(2)
        val fontTypeBold = FontType.from(3)

        assertEquals(FontType.ROBOTO_LIGHT, fontTypeLight)
        assertEquals(FontType.ROBOTO_REGULAR, fontTypeRegular)
        assertEquals(FontType.ROBOTO_MEDIUM, fontTypeMedium)
        assertEquals(FontType.ROBOTO_BOLD, fontTypeBold)
    }
}
