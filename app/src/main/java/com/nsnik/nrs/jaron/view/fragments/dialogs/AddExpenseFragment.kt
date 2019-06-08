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
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.formatTag
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getFormattedText
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getStringRes
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.listToTag
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.showNotification
import com.nsnik.nrs.jaron.util.FieldValidator.Companion.validateFrom
import com.nsnik.nrs.jaron.util.factory.ExpenseEntityFactory.Companion.createExpenseEntity
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import com.twitter.serial.stream.bytebuffer.ByteBufferSerial
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_add_expense.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

@Suppress("SENSELESS_COMPARISON", "UNNECESSARY_SAFE_CALL")
class AddExpenseFragment : DialogFragment() {

    private val dateFormat = "EEE, d MMM yyyy HH:mm:ss"
    private lateinit var thisDialog: Dialog
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var expenseListViewModel: ExpenseListViewModel
    private var toUpdateExpenseEntity: ExpenseEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.dialogWithTitle)
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

    private fun setFields() = arguments.let {
        toUpdateExpenseEntity =
            ByteBufferSerial().fromByteArray(it?.getByteArray(getStringRes(R.string.bundleExpenseEntity, activity!!)), ExpenseEntity.SERIALIZER)

        toUpdateExpenseEntity?.id = it?.getInt(getStringRes(R.string.bundleExpenseEntityId, activity!!), -1)!!

        if (it?.getBoolean(getStringRes(R.string.bundleEditorIsReadOnly, activity!!)) != null)
            if (it.getBoolean(getStringRes(R.string.bundleEditorIsReadOnly, activity!!), false)) disableFields()

        if (it != null) setViewFields()
    }

    private fun setViewFields() {
        newExpenseCreate.text = getStringRes(R.string.newExpenseUpdate, activity!!)
        toUpdateExpenseEntity.apply {
            newExpenseValue.setText(this?.amount?.value?.toString())
            newExpenseTitle.setText(this?.title)
            newExpenseDescription.setText(this?.description)
            newExpenseTags.setText(this?.tags?.reduce { acc, s -> "$acc $s" }?.trim())
        }
    }

    private fun disableFields() {
        listOf(newExpenseValue, newExpenseTitle, newExpenseDescription, newExpenseTags).forEach { it.isEnabled = false }
        listOf(newExpenseCreate, newExpenseCancel).forEach { it.visibility = View.GONE }
        newExpenseDate.visibility = View.VISIBLE
        newExpenseDate.text = String.format(
            getStringRes(R.string.newExpenseUpdatedDate, activity!!),
            SimpleDateFormat(dateFormat, Locale.ENGLISH).format(toUpdateExpenseEntity?.date)
        )
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
                formatTag(it.toString()).forEach(Consumer { s ->
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
                showNotification(activity!!, R.string.notificationExpenseAdded)
            } else {
                updateExpense()
                showNotification(activity!!, R.string.notificationExpenseUpdated)
            }
            dismiss()
        }
    }

    private fun addExpense() = expenseListViewModel.insertExpenses(listOf(createExpense()))

    private fun createExpense(date: Date = Calendar.getInstance().time) = createExpenseEntity(
        activity!!,
        newExpenseValue.text().toDouble(),
        newExpenseTitle.text(),
        newExpenseDescription.text(),
        date,
        formatTag(newExpenseTags.text())
    )

    private fun updateExpense() {
        val expenseEntity = createExpense(toUpdateExpenseEntity?.date!!)
        expenseEntity.id = toUpdateExpenseEntity?.id!!
        expenseListViewModel.updateExpenses(listOf(expenseEntity))
    }

    private fun addTags(strings: List<String>) = expenseListViewModel.insertTag(listToTag(strings))

    private fun validateEntries(): Boolean = validateFrom(activity!!, newExpenseValue, newExpenseTitle)

    private fun TextView.text() = text.toString()

    override fun onResume() {
        super.onResume()
        val params = dialog?.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }

    private fun cleanUp() = compositeDisposable.apply {
        clear()
        dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanUp()
    }
}
