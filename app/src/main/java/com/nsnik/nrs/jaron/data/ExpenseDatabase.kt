package com.nsnik.nrs.jaron.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ExpenseEntity::class, TagEntity::class], version = 3)
@TypeConverters(value = [DateConverter::class, ListConverter::class, PaymentTypeConverter::class])
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
    abstract val tagDao: TagDao
}