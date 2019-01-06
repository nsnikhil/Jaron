package com.nsnik.nrs.jaron.util

import java.text.SimpleDateFormat
import java.util.*

class ApplicationUtility {

    companion object {

        fun getCurrentMonth(): String {
            return SimpleDateFormat("MMMM", Locale.ENGLISH).format(getDate())
        }

        private fun getDate(): Date {
            return Calendar.getInstance().time
        }

    }

}