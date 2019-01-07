package com.nsnik.nrs.jaron.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ExpenseEntity::class, TagEntity::class], version = 2)
@TypeConverters(value = [DateConverter::class, ListConverter::class])
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
    abstract val tagDao: TagDao
}