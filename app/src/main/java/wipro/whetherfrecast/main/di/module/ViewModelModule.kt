package wipro.whetherfrecast.main.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import wipro.whetherfrecast.main.ui.viewmodel.WeatherViewModel

@Module(includes = arrayOf(ApiModule::class))
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun providesWeatherViweModel(weatherRepository: WeatherViewModel): ViewModel
}