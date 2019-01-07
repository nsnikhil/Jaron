package com.nsnik.nrs.jaron.util.factory

import com.nsnik.nrs.jaron.data.ExpenseEntity
import java.util.*

class ExpenseEntityFactory {

    companion object {

        fun createExpenseEntity(value: Double,title: String,description: String,date:Date,tags: List<String>): ExpenseEntity {
            val expenseEntity = ExpenseEntity()
            expenseEntity.value = value
            expenseEntity.title = title
            expenseEntity.description = description
            expenseEntity.date = date
            expenseEntity.tags = tags
            return expenseEntity
        }

    }

}