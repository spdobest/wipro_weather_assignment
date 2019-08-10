package wipro.whetherfrecast.main.ui.viewmodel


import androidx.lifecycle.ViewModel
import wipro.whetherfrecast.main.ui.model.*
import wipro.whetherfrecast.main.utils.CommonUtils

class WeatherItemViewModel(weatherDetails: WeatherDetails) : ViewModel() {

    lateinit var main: Main
    lateinit var weather: Weather
    lateinit var wind: Wind
    lateinit var rain: Rain
    lateinit var dateTime: String

    companion object {
        val TAG = "TopMovieViewModel"
    }

    init {
        weatherDetails?.let {
            main = it.main
            wind = it.wind
            rain = it.rain
            dateTime = it.dt_txt

            it.weather?.let {
                if (it.size > 0) {
                    weather = weatherDetails.weather.get(0)
                }
            }
        }
    }

    fun getWearherDate(): String {
        var date = "--"
        dateTime?.let {
            date = CommonUtils.getDateInddMMMYYYYFormat(it)
        }
        return date
    }

    fun getWeatherTime(): String {
        var time = "--"
        dateTime?.let {
            time = CommonUtils.getDateInddHHmmFormat(it)
        }
        return time
    }

    fun getWearherDay(): String {
        var day = ""
        dateTime?.let {
            day = CommonUtils.getDayOftheWeek(it)
        }
        return day
    }

    fun getWearherIcon(): String {
        var icon = "--"
        weather?.let {
            icon = it.icon
        }
        return icon
    }

    fun getWeatherTitle(): String {
        var title = "--"
        weather?.let {
            title = it.main
        }
        return title
    }

    fun getTemperture(): String {
        var tempeture = ""
        main?.let {
            tempeture =
                "MAX " + it.temp_min.toString() + 0x00B0.toChar() + " - MIN " + it.temp_min.toString() + 0x00B0.toChar()
        }
        return tempeture
    }

    fun getWindDetails(): String {
        var windDetails = "--"
        wind?.let {
            windDetails =
                "SPEED " + it.speed.toString() + "\n DEGREE " + it.deg.toString() + 0x00B0.toChar()
        }
        return windDetails
    }

    fun getWeatherDetailsMessage(): String {
        var weatherDescription = "--"
        weather?.let {
            weatherDescription = it.main + " : "
        }

        main?.let {
            weatherDescription =
                weatherDescription + "High " + it.temp_min.toString() + 0x00B0.toChar() + " - Low " + it.temp_min.toString() + 0x00B0.toChar()
        }

        wind?.let {
            weatherDescription =
                weatherDescription + " Winds W at " + it.speed.toString() + "\n at " + it.deg.toString() + 0x00B0.toChar()
        }

        main?.let {
            weatherDescription =
                weatherDescription + " With Humidity " + it.humidity
        }

        return weatherDescription
    }

    fun getWeatherColor(): String {
        weather?.let {
            return it.main
        }
    }
}