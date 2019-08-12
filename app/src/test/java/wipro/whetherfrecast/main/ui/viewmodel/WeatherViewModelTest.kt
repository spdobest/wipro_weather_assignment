package wipro.whetherfrecast.main.ui.viewmodel

import android.content.Context
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.only
import org.mockito.Mockito.verify
import wipro.whetherfrecast.main.ui.fragments.WeatherFragment

@RunWith(JUnit4::class)
class WeatherViewModelTest {

    @Mock
    lateinit var weatherFragment: WeatherFragment

    @Mock
    lateinit var weatherViewModel: WeatherViewModel

    @Mock
    lateinit var context: Context

    @Before
    @Throws(Exception::class)
    fun setUp() {
        weatherFragment = WeatherFragment.newInstance()
    }

    @Test
    fun onSearchButtonClickTest() {
        weatherViewModel.checkNetworkAndProcessd(context)
        verify<WeatherFragment>(weatherFragment, only()).onSearchCLick()
    }

    @Test
    fun nullifyParameters_Invoked() {
        weatherViewModel.nullifyParameters()
        assertEquals(null, weatherViewModel.errorMessage)
        assertEquals(null, weatherViewModel.progressStatus)
        assertEquals(null, weatherViewModel.cityName)
    }

    @Test
    fun citynameValidation_Test() {
        assertTrue("Invalid City Name", weatherViewModel.isCityNameValid(""))
        assertTrue("Invalid City Name", weatherViewModel.isCityNameValid("12"))
        assertTrue("Valid City Name ", weatherViewModel.isCityNameValid("Bhubaneswar"))
    }
}