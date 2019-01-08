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

import androidx.lifecycle.LiveData
import com.nsnik.nrs.jaron.dagger.scopes.ApplicationScope
import com.nsnik.nrs.jaron.data.ExpenseDatabase
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.data.TagEntity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@ApplicationScope
class DatabaseUtility @Inject constructor(private val expenseDatabase: ExpenseDatabase) {

    fun getAllExpenses(): LiveData<List<ExpenseEntity>> = expenseDatabase.expenseDao.getAllExpenses()

    fun getExpenseForDate(date: Date): LiveData<List<ExpenseEntity>> =
        expenseDatabase.expenseDao.getExpenseForDate(date)

    fun getAllTags(): LiveData<List<TagEntity>> = expenseDatabase.tagDao.getAllTags()

    fun getTagByValue(value: String): LiveData<TagEntity> = expenseDatabase.tagDao.getTagByValue(value)

    fun insertExpenses(expenseEntity: List<ExpenseEntity>) =
        Single.fromCallable { expenseDatabase.expenseDao.insertExpenses(expenseEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSingleInsertObserver())


    fun insertTag(tagEntity: List<TagEntity>) =
        Single.fromCallable { expenseDatabase.tagDao.insertTags(tagEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSingleInsertObserver())

    fun updateExpenses(expenseEntity: List<ExpenseEntity>) =
        Single.fromCallable { expenseDatabase.expenseDao.updateExpenses(expenseEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSingleUpdateObserver())

    fun updateTags(tagEntity: List<TagEntity>) =
        Single.fromCallable { expenseDatabase.tagDao.updateTags(tagEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSingleUpdateObserver())

    fun deleteExpenses(expenseEntity: List<ExpenseEntity>) =
        Completable.fromCallable { expenseDatabase.expenseDao.deleteExpenses(expenseEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCompletableDeleteObserver())

    fun deleteTags(tagEntity: List<TagEntity>) =
        Completable.fromCallable { expenseDatabase.tagDao.deleteTags(tagEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCompletableDeleteObserver())

    private fun getSingleInsertObserver() = object : SingleObserver<LongArray> {
        override fun onSuccess(t: LongArray) {
            t.forEach {
                Timber.d(it.toString())
            }
        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onError(e: Throwable) {
            Timber.d(e)
        }

    }

    private fun getSingleUpdateObserver() = object : SingleObserver<Int> {
        override fun onSuccess(t: Int) {
            Timber.d(t.toString())
        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onError(e: Throwable) {
            Timber.d(e)
        }
    }

    private fun getCompletableDeleteObserver() =
        object : CompletableObserver {
            override fun onComplete() {
                Timber.d("Deletion successful")
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                Timber.d(e)
            }

        }

}