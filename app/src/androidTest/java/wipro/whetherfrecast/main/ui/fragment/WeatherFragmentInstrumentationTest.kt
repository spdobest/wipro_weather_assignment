package wipro.whetherfrecast.main.ui.fragment

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.ui.activity.MainActivity
import wipro.whetherfrecast.main.ui.adapter.viewholders.WeatherViewHolder
import wipro.whetherfrecast.main.ui.fragments.WeatherFragment
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel
import wipro.whetherfrecast.main.utils.CommonUtils

@RunWith(JUnit4::class)
class WeatherFragmentInstrumentationTest {

    @Rule
    @JvmField
    public val rule = getRule()

    private val username_tobe_typed = "Ajesh"
    private val correct_password = "password"
    private val wrong_password = "passme123"

    @Mock
    var weatherViewModel: WeatherViewModel = Mockito.mock(WeatherViewModel::class.java)

    private lateinit var instrumentationCtx: Context

    /* @Rule
     var fragmentTestRule = MvvmFactFragment.create(MvvmFactFragment::class.java)*/


    private fun getRule(): ActivityTestRule<MainActivity> {
        Log.e("Initalising rule", "getting Mainactivity")
        return ActivityTestRule(MainActivity::class.java)
    }

    companion object {


    }


    @BeforeClass
    fun before_class_method() {
        Log.e("@Before Class", "Hi this is run before anything")
        rule.activity.loadWeatherFragment(WeatherFragment.newInstance())
        instrumentationCtx = InstrumentationRegistry.getContext()
    }


    @AfterClass
    fun after_class_method() {
        Log.e("@After Class", "Hi this is run after everything")
    }

    @Before
    fun before_test_method() {
        Log.e("@Before", "Hi this is run before every test function")
    }


    @Test
    fun weatherFetch_success() {

    }

    @Test
    fun weatherFetch_failure() {

    }

    @Test
    fun testInternetConnection() {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(message: Message) {
                if (CommonUtils.isNetworkAvailable(instrumentationCtx as AppCompatActivity)) {
                    Toast.makeText(instrumentationCtx, "Internet Available", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(instrumentationCtx, "No Internet Available", Toast.LENGTH_SHORT).show()
                }
                Handler().postDelayed(Runnable { }, 5000)
            }
        }
    }

    @After
    fun after_test_method() {
        Log.e("@After", "Hi this is run after every test function")
    }

    @Test
    fun weatherList_CheckValueAtPosition() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(RecyclerViewActions.scrollTo<WeatherViewHolder>(withId(R.id.constraintItem)))
        Espresso.onView(
            CoreMatchers.allOf(ViewMatchers.withId(R.id.textViewWeatherType), ViewMatchers.withText("Flag"))
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun testGetWeatherList() {

        weatherViewModel.fetchCityWeatherListFromServer(instrumentationCtx, "London")

        assertTrue(!rule.activity.isFinishing)

        onData(withId(R.id.cardRootItem))
            .inAdapterView(withId(R.id.constraintItem))
            .atPosition(0)
            .perform(click())
    }

}