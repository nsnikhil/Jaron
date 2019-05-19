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

package com.nsnik.nrs.jaron.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.nsnik.nrs.jaron.model.ExpenseMode
import com.nsnik.nrs.jaron.model.Money
import com.twitter.serial.serializer.CollectionSerializers
import com.twitter.serial.serializer.CoreSerializers
import com.twitter.serial.serializer.ObjectSerializer
import com.twitter.serial.serializer.SerializationContext
import com.twitter.serial.stream.SerializerInput
import com.twitter.serial.stream.SerializerOutput
import java.util.*

@Entity
class ExpenseEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var amount: Money? = null
    var title: String? = null
    var description: String? = null
    var date: Date? = null
    var tags: List<String?>? = null
    var paymentType: Int = ExpenseMode.CREDIT

    companion object {

        @Ignore
        val SERIALIZER: ObjectSerializer<ExpenseEntity> = ExpenseEntitySerializer()

        class ExpenseEntitySerializer : ObjectSerializer<ExpenseEntity>() {

            override fun serializeObject(context: SerializationContext, output: SerializerOutput<out SerializerOutput<*>>, expenseEntity: ExpenseEntity) {
                output.writeInt(expenseEntity.id)
                output.writeObject(SerializationContext.ALWAYS_RELEASE, expenseEntity.amount, Money.SERIALIZER)
                output.writeString(expenseEntity.title)
                output.writeString(expenseEntity.description)
                output.writeObject(SerializationContext.ALWAYS_RELEASE, expenseEntity.date, CoreSerializers.DATE)
                output.writeObject(SerializationContext.ALWAYS_RELEASE, expenseEntity.tags, CollectionSerializers.getListSerializer(CoreSerializers.STRING))
                output.writeInt(expenseEntity.paymentType)
            }

            override fun deserializeObject(context: SerializationContext, input: SerializerInput, versionNumber: Int): ExpenseEntity? {
                val expenseEntity = ExpenseEntity()
                expenseEntity.id = input.readInt()
                expenseEntity.amount = input.readObject(SerializationContext.ALWAYS_RELEASE, Money.SERIALIZER)
                expenseEntity.title = input.readString()
                expenseEntity.description = input.readString()
                expenseEntity.date = input.readObject(SerializationContext.ALWAYS_RELEASE, CoreSerializers.DATE)
                expenseEntity.tags = input.readObject(SerializationContext.ALWAYS_RELEASE, CollectionSerializers.getListSerializer(CoreSerializers.STRING))
                expenseEntity.paymentType = input.readInt()
                return expenseEntity
            }

        }

    }

}