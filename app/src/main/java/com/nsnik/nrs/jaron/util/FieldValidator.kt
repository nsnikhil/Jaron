package com.nsnik.nrs.jaron.util

import java.util.*

class FieldValidator {

    companion object {

        fun validateFrom(value: Double, title: String, description: String, date: Date, tags: List<String>): Boolean =
            validateValue(value) && validateTitle(title) && validateDescription(description) && validateDate(date) && validateTags(tags)

        private fun validateValue(value: Double): Boolean = value > 0

        private fun validateTitle(title: String): Boolean = title.isNotEmpty()

        private fun validateDescription(description: String): Boolean = description.isNotEmpty()

        private fun validateDate(date: Date): Boolean = true

        private fun validateTags(tags: List<String>): Boolean = true

    }

}