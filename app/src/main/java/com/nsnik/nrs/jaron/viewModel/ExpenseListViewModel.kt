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

package com.nsnik.nrs.jaron.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.data.TagEntity
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentMonthAndYear
import java.util.*

class ExpenseListViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseUtility = (application as MyApplication).databaseUtility

    private val currentDate: MutableLiveData<Date> = MutableLiveData()

    fun getCurrentDate(): MutableLiveData<Date> {
        currentDate.postValue(getCurrentMonthAndYear())
        return currentDate
    }

    fun getAllExpenses() = databaseUtility.getAllExpenses()

    fun getAllExpensesSortLatest() = databaseUtility.getAllExpensesSortLatest()

    fun getAllTags(): LiveData<List<TagEntity>> = databaseUtility.getAllTags()

    fun getExpenseForDate(date: Date) = databaseUtility.getExpenseForDate(date)

    fun getTagByValue(value: String): LiveData<TagEntity> = databaseUtility.getTagByValue(value)

    fun insertExpenses(expenseEntity: List<ExpenseEntity>) = databaseUtility.insertExpenses(expenseEntity)

    fun insertTag(tagEntity: List<TagEntity>) = databaseUtility.insertTag(tagEntity)

    fun updateExpenses(expenseEntity: List<ExpenseEntity>) = databaseUtility.updateExpenses(expenseEntity)

    fun udpateTags(tagEntity: List<TagEntity>) = databaseUtility.updateTags(tagEntity)

    fun deleteExpenses(expenseEntity: List<ExpenseEntity>) = databaseUtility.deleteExpenses(expenseEntity)

    fun deleteTags(tagEntity: List<TagEntity>) = databaseUtility.deleteTags(tagEntity)

}