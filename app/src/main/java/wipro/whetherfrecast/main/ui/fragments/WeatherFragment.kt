package wipro.whetherfrecast.main.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather.*
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.databinding.FragmentWeatherBinding
import wipro.whetherfrecast.main.listeners.WeatherClickListener
import wipro.whetherfrecast.main.ui.activity.MainActivity
import wipro.whetherfrecast.main.ui.adapter.WeatherAdapter
import wipro.whetherfrecast.main.ui.base.BaseFragment
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel

/**
 * Fragment TO show 5 days weather for a particular city
 */
class WeatherFragment : BaseFragment<FragmentWeatherBinding, WeatherViewModel>(), LifecycleOwner, WeatherClickListener {

    var weatherList: ArrayList<WeatherDetails> = ArrayList()

    var searchedCityName = ""

    val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter(weatherList)
    }

    lateinit var weatherViewModel: WeatherViewModel


    override fun getLayoutId(): Int {
        return R.layout.fragment_weather
    }


    override fun setClickListener() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentWeatherBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_weather,
                container,
                false
            )

        val view = binding.root
        binding.weatherViewmodel = getViewModel()
        binding.lifecycleOwner = this

        return view
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

    fun hideProgress() {
        //swipeContainer.isRefreshing = false
        hideProgressBar()
    }

    /**
     * Showing Error
     */
    fun showError(error: String) {
        hideProgress()
        when (error) {
            context?.getString(R.string.err_nointernet) -> {
                (activity as MainActivity).showError(getString(R.string.err_nointernet), Snackbar.LENGTH_INDEFINITE)
            }
            context?.getString(R.string.err_invalid_city_name) -> {
                hideError()
                (activity as MainActivity).showError(
                    getString(R.string.err_invalid_city_name),
                    Snackbar.LENGTH_INDEFINITE
                )
            }
            else -> {
                if (error.isEmpty()) {
                    showErrorBase("Error")
                } else {
                    showErrorBase(error)
                }
            }
        }
    }

    /**
     * Initialize and setup for ui elements
     */
    override fun initialize() {

        Handler().postDelayed(Runnable { hideProgress() }, 3000)


        searchedCityName = autocompleteTextViewWeather.text.toString()
        (activity as MainActivity).showError("London Weather Forecat")
        autocompleteTextViewWeather.setOnItemClickListener { adapterView, view, i, l ->
            searchedCityName = autocompleteTextViewWeather.text.toString()
            (activity as MainActivity).setToolbarTitle(searchedCityName)
        }

        if (getViewModel().mutableWeatherList.value != null && getViewModel().mutableWeatherList.value?.size != 0) {
            getViewModel().mutableWeatherList.value?.let { weatherAdapter.updateWeatherListItems(it) }
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
        weatherAdapter.setWeatherClcik(this)
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
            if (it) showProgressBar() else hideProgress()
        })

        getViewModel().getErrorStatus()?.observe(this, Observer {
            if (it.isNotEmpty()) showError(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getViewModel().nullifyParameters()
    }

    override fun onWeatherClick(weatherDetails: WeatherDetails) {
        showWeatherDetailsFragment(weatherDetails)
    }

    fun showWeatherDetailsFragment(weatherDetails: WeatherDetails) {
        activity?.supportFragmentManager?.let {
            WeatherDetailsDialogFragment.newInstance(weatherDetails, searchedCityName).show(
                it, "TAG"
            )
        }
    }
}
