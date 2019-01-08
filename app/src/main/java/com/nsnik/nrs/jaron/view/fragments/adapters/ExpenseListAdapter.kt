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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.single_expense_item.view.*


class ExpenseListAdapter : ListAdapter<ExpenseEntity, ExpenseListAdapter.MyViewHolder>(ExpenseEntityDiffUtil()) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_expense_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expenseEntity = getItem(position)
        holder.value.text = expenseEntity.value.toString()
        holder.title.text = expenseEntity.title
        holder.description.text = expenseEntity.description
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val value: TextView = itemView.singleExpenseValue
        val title: TextView = itemView.singleExpenseTitle
        val description: TextView = itemView.singleExpenseDescription

        init {
            compositeDisposable.addAll(
                RxView.clicks(itemView).subscribe {

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