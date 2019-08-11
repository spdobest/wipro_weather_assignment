package wipro.whetherfrecast.main.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import wipro.whetherfrecast.main.R
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
            loadWeatherFragment(WeatherFragment.newInstance())
        }
    }

    fun loadWeatherFragment(weatherFragment: WeatherFragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer,weatherFragment , WeatherFragment.TAG).commit()
        WeatherFragment()
    }

    override fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    override fun setToolbarVisibility(value: Int) {

    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val ab: ActionBar? = supportActionBar
        with(ab) {
            this?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            this?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showError(error: String = "ERROR", duration: Int = Snackbar.LENGTH_SHORT) {
        val snackBar = Snackbar.make(
            findViewById(R.id.constraintLayoutParent),
            error, duration
        )
        snackBar.show()
    }
}
