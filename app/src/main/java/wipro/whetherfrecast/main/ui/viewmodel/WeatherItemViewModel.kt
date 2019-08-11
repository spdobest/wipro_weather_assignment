package wipro.whetherfrecast.main.ui.viewmodel


import androidx.lifecycle.ViewModel
import wipro.whetherfrecast.main.ui.model.*
import wipro.whetherfrecast.main.utils.CommonUtils
import kotlin.math.round

class WeatherItemViewModel(weatherDetails: WeatherDetails) : ViewModel() {

    lateinit var main: Main
    lateinit var weather: Weather
    lateinit var wind: Wind
    lateinit var rain: Rain
    lateinit var cloud: Clouds
    lateinit var dateTime: String

    companion object {
        const val TAG = "TopMovieViewModel"
    }

    init {
        weatherDetails?.let {
            main = it.main
            wind = it.wind
            rain = it.rain
            cloud = it.clouds
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
                "MAX " + CommonUtils.getCelciousFromKalvin(it.temp_max) + " - MIN " + CommonUtils.getCelciousFromKalvin(
                    it.temp_min
                )
        }
        return tempeture
    }

    fun getWindDetails(): String {
        var windDetails = "--"
        wind?.let {
            windDetails =
                "Speed " + it.speed.toString() + " m/sec" + "\n Direction " + round((it.deg * 100) / 100).toString() + 0x00B0.toChar()
        }
        return windDetails
    }

    fun getCloudyness(): String {
        var cloudyNess = "--"
        cloud?.let {
            cloudyNess = "Cloudiness \n${it.all}%"
        }
        return cloudyNess
    }

    fun getWeatherDetailsMessage(): String {
        var weatherDescription = "--"
        weather?.let {
            weatherDescription = it.main + " : "
        }

        main?.let {
            weatherDescription =
                weatherDescription + "High " + CommonUtils.getCelciousFromKalvin(it.temp_max) + " - Low " + CommonUtils.getCelciousFromKalvin(
                    it.temp_min
                )
        }

        wind?.let {
            weatherDescription =
                weatherDescription + " Winds W at " + it.speed.toString() + "\n at " + it.deg.toString() + 0x00B0.toChar()
        }

        main?.let {
            weatherDescription =
                weatherDescription + " With Humidity " + it.humidity + " Pressure " + it.pressure + "hpa"

        }


        return weatherDescription
    }
}