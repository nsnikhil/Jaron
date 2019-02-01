/*
 *     Jaron  Copyright (C) 2019  Nikhil Soni
 *     This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 *     This is free software, and you are welcome to redistribute it
 *     under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 *   You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <https://www.gnu.org/licenses/>.
 *
 *   The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <https://www.gnu.org/licenses/why-not-lgpl.html>.
 */

@file:Suppress("FunctionName")

package com.nsnik.nrs.jaron.model

import android.content.Context
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.getDefaultCurrency
import com.twitter.serial.serializer.CoreSerializers
import com.twitter.serial.serializer.ObjectSerializer
import com.twitter.serial.serializer.SerializationContext
import com.twitter.serial.stream.SerializerInput
import com.twitter.serial.stream.SerializerOutput

data class Money(val value: Double, val currency: Currency) {

    companion object {

        val SERIALIZER: ObjectSerializer<Money> = MoneySerializer()

        class MoneySerializer : ObjectSerializer<Money>() {
            override fun serializeObject(context: SerializationContext, output: SerializerOutput<out SerializerOutput<*>>, money: Money) {
                output.writeDouble(money.value)
                output.writeObject(SerializationContext.ALWAYS_RELEASE, money.currency, CoreSerializers.getEnumSerializer(Currency::class.java))
            }

            override fun deserializeObject(context: SerializationContext, input: SerializerInput, versionNumber: Int): Money? {
                return Money(
                    input.readDouble(),
                    input.readObject(SerializationContext.ALWAYS_RELEASE, CoreSerializers.getEnumSerializer(Currency::class.java))!!
                )
            }

        }

        fun Inr(value: Double) = Money(value, Currency.Rupee)

        fun Usd(value: Double) = Money(value, Currency.UnitedStatesDollar)

        fun Aud(value: Double) = Money(value, Currency.AustralianDollar)

        fun Pounds(value: Double) = Money(value, Currency.Pound)

        fun Euro(value: Double) = Money(value, Currency.Euro)

    }

    fun add(second: Money): Money {
        if (second.currency != currency) return Money(value + second.convertTo(currency).value, currency)
        return Money(value + second.value, currency)
    }

    fun subtract(second: Money): Money {
        if (second.currency != currency) return Money(value - second.convertTo(currency).value, currency)
        return Money(value - second.value, currency)
    }

    fun divide(second: Money): Money {
        if (second.currency != currency) return Money(value / second.convertTo(currency).value, currency)
        return Money(value / second.value, currency)
    }

    fun multiply(second: Money): Money {
        if (second.currency != currency) return Money(value * second.convertTo(currency).value, currency)
        return Money(value * second.value, currency)
    }

    override fun equals(other: Any?): Boolean {
        if ((other as Money).currency != currency)
            return Math.abs(other.convertTo(currency).value - value) <= 0.5
        return value == other.value
    }

    override fun hashCode() = value.hashCode() + currency.hashCode()

    override fun toString() = currency.symbol.plus(value.toTwoDecimal())

    fun toBase() = Inr(this.value / this.currency.conversionFactor)

    fun fromBase(currency: Currency) =
        Money(this.value * currency.conversionFactor, currency)

    fun fromBase(second: Money, currency: Currency) =
        Money(second.value * currency.conversionFactor, currency)

    fun convertTo(currency: Currency) = fromBase(toBase(), currency)

    fun toDefault(context: Context) = fromBase(getDefaultCurrency(context))

    private fun Double.toTwoDecimal() = String.format("%.2f", this).toDouble()

}