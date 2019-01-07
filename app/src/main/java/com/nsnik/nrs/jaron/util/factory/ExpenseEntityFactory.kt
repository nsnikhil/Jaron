package com.nsnik.nrs.jaron.util.factory

import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.data.PaymentType
import java.util.*

class ExpenseEntityFactory {

    companion object {

        fun createExpenseEntity(
            value: Double,
            title: String,
            description: String,
            date: Date,
            tags: List<String>,
            paymentType: PaymentType = PaymentType.Credit
        ): ExpenseEntity {
            val expenseEntity = ExpenseEntity()
            expenseEntity.value = value
            expenseEntity.title = title
            expenseEntity.description = description
            expenseEntity.date = date
            expenseEntity.tags = tags
            expenseEntity.paymentType = paymentType
            return expenseEntity
        }

    }

}