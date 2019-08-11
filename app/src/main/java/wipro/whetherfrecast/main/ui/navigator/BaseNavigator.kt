package wipro.whetherfrecast.main.ui.navigator

import wipro.whetherfrecast.main.ui.model.WeatherDetails

interface BaseNavigator {
    fun showError(msg: String)
    fun showProgress()
    fun hideProgress()
    fun onSuccess(weatherList: List<WeatherDetails>)
    fun onFail(error: String)
}