package com.nsnik.nrs.jaron

import com.nsnik.nrs.jaron.util.FieldValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.util.*

class FieldValidatorTest {

    @Test
    fun shouldReturnTrueIfValueTitleAndDescriptionIsPresent() {
        assertTrue(
            FieldValidator.validateFrom(
                "100.00",
                "Grocery",
                "Daily shopping",
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfValueIsLessThanOREqualsZero() {
        assertFalse(
            FieldValidator.validateFrom(
                "0.00",
                "Grocery",
                "Daily shopping",
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfValueIsEmpty() {
        assertFalse(
            FieldValidator.validateFrom(
                "",
                "Grocery",
                "Daily shopping",
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfTitleIsEmpty() {
        assertFalse(
            FieldValidator.validateFrom(
                "100.00",
                "",
                "Daily shopping",
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfDescriptionIsEmpty() {
        assertFalse(
            FieldValidator.validateFrom(
                "100.00",
                "Grocery",
                "",
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

}