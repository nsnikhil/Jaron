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

package com.nsnik.nrs.jaron.util.factory

import android.content.Context
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.model.MonthSummary
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getAmountLeft
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getAmountSpend
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getPercentageLeft
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getPercentageSpend
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getTotalAmount

class MonthSummaryFactory {

    companion object {
        fun getMonthSummary(context: Context, list: List<ExpenseEntity>): MonthSummary = MonthSummary(
            total = getTotalAmount(context),
            totalSpend = getAmountSpend(list),
            totalLeft = getAmountLeft(context, list),
            percentageSpend = getPercentageSpend(context, list),
            percentageLeft = getPercentageLeft(context, list)
        )
    }

}