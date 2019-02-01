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

package com.nsnik.nrs.jaron.view.fragments

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.model.Currency
import com.nsnik.nrs.jaron.model.Money
import com.nsnik.nrs.jaron.util.ApplicationUtility
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getDefaultCurrency
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getTotalAmount
import com.nsnik.nrs.jaron.util.factory.MoneyFactory.Companion.createMoney

class PreferenceFragment : PreferenceFragmentCompat() {

    private lateinit var monthlyBudgetSummary: EditTextPreference
    private lateinit var defaultCurrency: ListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preferences)
        initializeBudgetPreference()
        initializeCurrencyPreference()
    }

    private fun initializeBudgetPreference() {
        monthlyBudgetSummary = preferenceManager.findPreference(stringRes(R.string.preferenceTitleChangeMonthlyBudgetKey, activity!!)) as EditTextPreference
        monthlyBudgetSummary.setOnPreferenceChangeListener { _, newValue ->
            saveNewBudget(newValue.toString().toFloat())
            setBudgetSummary(monthlyBudgetSummary)
            true
        }
        setBudgetSummary(monthlyBudgetSummary)
    }

    private fun initializeCurrencyPreference() {
        defaultCurrency = preferenceManager.findPreference(stringRes(R.string.preferenceCurrencyKey, activity!!)) as ListPreference
        defaultCurrency.setOnPreferenceChangeListener { _, newValue ->
            defaultCurrency.summary = newValue.toString()
            Toast.makeText(activity!!,"Go back to see the changes take effect not doing so may cause unusual effects",Toast.LENGTH_LONG).show()
            true
        }
        defaultCurrency.summary = defaultCurrency.value
    }

    private fun setBudgetSummary(editTextPreference: EditTextPreference) {
        val currency = getDefaultCurrency(activity!!)
        val amount = getTotalAmount(activity!!)
        editTextPreference.summary = appendMoneyWithCurrency(currency, amount)
        editTextPreference.text = amount.value.toString()
    }

    private fun appendMoneyWithCurrency(defaultCurrency: Currency, amount: Money) =
        defaultCurrency.symbol.plus(amount.value.toTwoDecimal())

    private fun Double.toTwoDecimal() = String.format("%.2f", this).toDouble()

    private fun saveNewBudget(newBudget: Float) = preferenceManager.sharedPreferences
        .edit()
        .putFloat(stringRes(R.string.sharedPreferenceKeyTotalAmount, activity!!), createMoney(newBudget.toDouble(), activity!!).toBase().value.toFloat())
        .apply()

    private fun stringRes(stringId: Int, context: Context) = ApplicationUtility.getStringRes(stringId, context)

}