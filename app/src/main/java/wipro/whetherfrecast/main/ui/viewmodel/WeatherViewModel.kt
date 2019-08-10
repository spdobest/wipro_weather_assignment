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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wipro.whetherfrecast.main.ui.activity.MainActivity
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.network.ApiServiceInterface
import wipro.whetherfrecast.main.ui.adapter.WeatherAdapter
import wipro.whetherfrecast.main.ui.base.BaseNavigator
import wipro.whetherfrecast.main.ui.base.BaseViewModel
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.model.WeatherResponse
import wipro.whetherfrecast.main.utils.CommonUtils


class WeatherViewModel constructor(application: Application) : BaseViewModel<BaseNavigator>(application), Observable{

    var cityName: String = ""

    var apiServiceInterface: ApiServiceInterface

    var mutableWeatherList: MutableLiveData<List<WeatherDetails>> = MutableLiveData()
    var weatherDetailsList: ArrayList<WeatherDetails> = ArrayList()

    var progressStatus: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()


    val adapter: WeatherAdapter by lazy {
        WeatherAdapter(weatherDetailsList)
    }

    init {
        apiServiceInterface = ApiServiceInterface.create()
    }


    fun getWeatherListForCity(cityName: String = ""): MutableLiveData<List<WeatherDetails>> {

        setProgress(true)

        val call = apiServiceInterface.getWeatherDetails(cityName, "7726cc18a2f0b0e79676dc02fdf5fa69")
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {

                    errorMessage.value = ""

                    Log.i("Response", response.toString())

                    response?.let {
                        it.body()?.let {
                            it?.let {
                                with(it) {
                                    this?.let {
                                        setProgress(false)
                                        android.util.Log.i("TAG", it?.toString())

                                        if (it.list.size > 0) {
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
                } else {
                    setProgress(false)
                    setError("No Data Found")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("ERROR", call.toString())
                setError("Server Error")
                setProgress(false)
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

    fun checkNetworkAndgetWeatherDetails(context: Context?, cityName: String = "") {
        if (!CommonUtils.isNetworkAvailable(context as AppCompatActivity)) {
            errorMessage.value = context.getString(R.string.no_internet)

            if (context is MainActivity) {
                (context as MainActivity).showError(errorMessage.value.toString())
            }
        } else {
            getWeatherListForCity(cityName)
        }
        // getWeatherListForCity(cityName)
    }

    fun showOrHideError(): Boolean {
        var isError = false
        errorMessage.value?.let {
            if (!it.isEmpty()) {
                isError = false
            }
        }
        return isError
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

    fun getProgressStatus(): LiveData<Boolean> {
        return progressStatus
    }

    fun setProgress(progress: Boolean) {
        progressStatus.value = progress
    }

    fun getErrorStatus(): LiveData<String> {
        return errorMessage
    }

    fun setError(errMsg: String) {
        errorMessage.value = errMsg
    }
}