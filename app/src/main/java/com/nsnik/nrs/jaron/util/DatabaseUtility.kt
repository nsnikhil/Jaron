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