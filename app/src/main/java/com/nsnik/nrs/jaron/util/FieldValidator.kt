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

import android.content.Context
import android.widget.TextView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getString
import java.util.*

class FieldValidator {

    companion object {

        fun validateFrom(
            context: Context,
            value: TextView,
            title: TextView,
            description: TextView,
            date: Date,
            tags: List<String>
        ): Boolean = validateValueString(context, value) &&
                validateTitle(context, title) &&
                validateDescription(context, description) &&
                validateDate(date) &&
                validateTags(tags)

        fun validateValueString(context: Context, textView: TextView) =
            checkEmpty(context, textView, R.string.newExpenseErrorNoValue) &&
                    checkNonZero(context, textView, R.string.newExpenseErrorNegativeValue) &&
                    validateOverFlow(context, textView)


        private fun validateTitle(context: Context, textView: TextView): Boolean =
            checkEmpty(context, textView, R.string.newExpenseErrorNoTitle)

        private fun validateOverFlow(context: Context, textView: TextView): Boolean {
            if (textView.text.toString().toDouble() > ExpenseUtility.getTotalAmount(context)) {
                return setError(textView, getString(R.string.newExpenseErrorOverflowValue, context))
            }
            return true
        }
        
        private fun validateDescription(context: Context, textView: TextView): Boolean =
            checkEmpty(context, textView, R.string.newExpenseErrorNoDescription)

        private fun validateDate(date: Date): Boolean = true

        private fun validateTags(tags: List<String>): Boolean = true

        private fun checkEmpty(context: Context, textView: TextView, stringId: Int) =
            checkCondition(context, textView, stringId, textView.text.toString().isEmpty())

        private fun checkNonZero(context: Context, textView: TextView, stringId: Int) =
            checkCondition(context, textView, stringId, textView.text.toString().toDouble() <= 0)

        private fun checkCondition(context: Context, textView: TextView, stringId: Int, condition: Boolean): Boolean {
            if (condition) return setError(textView, getString(stringId, context))
            return true
        }

        private fun setError(textView: TextView, errorMessage: String): Boolean {
            textView.error = errorMessage
            return false
        }

    }

}