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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.model.MonthSummary
import com.nsnik.nrs.jaron.util.ApplicationUtility
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getString
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.formatWithPercent
import com.nsnik.nrs.jaron.util.ExpenseUtility.Companion.toTwoDecimal
import com.nsnik.nrs.jaron.view.fragments.ExpenseListFragment
import com.nsnik.nrs.jaron.view.fragments.listeners.ExpenseItemClickListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.month_summary_layout.view.*
import kotlinx.android.synthetic.main.single_expense_item.view.*
import kotlinx.android.synthetic.main.summary_item.view.*


class ExpenseListAdapter(private val expenseListFragment: ExpenseListFragment) :
    ListAdapter<ExpenseEntity, RecyclerView.ViewHolder>(ExpenseEntityDiffUtil()) {

    private val summaryViewType = 0
    private val itemViewType = 1
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val summary: MutableLiveData<MonthSummary> = MutableLiveData()
    private val expenseItemClickListener: ExpenseItemClickListener = expenseListFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == summaryViewType)
            return SummaryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.month_summary_layout,
                    parent,
                    false
                )
            )
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_expense_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) bindSummaryHolder(holder = (holder as SummaryViewHolder), position = position)
        else bindItemHolder(holder = (holder as ItemViewHolder), position = position - 1)
    }

    private fun bindSummaryHolder(holder: SummaryViewHolder, position: Int) {
        summary.observe(expenseListFragment, Observer(function = {
            holder.total.text = String.format(
                "%s%.2f",
                getString(R.string.expenseCurrencySymbol, expenseListFragment.context!!),
                it?.total
            )
            holder.spend.text = String.format(
                "%s%.2f",
                getString(R.string.expenseCurrencySymbol, expenseListFragment.context!!),
                it?.totalSpend
            )
            holder.left.text = String.format(
                "%s%.2f",
                getString(R.string.expenseCurrencySymbol, expenseListFragment.context!!),
                it?.totalLeft
            )
            holder.percentageSpend.text = formatWithPercent(toTwoDecimal(it.percentageSpend))
            holder.percentageProgress.progress = it.percentageSpend.toInt()
        }))
    }

    private fun bindItemHolder(holder: ItemViewHolder, position: Int) {
        val expenseEntity = getItem(position)
        holder.value.text = String.format(
            "%s%.2f",
            getString(R.string.expenseCurrencySymbol, expenseListFragment.context!!), expenseEntity.amount?.value
        )
        holder.title.text = expenseEntity.title
        holder.description.text = expenseEntity.description
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return summaryViewType
        return itemViewType
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    fun submitSummary(summary: MonthSummary) {
        this.summary.postValue(summary)
    }

    inner class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val total: TextView = itemView.summaryTotalHaveContainer.summaryTotalValue
        val left: TextView = itemView.summaryTotalLeftContainer.summaryTotalValue
        val spend: TextView = itemView.summaryTotalSpendContainer.summaryTotalValue

        private val totalTitle: TextView = itemView.summaryTotalHaveContainer.summaryItemTitle
        private val leftTitle: TextView = itemView.summaryTotalLeftContainer.summaryItemTitle
        private val spendTitle: TextView = itemView.summaryTotalSpendContainer.summaryItemTitle

        val percentageSpend: TextView = itemView.summaryPercentageProgress
        val percentageProgress: ProgressBar = itemView.summaryPercentage

        init {

            totalTitle.text =
                    ApplicationUtility.getString(R.string.monthSummaryTotalTitle, expenseListFragment.context!!)
            leftTitle.text = ApplicationUtility.getString(R.string.monthSummaryLeftTitle, expenseListFragment.context!!)
            spendTitle.text =
                    ApplicationUtility.getString(R.string.monthSummarySpendTitle, expenseListFragment.context!!)

            compositeDisposable.addAll(
                RxView.clicks(itemView).subscribe {

                }
            )
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val value: TextView = itemView.singleExpenseValue
        val title: TextView = itemView.singleExpenseTitle
        val description: TextView = itemView.singleExpenseDescription

        init {

            compositeDisposable.addAll(
                RxView.clicks(itemView).subscribe {
                    expenseItemClickListener.onClick(getItem(adapterPosition - 1))
                },
                RxView.longClicks(itemView).subscribe {
                    expenseItemClickListener.onLongClick(getItem(adapterPosition - 1), itemView)
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