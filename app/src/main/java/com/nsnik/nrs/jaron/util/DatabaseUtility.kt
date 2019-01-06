package com.nsnik.nrs.jaron.util

import androidx.lifecycle.LiveData
import com.nsnik.nrs.jaron.dagger.scopes.ApplicationScope
import com.nsnik.nrs.jaron.data.ExpenseDatabase
import com.nsnik.nrs.jaron.data.ExpenseEntity
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

    fun insertExpenses(expenseEntity: List<ExpenseEntity>) {
        val single = Single.fromCallable { expenseDatabase.expenseDao.insertExpenses(expenseEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        single.subscribe(object : SingleObserver<LongArray> {
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

        })
    }

    fun updateExpenses(expenseEntity: List<ExpenseEntity>) {
        val single = Single.fromCallable { expenseDatabase.expenseDao.updateExpenses(expenseEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        single.subscribe(object : SingleObserver<Int> {
            override fun onSuccess(t: Int) {
                Timber.d(t.toString())
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                Timber.d(e)
            }
        })
    }

    fun deleteExpenses(expenseEntity: List<ExpenseEntity>) {
        val completable = Completable.fromCallable { expenseDatabase.expenseDao.deleteExpenses(expenseEntity) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        completable.subscribe(object : CompletableObserver {
            override fun onComplete() {
                Timber.d("Deletion successful")
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                Timber.d(e)
            }

        })
    }

}