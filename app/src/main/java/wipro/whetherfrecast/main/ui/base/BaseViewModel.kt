package wipro.whetherfrecast.main.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import wipro.whetherfrecast.main.network.ResponseCallBack


open class BaseViewModel(application: Application) : AndroidViewModel(application), ResponseCallBack<Any> {

    override fun onSuccess(value: Any) {

    }

    override fun onError(error: String) {

    }

}