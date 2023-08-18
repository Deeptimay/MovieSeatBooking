package com.example.testassignmentgitrepo.presentation.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilityTest {

    @Test
    fun testValidDateFormat() {
        val inputDate = "2023-08-18T12:00:00Z"
        val formattedDate = DateUtility.formatDate(inputDate)

        assertEquals("18 Aug 2023", formattedDate)
    }

    @Test
    fun testInvalidDateFormat() {
        val inputDate = "InvalidDate"
        val formattedDate = DateUtility.formatDate(inputDate)

        assertEquals("InvalidDate", formattedDate)
    }
}
