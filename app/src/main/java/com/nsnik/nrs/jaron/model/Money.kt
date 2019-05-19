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

import com.twitter.serial.serializer.ObjectSerializer
import com.twitter.serial.serializer.SerializationContext
import com.twitter.serial.stream.SerializerInput
import com.twitter.serial.stream.SerializerOutput

data class Money(val value: Double) {

    companion object {

        val SERIALIZER: ObjectSerializer<Money> = MoneySerializer()

        class MoneySerializer : ObjectSerializer<Money>() {
            override fun serializeObject(context: SerializationContext, output: SerializerOutput<out SerializerOutput<*>>, money: Money) {
                output.writeDouble(money.value)
            }

            override fun deserializeObject(context: SerializationContext, input: SerializerInput, versionNumber: Int): Money? {
                return Money(input.readDouble())
            }

        }

    }

    fun add(second: Money) = Money(value + second.value)

    fun subtract(second: Money) = Money(value - second.value)

    fun divide(second: Money) = Money(value / second.value)

    fun multiply(second: Money) = Money(value * second.value)

    override fun toString(): String {
        return String.format("%.2f", value)
    }

}