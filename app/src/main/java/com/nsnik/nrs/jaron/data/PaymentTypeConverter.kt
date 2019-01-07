package com.nsnik.nrs.jaron.data

import androidx.room.TypeConverter

class PaymentTypeConverter {

    @TypeConverter
    fun paymentTypeToString(paymentType: PaymentType): String = paymentType.name

    @TypeConverter
    fun stringToPaymentType(string: String): PaymentType = PaymentType.valueOf(string)

}