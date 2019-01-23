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

package com.nsnik.nrs.jaron.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.jakewharton.rxbinding2.view.RxView
import com.nsnik.nrs.jaron.BuildConfig
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.util.ApplicationUtility
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getFormattedCurrentDate
import com.nsnik.nrs.jaron.view.fragments.dialogs.AboutFragment
import com.nsnik.nrs.jaron.view.fragments.dialogs.MonthYearPickerFragment
import com.nsnik.nrs.jaron.viewModel.ExpenseListViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var expenseListViewModel: ExpenseListViewModel
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setToolBarDate()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun setToolBarDate() {
        expenseListViewModel = ViewModelProviders.of(this).get(ExpenseListViewModel::class.java)
        changeToolBarDate()
    }

    private fun changeToolBarDate() {
        expenseListViewModel.getCurrentDate().observe(this, Observer {
            title = String.format("%s %s", getFormattedCurrentDate(it), "▼")
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuItemSearch -> {
            }
            R.id.menuItemSettings -> findNavController(R.id.mainNavHost).navigate(R.id.preferenceFragment)
            R.id.menuItemAbout -> AboutFragment().show(supportFragmentManager, "about")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialize() {

        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, null)

        setSupportActionBar(mainToolbar)

        controller = findNavController(R.id.mainNavHost)

        controller.addOnDestinationChangedListener { controller, destination, arguments ->
            mainToolbar.visibility = if (destination.id == R.id.introFragment) View.GONE else View.VISIBLE
            if (destination.id == R.id.expenseList) {
                changeToolBarDate()
            } else {
                title = ApplicationUtility.getString(R.string.Preferences, this@MainActivity)
            }
        }


        setupActionBarWithNavController(controller, AppBarConfiguration(controller.graph))

        compositeDisposable.addAll(
            RxView.clicks(mainToolbar).subscribe {
                if (controller.currentDestination?.id == R.id.expenseList)
                    MonthYearPickerFragment().show(supportFragmentManager, "picker")
            }
        )
    }

    private fun cleanUp() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    override fun onSupportNavigateUp(): Boolean = controller.navigateUp() || super.onSupportNavigateUp()

    override fun onDestroy() {
        super.onDestroy()
        cleanUp()
        if (BuildConfig.DEBUG) MyApplication.getRefWatcher(this)?.watch(this)
    }

}
