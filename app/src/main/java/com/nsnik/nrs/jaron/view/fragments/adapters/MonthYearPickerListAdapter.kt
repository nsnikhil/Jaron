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

package com.nsnik.nrs.jaron.view.fragments.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentMonth
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentYear
import com.nsnik.nrs.jaron.view.fragments.adapters.listeners.MonthYearPickerListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.single_date_item.view.*

class MonthYearPickerListAdapter(val monthYearPickerListener: MonthYearPickerListener) :
    ListAdapter<String, MonthYearPickerListAdapter.MyViewHolder>(MonthYearDiffUtil()) {

    private lateinit var context: Context
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var currentTextView: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.single_date_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dateItem.text = getItem(position)
        if (holder.dateItem.text == holder.currentMonth || holder.dateItem.text == holder.currentYear) {
            currentTextView = holder.dateItem
            stylizeText(currentTextView)
        }
    }

    private fun stylizeText(textView: TextView) {
        textView.textSize = 20F
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
    }

    private fun normalizeText(textView: TextView) {
        textView.textSize = 18F
        textView.typeface = Typeface.DEFAULT
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.tab_indicator_text))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateItem: TextView = itemView.singleDateItem
        val currentMonth = getCurrentMonth()
        val currentYear = getCurrentYear()

        init {
            compositeDisposable.addAll(
                RxView.clicks(dateItem).subscribe {
                    normalizeText(currentTextView)
                    currentTextView = dateItem
                    stylizeText(dateItem)
                    monthYearPickerListener.onNewItemSelected(dateItem.text.toString())
                }
            )
        }

    }

    private fun cleanUp() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        cleanUp()
    }

}