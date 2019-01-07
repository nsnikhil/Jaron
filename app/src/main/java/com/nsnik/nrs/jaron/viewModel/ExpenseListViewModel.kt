package com.nsnik.nrs.jaron.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.data.TagEntity
import java.util.*

class ExpenseListViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseUtility = (application as MyApplication).databaseUtility

    fun getAllExpenses() = databaseUtility.getAllExpenses()

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