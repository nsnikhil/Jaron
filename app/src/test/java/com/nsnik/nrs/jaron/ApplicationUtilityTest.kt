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

import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentMonth
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentMonthAndYear
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentYear
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getDateFromString
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.isSameDate
import junit.framework.Assert.*
import org.junit.Test
import java.util.*

class ApplicationUtilityTest {

    @Test
    fun testReturnCurrentMonth() {
        assertEquals(getCurrentMonth(), "January")
    }

    @Test
    fun testReturnCurrentYear() {
        assertEquals(getCurrentYear(), "2019")
    }

    @Test
    fun testGetDateWithCurrentMonthAndYear() {
        val calendar = Calendar.getInstance()
        calendar.time = getCurrentMonthAndYear()
        assertEquals(calendar.get(Calendar.MONTH), 0)
        assertEquals(calendar.get(Calendar.YEAR), 2019)
    }

    @Test
    fun testTwoDateEqualIfSameMonthAndYear() {
        assertTrue(
            isSameDate(Calendar.getInstance().time, getCurrentMonthAndYear())
        )
    }

    @Test
    fun testTwoDateEqualIfDifferentMonthAndYear() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, 1, 9)
        assertFalse(
            isSameDate(calendar.time, getCurrentMonthAndYear())
        )
    }

    @Test
    fun testGetCalendarFromString() {
        val string = "March, 2020"
        val calendar = Calendar.getInstance()
        calendar.time = getDateFromString(string)
        assertEquals(calendar.get(Calendar.MONTH), 2)
        assertEquals(calendar.get(Calendar.YEAR), 2020)
    }

}