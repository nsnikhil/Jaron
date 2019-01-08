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

package com.nsnik.nrs.jaron.util

import java.util.*

class FieldValidator {

    companion object {

        fun validateFrom(value: String, title: String, description: String, date: Date, tags: List<String>): Boolean =
            validateValueString(value) &&
                    validateValue(value.toDouble()) &&
                    validateTitle(title) &&
                    validateDescription(description) &&
                    validateDate(date) &&
                    validateTags(tags)

        private fun validateValueString(value: String): Boolean = value.isNotEmpty()

        private fun validateValue(value: Double): Boolean = value > 0

        private fun validateTitle(title: String): Boolean = title.isNotEmpty()

        private fun validateDescription(description: String): Boolean = description.isNotEmpty()

        private fun validateDate(date: Date): Boolean = true

        private fun validateTags(tags: List<String>): Boolean = true

    }

}