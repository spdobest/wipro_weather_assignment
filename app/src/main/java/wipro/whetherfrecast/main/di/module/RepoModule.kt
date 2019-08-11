package wipro.whetherfrecast.main.di.module

import dagger.Module
import dagger.Provides
import wipro.whetherfrecast.main.network.ApiServiceInterface
import wipro.whetherfrecast.main.ui.repository.WeatherRepository
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class))
class RepoModule {
    @Singleton
    @Provides
    fun providesWeatherRepo(apiServiceInterface: ApiServiceInterface): WeatherRepository {
        return WeatherRepository(apiServiceInterface)
    }
}