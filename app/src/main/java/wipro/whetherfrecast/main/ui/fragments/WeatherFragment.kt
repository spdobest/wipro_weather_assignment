package wipro.whetherfrecast.main.ui.fragments

import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_weather.*
import wipro.whetherfrecast.main.ui.activity.MainActivity
import wipro.whetherfrecast.main.databinding.FragmentWeatherBinding
import wipro.whetherfrecast.main.ui.adapter.WeatherAdapter
import wipro.whetherfrecast.main.ui.base.BaseFragment
import wipro.whetherfrecast.main.ui.base.BaseNavigator
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel


class WeatherFragment() : BaseFragment<FragmentWeatherBinding, WeatherViewModel>(), BaseNavigator, LifecycleOwner {

    var weatherList = ArrayList<WeatherDetails>()

    val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter(weatherList)
    }

    lateinit var weatherViewModel: WeatherViewModel

    init {

    }

    override fun getLayoutId(): Int {
        return wipro.whetherfrecast.main.R.layout.fragment_weather
    }


    override fun setClickListener() {

    }

    // TODO: Rename and change types of parameters
    private var isFevoriteTab: Boolean = false

    constructor(parcel: Parcel) : this() {
        isFevoriteTab = parcel.readByte() != 0.toByte()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(wipro.whetherfrecast.main.R.layout.fragment_weather, container, false)
        setLayout(wipro.whetherfrecast.main.R.layout.fragment_weather)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
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

    override fun onSuccess(weatherList: List<WeatherDetails>) {
        this.weatherList.addAll(weatherList)
    }

    override fun onFail(err: String) {

    }

    override fun showProgress() {
        progressbarLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbarLoading.visibility = View.GONE
    }

    override fun showError(error: String) {
        textViewError.visibility = View.VISIBLE
        textViewError.text = error
    }

    override fun initialize() {}

    fun setUpView() {

        showBaseProgressBar()

        recyclerView.adapter = weatherAdapter

        val countries = resources.getStringArray(wipro.whetherfrecast.main.R.array.india_top_cities)
        val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, countries)
        autocompleteTextViewWeather.setAdapter(adapter)


        autocompleteTextViewWeather.setOnItemClickListener { adapterView, view, i, l ->
            with(adapterView?.getSelectedItem()) {
                (activity as MainActivity).setToolbarTitle(autocompleteTextViewWeather.text.toString())
            }
        }

        if (getViewModel() != null && getViewModel().mutableWeatherList != null && getViewModel().mutableWeatherList.value != null
            && getViewModel().mutableWeatherList.value?.size != 0
        ) {
            getViewModel().mutableWeatherList.value?.let { weatherAdapter.updateWeatherListItems(it) }
        } else {
            getViewModel().getWeatherListForCity("Pune").observe(this, Observer {
                weatherAdapter.updateWeatherListItems(it)
            })
        }

        buttonSearch.setOnClickListener {
            var city = autocompleteTextViewWeather.text.toString().toLowerCase().trim()

            city?.let {
                if (it.length > 3) {
                    (activity as MainActivity).setToolbarTitle(it)

                    getViewModel().checkNetworkAndgetWeatherDetails(activity as? MainActivity?, it)

                    getViewModel().getWeatherListForCity(it).observe(this, Observer {
                        weatherAdapter.updateWeatherListItems(it)
                    })
                } else {
                    (activity as MainActivity).setToolbarTitle("Enter Valid City")
                    (activity as MainActivity).showError("Enter Valid City")
                }
            }
        }

        getViewModel().getProgressStatus().observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        getViewModel().getErrorStatus().observe(this, Observer {
            if (it.isEmpty()) showError("") else showError(it)
        })
    }
}
