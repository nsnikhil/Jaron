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
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getString

class ExpenseUtility {

    companion object {

        fun getAmountSpend(list: List<ExpenseEntity>): Double = list.stream()
            .map { it -> it.value }
            .reduce(0.0) { t, u -> t + u }

        fun getTotalAmount(): Double = 47000.0

        fun getAmountLeft(list: List<ExpenseEntity>) = getTotalAmount() - getAmountSpend(list)

        fun getPercentageSpend(list: List<ExpenseEntity>) = (getAmountSpend(list) / getTotalAmount()) * 100

        fun getPercentageLeft(list: List<ExpenseEntity>) = (getAmountLeft(list) / getTotalAmount()) * 100

        fun toTwoDecimal(double: Double) = String.format("%.2f", double).toDouble()

        fun formatTotal(context: Context, double: Double) =
            formatWithTitle(getString(R.string.expenseTotalAvailable, context), double)

        fun formatTotalSpend(context: Context, double: Double) =
            formatWithTitle(getString(R.string.expenseTotalSpend, context), double)

        fun formatTotalLeft(context: Context, double: Double) =
            formatWithTitle(getString(R.string.expenseTotalLeft, context), double)

        private fun formatWithTitle(title: String, double: Double) = String.format("%s %.2f", title, double)

        fun formatWithPercent(double: Double) = StringBuilder(double.toString()).append("%").toString()

    }

}