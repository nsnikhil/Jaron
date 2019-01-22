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
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.model.Currency
import com.nsnik.nrs.jaron.model.Money
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getString

class ExpenseUtility {

    companion object {

//        fun getAmountSpend(list: List<ExpenseEntity>): Double = list.stream()
//            .map { it -> it.amount }
//            .map { it -> it?.value!! }
//            .reduce(0.0) { t, u -> t + u }

        //TODO CHANGE ALL FUNCTIONS TO RETURN MONEY OBJECT
        fun getAmountSpend(list: List<ExpenseEntity>): Money = list.stream()
            .map { it -> it.amount }
            .reduce(Money(0.0, Currency.Rupee)) { t, u -> t?.add(u!!) }!!

        fun getTotalAmount(context: Context): Money =
            Money(
                (context.applicationContext as MyApplication)
                    .sharedPreferences
                    .getFloat(getString(R.string.sharedPreferenceKeyTotalAmount, context), 0.0F)
                    .toDouble(), Currency.Rupee
            )

        fun getAmountLeft(context: Context, list: List<ExpenseEntity>) =
            getTotalAmount(context).subtract(getAmountSpend(list))

        fun getPercentageSpend(context: Context, list: List<ExpenseEntity>) =
            (getAmountSpend(list).divide(getTotalAmount(context))).value.toTwoDecimal() * 100

        fun getPercentageLeft(context: Context, list: List<ExpenseEntity>) =
            (getAmountLeft(context, list).divide(getTotalAmount(context))).value.toTwoDecimal() * 100

        fun Double.toTwoDecimal() = String.format("%.2f", this).toDouble()

        fun formatWithPercent(double: Double) = StringBuilder(double.toString()).append("%").toString()

    }

}