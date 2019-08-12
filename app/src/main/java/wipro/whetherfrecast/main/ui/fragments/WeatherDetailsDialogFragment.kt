package wipro.whetherfrecast.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.dialogfragment_weatherdetails.*
import wipro.whetherfrecast.main.databinding.DialogfragmentWeatherdetailsBinding
import wipro.whetherfrecast.main.ui.base.BaseDialogFragment
import wipro.whetherfrecast.main.ui.model.WeatherDetails
import wipro.whetherfrecast.main.ui.viewmodel.WeatherDetailsViewModel
import wipro.whetherfrecast.main.utils.BundleConstants


/**
 * Created by sibaprasad on 12/08/19.
 */
class WeatherDetailsDialogFragment : BaseDialogFragment() {

    lateinit var weatherDetails: WeatherDetails
    lateinit var weatherDetailsViewModel: WeatherDetailsViewModel
    lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            weatherDetails = it.getParcelable(BundleConstants.WEATHER_DETAILS)
            cityName = it.getString(BundleConstants.CITY_NAME)
        }

        weatherDetailsViewModel = WeatherDetailsViewModel(weatherDetails, "$cityName Weather Forecast")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setLayout(wipro.whetherfrecast.main.R.layout.dialogfragment_weatherdetails)

        var binding: DialogfragmentWeatherdetailsBinding =
            DataBindingUtil.inflate(
                inflater,
                wipro.whetherfrecast.main.R.layout.dialogfragment_weatherdetails,
                container,
                false
            )

        val view = binding.root
        binding.weatherItemViewModel = weatherDetailsViewModel
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityName.let {
            setToolbarTitle(it)
        }

        imageViewBack.setOnClickListener { dismiss() }

    }

    companion object {
        val TAG = WeatherDetailsDialogFragment.javaClass.simpleName
        @JvmStatic
        fun newInstance(weatherdetails: WeatherDetails, cityName: String) =
            WeatherDetailsDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BundleConstants.WEATHER_DETAILS, weatherdetails)
                    putString(BundleConstants.CITY_NAME, cityName)
                }
            }
    }
}
