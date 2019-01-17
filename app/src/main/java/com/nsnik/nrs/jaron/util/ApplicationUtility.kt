/*
 *     Jaron  Copyright (C) 2019  Nikhil Soni
 *     This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 *     This is free software, and you are welcome to redistribute it
 *     under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 *   You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <https://www.gnu.org/licenses/>.
 *
 *   The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <https://www.gnu.org/licenses/why-not-lgpl.html>.
 */

package com.nsnik.nrs.jaron.util

import android.app.Activity
import android.content.Context
import android.text.Html
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.data.TagEntity
import com.nsnik.nrs.jaron.util.factory.TagEntityFactory.Companion.createTagEntity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList

class ApplicationUtility {

    companion object {

        fun getCurrentMonth(): String = getSimpleDateFormatter("MMMM").format(getDate())

        fun getCurrentYear(): String = getSimpleDateFormatter("YYYY").format(getDate())

        private fun getSimpleDateFormatter(format: String) = SimpleDateFormat(format, Locale.ENGLISH)

        private fun getDate(): Date = getCalendar().time

        private fun getCalendar(): Calendar = Calendar.getInstance()

        fun getFormattedCurrentDate() = getFormattedCurrentDate(getDate())

        fun getFormattedCurrentDate(date: Date) = StringBuilder(getSimpleDateFormatter("MMMM").format(date))
            .append(", ")
            .append(getSimpleDateFormatter("YYYY").format(date))
            .toString()


        fun getFormattedText(text: String?) =
            Html.fromHtml("<font color='#009688'>$text</font>", Html.FROM_HTML_MODE_LEGACY)!!

        fun formatTag(tags: String): List<String> = tags.toLowerCase(Locale.ENGLISH).split("\\s".toRegex())

        fun listToTag(strings: List<String>): List<TagEntity> = strings.stream()
            .map { createTagEntity(it) }
            .collect(Collectors.toList())

        fun getCurrentMonthAndYear(): Date = getCurrentMonthAndYearUtil(getCalendar())

        private fun getCurrentMonthAndYearUtil(calendar: Calendar): Date {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            return calendar.time
        }

        fun isSameDate(thisDate: Date, todayDate: Date): Boolean {
            val thisCalendar = Calendar.getInstance()
            thisCalendar.time = thisDate
            val todayCalendar = Calendar.getInstance()
            todayCalendar.time = todayDate
            return thisCalendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                    thisCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
        }

        fun formatText(originalString: String, newValue: String): String {
            if (newValue.contains("20"))
                return originalString.substring(0, originalString.indexOf(" ") + 1) + newValue
            return newValue + originalString.substring(originalString.indexOf(","))
        }


        private fun getMonthNumberFromName(value: String): Int {
            val calendar = getCalendar()
            calendar.time = SimpleDateFormat("MMMM", Locale.ENGLISH).parse(value)
            return calendar.get(Calendar.MONTH)
        }

        fun getDateFromString(dateString: String): Date {
            val month = dateString.substring(0, dateString.indexOf(","))
            val year = dateString.substring(dateString.indexOf(" ") + 1)
            val calendar = getCalendar()
            calendar.set(Calendar.YEAR, year.toInt())
            calendar.set(Calendar.MONTH, getMonthNumberFromName(month))
            return calendar.time
        }

        fun getString(id: Int, context: Context) = context.resources.getString(id)

        fun getColor(id: Int, context: Context) = ContextCompat.getColor(context, id)

        fun getDrawable(id: Int, context: Context) = ContextCompat.getDrawable(context, id)

        fun getYear(date: Date): Int {
            val calendar = getCalendar()
            calendar.time = date
            return calendar.get(Calendar.YEAR)
        }

        fun getMonth(date: Date): Int {
            val calendar = getCalendar()
            calendar.time = date
            return calendar.get(Calendar.MONTH)
        }


        fun filteredListByDate(list: List<ExpenseEntity>, date: Date): List<ExpenseEntity> = list.stream()
            .filter { t -> isSameDate(t.date!!, date) }
            .toList()

        fun goToIntro(activity: Activity) {
            Timber.d(ExpenseUtility.getTotalAmount(activity).toString())
            if (ExpenseUtility.getTotalAmount(activity) == 0.0)
                activity.findNavController(R.id.mainNavHost).navigate(R.id.introFragment)
        }

    }

}