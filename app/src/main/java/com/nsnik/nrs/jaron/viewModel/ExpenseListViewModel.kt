package com.nsnik.nrs.jaron.viewModel

import androidx.lifecycle.AndroidViewModel
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.data.ExpenseEntity
import java.util.*

class ExpenseListViewModel(application: MyApplication) : AndroidViewModel(application) {

    private val databaseUtility = application.databaseUtility

    fun getAllExpenses() = databaseUtility.getAllExpenses()

    fun getExpenseForDate(date: Date) = databaseUtility.getExpenseForDate(date)

    fun insertExpenses(expenseEntity: List<ExpenseEntity>) = databaseUtility.insertExpenses(expenseEntity)

    fun updateExpenses(expenseEntity: List<ExpenseEntity>) = databaseUtility.updateExpenses(expenseEntity)

    fun deleteExpenses(expenseEntity: List<ExpenseEntity>) = databaseUtility.deleteExpenses(expenseEntity)

}