package com.nsnik.nrs.jaron.view.fragments.dialogs


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getFormattedText
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_add_expense.*


class AddExpenseFragment : DialogFragment() {

    private lateinit var thisDialog: Dialog
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var expenseListViewModel: ExpenseListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogWithTitle)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        thisDialog = super.onCreateDialog(savedInstanceState)
        thisDialog.setTitle(getFormattedText(activity?.resources?.getString(R.string.newExpenseDialogTitle)))
        return thisDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        listeners()
    }

    private fun initialize() {
        expenseListViewModel = ViewModelProviders.of(this).get(ExpenseListViewModel::class.java)
    }

    private fun listeners() {
        compositeDisposable.addAll(
            RxView.clicks(newExpenseCancel).subscribe {
                dismiss()
            },
            RxView.clicks(newExpenseCreate).subscribe {

            }
        )
    }

    private fun addExpense() {

    }

    private fun validateEntries(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
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
