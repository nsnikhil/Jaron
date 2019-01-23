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

package com.nsnik.nrs.jaron.view.fragments.dialogs


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.data.ExpenseEntity
import com.nsnik.nrs.jaron.util.ApplicationUtility
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.formatTag
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getFormattedText
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.listToTag
import com.nsnik.nrs.jaron.util.FieldValidator.Companion.validateFrom
import com.nsnik.nrs.jaron.util.factory.ExpenseEntityFactory.Companion.createExpenseEntity
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import com.twitter.serial.stream.bytebuffer.ByteBufferSerial
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_add_expense.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer


class AddExpenseFragment : DialogFragment() {

    private lateinit var thisDialog: Dialog
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var expenseListViewModel: ExpenseListViewModel
    private var toUpdateExpenseEntity: ExpenseEntity? = null

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
        setFields()
        initialize()
        listeners()
    }

    private fun setFields() {
        if (arguments != null) {
            val byteArray = arguments?.getByteArray(ApplicationUtility.getString(R.string.bundleExpenseEntity, activity!!))
            toUpdateExpenseEntity = ByteBufferSerial().fromByteArray(byteArray, ExpenseEntity.SERIALIZER)
            toUpdateExpenseEntity?.id = arguments?.getInt(ApplicationUtility.getString(R.string.bundleExpenseEntityId, activity!!), -1)!!
            newExpenseCreate.text = ApplicationUtility.getString(R.string.newExpenseUpdate, activity!!)
            newExpenseValue.setText(toUpdateExpenseEntity?.amount?.value?.toString())
            newExpenseTitle.setText(toUpdateExpenseEntity?.title)
            newExpenseDescription.setText(toUpdateExpenseEntity?.description)
            newExpenseTags.setText(toUpdateExpenseEntity?.tags?.reduce { acc, s -> "$acc $s" }?.trim())
        }
    }

    private fun initialize() {
        expenseListViewModel = ViewModelProviders.of(activity!!).get(ExpenseListViewModel::class.java)
    }

    private fun listeners() = compositeDisposable.addAll(
        RxView.clicks(newExpenseCancel).subscribe {
            dismiss()
        },
        RxView.clicks(newExpenseCreate).subscribe {
            addEntries()
        },
        RxTextView.textChanges(newExpenseTags)
            .debounce(2, TimeUnit.SECONDS)
            .subscribe {
                ApplicationUtility.formatTag(it.toString()).forEach(Consumer { s ->
                    Timber.d(s)
                })
            }
    )


    private fun addEntries() {
        if (validateEntries()) {
            if (toUpdateExpenseEntity == null) {
                addExpense()
                val strings = formatTag(newExpenseTags.text())
                if (strings.isNotEmpty()) addTags(strings)
            } else {
                updateExpense()
            }
            dismiss()
        }
    }


    private fun addExpense() = expenseListViewModel.insertExpenses(listOf(createExpense()))

    private fun createExpense(date: Date = Calendar.getInstance().time) = createExpenseEntity(
        newExpenseValue.text().toDouble(),
        newExpenseTitle.text(),
        newExpenseDescription.text(),
        date,
        formatTag(newExpenseTags.text())
    )

    private fun updateExpense() {
        val expenseEntity = createExpense(date = toUpdateExpenseEntity?.date!!)
        expenseEntity.id = toUpdateExpenseEntity?.id!!
        expenseListViewModel.updateExpenses(listOf(expenseEntity))
    }

    private fun addTags(strings: List<String>) = expenseListViewModel.insertTag(listToTag(strings))

    private fun validateEntries(): Boolean = validateFrom(
        activity!!,
        newExpenseValue,
        newExpenseTitle,
        newExpenseDescription,
        Calendar.getInstance().time,
        formatTag(newExpenseTags.text())
    )

    private fun TextView.text() = text.toString()

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
