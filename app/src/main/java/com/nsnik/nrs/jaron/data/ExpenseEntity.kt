package com.nsnik.nrs.jaron.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class ExpenseEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var value: Double = 0.0
    var title: String? = null
    var description: String? = null
    var date: Date? = null
    var tags: List<String?>? = null
    var paymentType: PaymentType = PaymentType.Credit

}

enum class PaymentType {
    Debit, Credit
}