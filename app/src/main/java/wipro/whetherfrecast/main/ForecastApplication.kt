package wipro.whetherfrecast.main

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import wipro.whetherfrecast.main.di.component.DaggerAppComponent
import javax.inject.Inject

class ForecastApplication : Application() {



    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

    }
}