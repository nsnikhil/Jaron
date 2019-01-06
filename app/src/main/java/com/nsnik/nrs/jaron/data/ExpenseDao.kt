package com.nsnik.nrs.jaron.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM ExpenseEntity")
    fun getAllExpenses(): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM ExpenseEntity WHERE date = :date")
    fun getExpenseForDate(date: Date): LiveData<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExpenses(expenseEntity: List<ExpenseEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateExpenses(expenseEntity: List<ExpenseEntity>): Int

    @Delete
    fun deleteExpense(expenseEntity: List<ExpenseEntity>)

}