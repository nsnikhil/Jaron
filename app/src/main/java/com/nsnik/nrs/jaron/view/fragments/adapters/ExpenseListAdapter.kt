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