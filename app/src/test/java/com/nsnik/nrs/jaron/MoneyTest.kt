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

import com.nsnik.nrs.jaron.model.Currency.*
import com.nsnik.nrs.jaron.model.Money
import com.nsnik.nrs.jaron.model.Money.Companion.Inr
import com.nsnik.nrs.jaron.model.Money.Companion.Usd
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

class MoneyTest {

    private lateinit var hundredRupee: Money
    private lateinit var oneDollarFortyCentsUsd: Money
    private lateinit var hundredDollars: Money

    @Before
    fun setUp() {
        hundredRupee = Inr(100.0)
        oneDollarFortyCentsUsd = Usd(1.40)
        hundredDollars = Usd(100.0)
    }

    @Test
    fun shouldReturnTrueForMoneyWithSameValue() {
        assertTrue(hundredRupee == Money(100.0, Rupee))
    }

    @Test
    fun shouldTrueForHundredRupeeAndOneDollarFortyCents(){
        assertTrue(hundredRupee == oneDollarFortyCentsUsd)
    }

    @Test
    fun shouldReturnFalseForMoneyWithDifferentValue() {
        assertFalse(hundredRupee == Money(99.0, UnitedStatesDollar))
    }

    @Test
    fun shouldAddHundredAndTwoHundredRupeeToGiveThreeHundredRupee() {
        assertTrue(hundredRupee.add(Money(200.00, Rupee)) == Money(300.00, Rupee))
    }

    @Test
    fun shouldAddHundredRupeeAndThreeDollarToGiveThreeHundredRupee() {
        assertEquals(hundredRupee.add(Money(3.0, UnitedStatesDollar)).value, Money(313.88, Rupee).value,0.1)
    }

    @Test
    fun shouldConvertHundredDollarsToHundredDollars(){
        assertTrue(hundredDollars.convertTo(UnitedStatesDollar) == hundredDollars)
    }

}