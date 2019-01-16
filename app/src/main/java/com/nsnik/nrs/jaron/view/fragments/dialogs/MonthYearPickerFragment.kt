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


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.util.ApplicationUtility
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.formatText
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getFormattedCurrentDate
import com.nsnik.nrs.jaron.util.eventbus.RxBus
import com.nsnik.nrs.jaron.util.eventbus.RxEvent
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_month_year_picker.*


class MonthYearPickerFragment : DialogFragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month_year_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        listeners()
    }

    private fun initialize() {

        monthYearPickerMonthList.apply {
            adapter = getAdapter(getMonthList()!!)
        }

        monthYearPickerYearList.apply {
            adapter = getAdapter(getYearList())
        }

        monthYearPickerMonthList?.onItemSelectedListener = onItemSelectedListener
        monthYearPickerYearList?.onItemSelectedListener = onItemSelectedListener

        monthYearPickerCurrentDate.text = getFormattedCurrentDate()

    }

    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            monthYearPickerCurrentDate.text = formatText(
                monthYearPickerCurrentDate.text.toString(),
                parent?.getItemAtPosition(position).toString()
            )
        }

    }

    private fun getAdapter(list: List<String>) = ArrayAdapter(activity, android.R.layout.simple_list_item_1, list)

    private fun listeners() = compositeDisposable.addAll(
        RxView.clicks(monthYearPickerOk).subscribe {
            RxBus.publish(RxEvent.NewDataSelectedEvent(monthYearPickerCurrentDate.text.toString()))
            dismiss()
        }
    )

    private fun getMonthList() = activity?.resources?.getStringArray(R.array.months)?.asList()

    private fun getYearList() = generateSequence(2019) { e -> e + 1 }.take(10).map { it.toString() }.toList()

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
