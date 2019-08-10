package wipro.whetherfrecast.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import wipro.whetherfrecast.main.listeners.ToolbarListener
import wipro.whetherfrecast.main.ui.fragments.WeatherFragment


class MainActivity : AppCompatActivity(), ToolbarListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()

        /**
         * On Screen rotate , it will call multiple times
         * Once SCreen rotate, it will call once
         */
        if (savedInstanceState == null) {
            loadWeatherFragment()
        }
    }

    fun loadWeatherFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, WeatherFragment.newInstance(), WeatherFragment.TAG).commit()
        WeatherFragment()
    }

    override fun setToolbarTitle(title: String) {
        toolbar.setTitle(title)
    }

    override fun setToolbarVisibility(value: Int) {

    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        val ab: ActionBar? = supportActionBar
        with(ab) {
            this?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            this?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showError(error: String = "ERROR") {
        val snackBar = Snackbar.make(
            findViewById(R.id.constraintLayoutParent),
            error, Snackbar.LENGTH_LONG
        )
        snackBar.show()
    }
}
