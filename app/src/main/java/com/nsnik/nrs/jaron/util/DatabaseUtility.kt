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
        singleInsertSubscriber(singleInsert(expenseDatabase.expenseDao.insertExpenses(expenseEntity)))

    fun insertTag(tagEntity: List<TagEntity>) =
        singleInsertSubscriber(singleInsert(expenseDatabase.tagDao.insertTags(tagEntity)))

    fun updateExpenses(expenseEntity: List<ExpenseEntity>) =
        singleUpdateSubscriber(singleUpdate(expenseDatabase.expenseDao.updateExpenses(expenseEntity)))

    fun udpateTags(tagEntity: List<TagEntity>) =
        singleUpdateSubscriber(singleUpdate(expenseDatabase.tagDao.updateTags(tagEntity)))

    fun deleteExpenses(expenseEntity: List<ExpenseEntity>) =
        completeDeleteSubscriber(completeDelete(expenseDatabase.expenseDao.deleteExpenses(expenseEntity)))

    fun deleteTags(tagEntity: List<TagEntity>) =
        completeDeleteSubscriber(completeDelete(expenseDatabase.tagDao.deleteTags(tagEntity)))

    private fun singleInsert(longArray: LongArray) =
        Single.fromCallable { longArray }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    private fun singleUpdate(integer: Int) =
        Single.fromCallable { integer }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    private fun completeDelete(value: Any) =
        Completable.fromCallable { value }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    private fun singleInsertSubscriber(single: Single<LongArray>) =
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

    private fun singleUpdateSubscriber(single: Single<Int>) = single.subscribe(object : SingleObserver<Int> {
        override fun onSuccess(t: Int) {
            Timber.d(t.toString())
        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onError(e: Throwable) {
            Timber.d(e)
        }
    })

    private fun completeDeleteSubscriber(completable: Completable) =
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