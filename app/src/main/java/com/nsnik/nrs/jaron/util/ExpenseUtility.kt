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
import androidx.preference.PreferenceManager
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.model.Currency
import com.nsnik.nrs.jaron.model.Money
import com.nsnik.nrs.jaron.model.Money.Companion.Inr
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getStringRes
import com.nsnik.nrs.jaron.util.factory.MoneyFactory.Companion.createMoney
import timber.log.Timber

class ExpenseUtility {

    companion object {

//        fun getAmountSpend(context: Context, list: List<ExpenseEntity>): Money = list.stream()
//            .map { it.amount }
//            .reduce(createMoney(0.0, context)) { t, u -> t?.add(u!!) }!!

        fun getAmountSpend(context: Context, list: List<ExpenseEntity>) = list.stream()
            .map { it.amount }
            .reduce { t: Money?, u: Money? -> t?.add(u!!) }
            .orElse(createMoney(0.0,context))
            ?.toDefault(context)

//        fun getTotalAmount(context: Context): Money =
//            createMoney(
//                (context.applicationContext as MyApplication)
//                    .sharedPreferences.getFloat(getStringRes(R.string.sharedPreferenceKeyTotalAmount, context), 0.0F)
//                    .toDouble(), context)
//                .toDefault(context)

        fun getTotalAmount(context: Context): Money {
            val value = (context.applicationContext as MyApplication)
                .sharedPreferences.getFloat(getStringRes(R.string.sharedPreferenceKeyTotalAmount, context), 0.0F)

            Timber.d(value.toString())

            var money = Inr(value.toDouble())

            Timber.d(money.currency.name)
            Timber.d(money.value.toString())

            money = money.toDefault(context)

            Timber.d(money.currency.name)
            Timber.d(money.value.toString())


            return money
        }

        fun getAmountLeft(context: Context, list: List<ExpenseEntity>): Money {

            Timber.d(getDefaultCurrency(context).name)

            Timber.d(getTotalAmount(context).value.toString())
            Timber.d(getAmountSpend(context,list)?.value?.toString())

            Timber.d(getAmountSpend(context,list)?.currency?.name)
            Timber.d(getAmountSpend(context,list)?.currency?.name)

            Timber.d(getTotalAmount(context).subtract(getAmountSpend(context, list)!!).value.toString())
            Timber.d(getTotalAmount(context).subtract(getAmountSpend(context, list)!!).currency.name)

            return getTotalAmount(context).subtract(getAmountSpend(context, list)!!)
        }

        fun getPercentageSpend(context: Context, list: List<ExpenseEntity>) =
            (getAmountSpend(context, list)?.divide(getTotalAmount(context)))?.value?.toTwoDecimal()!! * 100

        fun getPercentageLeft(context: Context, list: List<ExpenseEntity>) =
            (getAmountLeft(context, list).divide(getTotalAmount(context))).value.toTwoDecimal() * 100

        private fun Double.toTwoDecimal() = String.format("%.2f", this).toDouble()

        fun formatWithPercent(double: Double) = StringBuilder(double.toString()).append("%").toString()

        fun getDefaultCurrency(context: Context) = Currency.values().first {
            it.acronym == PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(
                    getStringRes(R.string.preferenceCurrencyKey, context),
                    getStringRes(R.string.preferenceCurrencyDefaultValue, context)
                )!!
        }
    }

}