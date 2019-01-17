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
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxPopupMenu
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.util.ApplicationUtility
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.filteredListByDate
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentMonthAndYear
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getDateFromString
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.goToIntro
import com.nsnik.nrs.jaron.util.eventbus.RxBus
import com.nsnik.nrs.jaron.util.eventbus.RxEvent
import com.nsnik.nrs.jaron.util.factory.MonthSummaryFactory.Companion.getMonthSummary
import com.nsnik.nrs.jaron.view.fragments.adapters.ExpenseListAdapter
import com.nsnik.nrs.jaron.view.fragments.dialogs.AddExpenseFragment
import com.nsnik.nrs.jaron.view.fragments.listeners.ExpenseItemClickListener
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_expense_list.*
import timber.log.Timber
import java.util.*

class ExpenseListFragment : Fragment(), ExpenseItemClickListener {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var expenseListViewModel: ExpenseListViewModel
    private lateinit var expenseListAdapter: ExpenseListAdapter
    private lateinit var dateDisposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        goToIntro(activity!!)
        return inflater.inflate(R.layout.fragment_expense_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        listeners()
    }

    private fun initialize() {
        expenseListViewModel = ViewModelProviders.of(activity!!).get(ExpenseListViewModel::class.java)
        expenseListAdapter = ExpenseListAdapter(this)

        expenseFragmentExpenseList.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = expenseListAdapter
        }

        observeViewModel(getCurrentMonthAndYear())

        dateDisposable = RxBus.listen(RxEvent.NewDataSelectedEvent::class.java).subscribe {
            val date = getDateFromString(it.dateString)
            observeViewModel(date)
            expenseListViewModel.getCurrentDate().postValue(date)
        }
    }

    private fun observeViewModel(date: Date) =
        expenseListViewModel.getAllExpenses().observe(this, Observer { modifyList(it, date) })

    private fun modifyList(list: List<ExpenseEntity>, date: Date) {
        val filteredList = filteredListByDate(list, date)
        expenseListAdapter.submitList(filteredList)
        expenseListAdapter.submitSummary(getMonthSummary(activity!!, filteredList))
        //TODO fix this
        expenseListAdapter.notifyDataSetChanged()
    }


    private fun listeners() {
        compositeDisposable.addAll(
            RxView.clicks(expenseFragmentAddExpense).subscribe {
                AddExpenseFragment().show(fragmentManager, "addExpense")
            }
        )
    }

    override fun onClick(expenseEntity: ExpenseEntity) {
        Timber.d(expenseEntity.id.toString())
    }

    override fun onLongClick(expenseEntity: ExpenseEntity, view: View) {
        inflatePopupMenu(expenseEntity, view)
    }

    private fun inflatePopupMenu(expenseEntity: ExpenseEntity, view: View) {
        val popupMenu = PopupMenu(activity, view, Gravity.END)
        popupMenu.inflate(R.menu.expense_item_pop_up_menu)
        compositeDisposable.add(
            RxPopupMenu.itemClicks(popupMenu).subscribe {
                when (it.itemId) {
                    R.id.expensePopupEdit -> {

                    }
                    R.id.expensePopupDelete -> {
                        showAlertPopUpDialog(expenseEntity)

                    }
                }
            }
        )
        popupMenu.show()
    }

    private fun showAlertPopUpDialog(expenseEntity: ExpenseEntity) {
        AlertDialog.Builder(activity!!)
            .setTitle(ApplicationUtility.getString(R.string.alertDialogDeleteTitle,activity!!))
            .setMessage(ApplicationUtility.getString(R.string.alertDialogDeleteMessage,activity!!))
            .setPositiveButton(ApplicationUtility.getString(R.string.alertDialogDeletePositiveText,activity!!)) { dialog, which ->
                expenseListViewModel.deleteExpenses(listOf(expenseEntity))
            }
            .setNegativeButton(ApplicationUtility.getString(R.string.alertDialogDeleteNegativeText,activity!!)) { dialog, which ->
            }
            .create()
            .show()
    }

    private fun cleanUp() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        if (!dateDisposable.isDisposed) dateDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanUp()
    }
}
