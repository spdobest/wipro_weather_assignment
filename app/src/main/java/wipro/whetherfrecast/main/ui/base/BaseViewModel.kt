package wipro.whetherfrecast.main.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import wipro.whetherfrecast.main.network.ResponseCallBack
import java.lang.ref.WeakReference


open class BaseViewModel<N : BaseNavigator> : AndroidViewModel, ResponseCallBack<Any> {

    constructor(application: Application) : super(application)

    private var mBaseViewNavigator: WeakReference<N>? = null


    fun getViewNavigator(): N? {
        return mBaseViewNavigator!!.get()
    }

    fun setNavigator(navigator: N) {
        this.mBaseViewNavigator = WeakReference(navigator)
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun onSuccess(value: Any) {

    }

    override fun onError(error: String) {

    }

}