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

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDao {

    @Query("SELECT * FROM TagEntity")
    fun getAllTags(): LiveData<List<TagEntity>>

    @Query("SELECT * FROM TagEntity WHERE tagValue = :value")
    fun getTagByValue(value: String): LiveData<TagEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTags(tagEntity: List<TagEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTags(tagEntity: List<TagEntity>): Int

    @Delete
    fun deleteTags(tagEntity: List<TagEntity>)

}