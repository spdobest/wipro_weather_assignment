package wipro.whetherfrecast.main.ui.fragments

interface BaseNavigator {
    fun showError(msg: String)
    fun showProgress()
    fun hideProgress()
}