package wipro.whetherfrecast.main.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import wipro.whetherfrecast.main.ForecastApplication
import wipro.whetherfrecast.main.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ViewModelModule::class))
interface AppComponent {

    fun inject(application: ForecastApplication)

//    fun inject(ancd: ViewModelFactory)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}