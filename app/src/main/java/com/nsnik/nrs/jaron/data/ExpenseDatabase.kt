package com.nsnik.nrs.jaron.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ExpenseEntity::class], version = 1)
@TypeConverters(value = [DateConverter::class, ListConverter::class])
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
}