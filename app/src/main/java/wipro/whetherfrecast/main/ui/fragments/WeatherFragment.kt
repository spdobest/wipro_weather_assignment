package wipro.whetherfrecast.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather.*
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.databinding.FragmentWeatherBinding
import wipro.whetherfrecast.main.ui.activity.MainActivity
import wipro.whetherfrecast.main.ui.adapter.WeatherAdapter
import wipro.whetherfrecast.main.ui.base.BaseFragment
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel

/**
 * Fragment TO show 5 days weather for a particular city
 */
class WeatherFragment : BaseFragment<FragmentWeatherBinding, WeatherViewModel>(),
    BaseNavigator, LifecycleOwner {

    var weatherList: ArrayList<WeatherDetails> = ArrayList()

    var searchedCityName = ""

    val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter(weatherList)
    }

    lateinit var weatherViewModel: WeatherViewModel


    override fun getLayoutId(): Int {
        return R.layout.fragment_weather
    }


    override fun setClickListener() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_weather, container, false)
        setLayout(R.layout.fragment_weather)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setAdapter()
        setupSwipeRefresh()
        setObservData()
    }

    override fun getViewModel(): WeatherViewModel {
        weatherViewModel = ViewModelProviders.of(this)[WeatherViewModel::class.java]
        return weatherViewModel
    }

    companion object {
        val TAG = WeatherFragment.javaClass.simpleName
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }

    override fun showProgress() {
        swipeContainer.isRefreshing = true
    }

    override fun hideProgress() {
        textViewError.visibility = View.GONE
        swipeContainer.isRefreshing = false
    }

    /**
     * Showing Error
     */
    override fun showError(error: String) {
        hideProgress()
        when (error) {
            context?.getString(R.string.err_nointernet) -> {
                (activity as MainActivity).showError(getString(R.string.err_nointernet), Snackbar.LENGTH_INDEFINITE)
            }
            context?.getString(R.string.err_invalid_city_name) -> {
                textViewError.visibility = View.GONE
                (activity as MainActivity).showError(
                    getString(R.string.err_invalid_city_name),
                    Snackbar.LENGTH_INDEFINITE
                )
            }
            else -> {
                if (error.isEmpty()) {
                    textViewError.visibility = View.GONE
                }
                textViewError.visibility = View.VISIBLE
                textViewError.text = error
            }
        }
    }

    /**
     * Initialize and setup for ui elements
     */
    override fun initialize() {
        searchedCityName = autocompleteTextViewWeather.text.toString()
        getViewModel().setNavigator(this)

        autocompleteTextViewWeather.setOnItemClickListener { adapterView, view, i, l ->
            searchedCityName = autocompleteTextViewWeather.text.toString()
            (activity as MainActivity).setToolbarTitle(searchedCityName)
        }

        if (getViewModel().mutableWeatherList.value != null && getViewModel().mutableWeatherList.value?.size != 0) {
            getViewModel().mutableWeatherList.value?.let { weatherAdapter.updateWeatherListItems(it) }
        }

        buttonSearch.setOnClickListener {
            onSearchCLick()
        }
    }

    /**
     * Search Button Click
     */
    fun onSearchCLick() {
        searchedCityName = autocompleteTextViewWeather.text.toString().toLowerCase().trim()
        searchedCityName.let { it ->
            getViewModel().searchCityWeather(activity as FragmentActivity, it)
        }
    }

    /**
     * SET up UI for Pull to refresh
     */
    fun setupSwipeRefresh() {

        swipeContainer.setOnRefreshListener {
            getViewModel().searchCityWeather(activity as FragmentActivity, searchedCityName)
        }

        swipeContainer.setColorSchemeColors(
            ContextCompat.getColor(activity as FragmentActivity, android.R.color.holo_blue_bright),
            ContextCompat.getColor(activity as FragmentActivity, android.R.color.holo_green_light),
            ContextCompat.getColor(activity as FragmentActivity, android.R.color.holo_orange_light),
            ContextCompat.getColor(activity as FragmentActivity, android.R.color.holo_red_light)
        )
    }

    /**
     * SETup weather recyclerview Adapter
     */
    fun setAdapter() {
        recyclerView.adapter = weatherAdapter
        val countries = resources.getStringArray(R.array.india_top_cities)
        val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, countries)
        autocompleteTextViewWeather.setAdapter(adapter)
    }

    /**
     * set all the observeable Livedata in view model
     */
    fun setObservData() {
        getViewModel().fetchCityWeatherListFromServer(activity as FragmentActivity)
            .observe(this, Observer {
                weatherAdapter.updateWeatherListItems(it)
                showError("")
            })

        getViewModel().getProgressStatus()?.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        getViewModel().getErrorStatus()?.observe(this, Observer {
            if (it.isNotEmpty()) showError(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getViewModel().nullifyParameters()
    }

}
