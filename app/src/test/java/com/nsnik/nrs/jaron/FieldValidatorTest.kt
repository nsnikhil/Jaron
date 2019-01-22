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

package com.nsnik.nrs.jaron

import android.content.Context
import android.widget.TextView
import com.nsnik.nrs.jaron.util.FieldValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.Instant
import java.util.*


//TODO FIX THIS
class FieldValidatorTest {

    private lateinit var context: Context
    private lateinit var value: TextView
    private lateinit var title: TextView
    private lateinit var description: TextView

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        value = mock(TextView::class.java)
        title = mock(TextView::class.java)
        description = mock(TextView::class.java)
    }

    @Test
    fun shouldReturnTrueIfValueTitleAndDescriptionIsPresent() {
        Mockito.`when`(value.text.toString()).thenReturn("100.00")
        Mockito.`when`(title.text.toString()).thenReturn("Grocery")
        Mockito.`when`(description.text.toString()).thenReturn("Daily shopping")
        assertTrue(
            FieldValidator.validateFrom(
                context,
                value,
                title,
                description,
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfValueIsLessThanOREqualsZero() {
        Mockito.`when`(value.text.toString()).thenReturn("0.00")
        Mockito.`when`(title.text.toString()).thenReturn("Grocery")
        Mockito.`when`(description.text.toString()).thenReturn("Daily shopping")
        assertFalse(
            FieldValidator.validateFrom(
                context,
                value,
                title,
                description,
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfValueIsEmpty() {
        Mockito.`when`(value.text.toString()).thenReturn("")
        Mockito.`when`(title.text.toString()).thenReturn("Grocery")
        Mockito.`when`(description.text.toString()).thenReturn("Daily shopping")
        assertFalse(
            FieldValidator.validateFrom(
                context,
                value,
                title,
                description,
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfTitleIsEmpty() {
        Mockito.`when`(value.text.toString()).thenReturn("100.00")
        Mockito.`when`(title.text.toString()).thenReturn("")
        Mockito.`when`(description.text.toString()).thenReturn("Daily shopping")
        assertFalse(
            FieldValidator.validateFrom(
                context,
                value,
                title,
                description,
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

    @Test
    fun shouldReturnFalseIfDescriptionIsEmpty() {
        Mockito.`when`(value.text.toString()).thenReturn("100.00")
        Mockito.`when`(title.text.toString()).thenReturn("Grocery")
        Mockito.`when`(description.text.toString()).thenReturn("")
        assertFalse(
            FieldValidator.validateFrom(
                context,
                value,
                title,
                description,
                Date.from(Instant.now()),
                Collections.emptyList()
            )
        )
    }

}