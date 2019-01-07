package com.nsnik.nrs.jaron.view.fragments.adapters

import androidx.recyclerview.widget.DiffUtil
import com.nsnik.nrs.jaron.data.ExpenseEntity

class ExpenseEntityDiffUtil : DiffUtil.ItemCallback<ExpenseEntity>() {

    override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
        return false
    }

}