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

package com.nsnik.nrs.jaron.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.view.fragments.adapters.ExpenseListAdapter
import com.nsnik.nrs.jaron.view.fragments.dialogs.AddExpenseFragment
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_expense_list.*

class ExpenseListFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var expenseListViewModel: ExpenseListViewModel
    private lateinit var expenseListAdapter: ExpenseListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expense_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        listeners()
    }

    private fun initialize() {
        expenseListViewModel = ViewModelProviders.of(this).get(ExpenseListViewModel::class.java)
        expenseListAdapter = ExpenseListAdapter()

        expenseFragmentExpenseList.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = expenseListAdapter
        }

        expenseListViewModel.getAllExpenses().observe(this, Observer {
            expenseListAdapter.submitList(it)
        })
    }

    private fun listeners() {
        compositeDisposable.addAll(
            RxView.clicks(expenseFragmentAddExpense).subscribe {
                AddExpenseFragment().show(fragmentManager, "addExpense")
            }
        )
    }


    private fun cleanUp() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanUp()
    }

}
