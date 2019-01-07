package com.nsnik.nrs.jaron.util

import android.text.Html
import java.text.SimpleDateFormat
import java.util.*

class ApplicationUtility {

    companion object {

        fun getCurrentMonth(): String = SimpleDateFormat("MMMM", Locale.ENGLISH).format(getDate())

        private fun getDate(): Date = Calendar.getInstance().time

        fun getFormattedText(text: String?) =
            Html.fromHtml("<font color='#0500ff'>$text</font>", Html.FROM_HTML_MODE_LEGACY)!!

        fun formatTag(tags: String): List<String> {
            return tags.split("\\s".toRegex())

        }

    }

}