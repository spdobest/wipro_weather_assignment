package wipro.whetherfrecast.main.ui.viewmodel

import android.app.Application
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.network.ApiServiceInterface
import wipro.whetherfrecast.main.network.ResponseCallBack
import wipro.whetherfrecast.main.ui.adapter.WeatherAdapter
import wipro.whetherfrecast.main.ui.base.BaseViewModel
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.model.WeatherResponse
import wipro.whetherfrecast.main.ui.navigator.BaseNavigator
import wipro.whetherfrecast.main.ui.repository.WeatherRepository
import wipro.whetherfrecast.main.utils.CommonUtils
import javax.inject.Inject


class WeatherViewModel constructor(application: Application) :
    BaseViewModel<BaseNavigator>(application),
    Observable, ResponseCallBack<Any> {

    lateinit var appCOntext: Application
    lateinit var mWeatherRepository: WeatherRepository

    @Inject
    constructor(weatherRepository: WeatherRepository, application: Application) : this(application) {
        this.appCOntext = application
        this.mWeatherRepository = weatherRepository
    }

//    constructor(val weatherRepository: WeatherRepository, application: Application){}

    var cityName: String? = ""

    var apiServiceInterface: ApiServiceInterface

    var mutableWeatherList: MutableLiveData<List<WeatherDetails>> = MutableLiveData()
    var weatherDetailsList: ArrayList<WeatherDetails> = ArrayList()

    var progressStatus: MutableLiveData<Boolean>? = MutableLiveData()
    var errorMessage: MutableLiveData<String>? = MutableLiveData()


    val adapter: WeatherAdapter by lazy {
        WeatherAdapter(weatherDetailsList)
    }

    init {
        apiServiceInterface = ApiServiceInterface.create()
    }


    fun fetchCityWeatherListFromServer(
        context: Context?,
        cityName: String = "London"
    ): MutableLiveData<List<WeatherDetails>> {
        checkNetworkAndProcessd(context)
        setProgress(true)
        mWeatherRepository.getWeatherListForCity(cityName, object : ResponseCallBack<WeatherResponse> {
            override fun onSuccess(value: WeatherResponse) {
                when (value) {
                    is WeatherResponse -> {
                        var weatherResponse = value as? WeatherResponse
                        handleWeatherResponse(weatherResponse)
                    }
                }
            }

            override fun onError(error: String) {
                setError(error)
            }
        })
        return mutableWeatherList
    }

    fun getWeatherAdapter(): WeatherAdapter {
        return adapter
    }

    @Bindable
    fun getData(): List<WeatherDetails> {
        return this.weatherDetailsList
    }

    @Bindable
    fun getMYAdapter(): WeatherAdapter {
        return this.adapter
    }

    companion object {
        val TAG = "TopMovieViewModel"
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    @Bindable
    fun getCityTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setCity(s.toString())
            }

            override fun afterTextChanged(s: Editable) {

            }
        }
    }

    fun setCity(name: String) {
        this.cityName = name
    }

    fun getProgressStatus(): LiveData<Boolean>? {
        return progressStatus
    }

    fun setProgress(progress: Boolean) {
        progressStatus?.value = progress
    }

    fun getErrorStatus(): LiveData<String>? {
        return errorMessage
    }

    fun setError(errMsg: String) {
        errorMessage?.value = errMsg
    }

    fun searchCityWeather(context: Context, cityName: String) {
        if (isCityNameValid(cityName)) {
            errorMessage?.value = context.getString(R.string.err_invalid_city_name)
        } else {
            fetchCityWeatherListFromServer(context, cityName)
        }
    }

    fun isCityNameValid(city: String): Boolean {
        return if (city.isEmpty() || (city.isNotEmpty() && city.length < 2)) false else true
    }

    fun checkNetworkAndProcessd(context: Context?): Boolean {
        var proceed = true
        if (!CommonUtils.isNetworkAvailable(context as AppCompatActivity)) {
            setError(context.getString(R.string.err_nointernet))
            proceed = false
        }
        return proceed
    }

    fun nullifyParameters() {
        cityName = null
        progressStatus = null
        errorMessage = null
    }

    fun handleWeatherResponse(response: WeatherResponse?) {

        setError("")
        Log.i("Response", response.toString())

        response?.let {
            with(it) {
                this.let {

                    setProgress(false)

                    Log.i("TAG", it.toString())

                    if (it.list.isNotEmpty()) {
                        weatherDetailsList.addAll(it.list)
                        mutableWeatherList.value = weatherDetailsList
                        adapter.updateWeatherListItems(it.list)
                    } else {
                        setError("No Data Found")
                    }
                }
            }
        }
    }
}