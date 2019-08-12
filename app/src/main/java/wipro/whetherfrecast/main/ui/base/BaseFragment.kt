package wipro.whetherfrecast.main.ui.base


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_base.*
import wipro.whetherfrecast.main.R
import wipro.whetherfrecast.main.listeners.ToolbarListener
import wipro.whetherfrecast.main.ui.fragments.BaseNavigator

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<BaseNavigator>> : Fragment() {

    var mViewDataBinding: T? = null
    var mViewModel: V? = null

    var mLayoutId: Int = 0
    var mToolbarListener: ToolbarListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mToolbarListener = context as ToolbarListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context as Activity).localClassName + " must implement ToolbarListener ")
        }
    }

    protected fun setBaseEmptyView(view: AppCompatTextView) {
        view.visibility = View.VISIBLE
        view.setText(R.string.error_nodata)
    }

    protected fun setBaseEmptyView(view: TextView, message: String) {
        view.text = message
    }

    fun hideBaseProgressBar() {
        progressbarLoading?.visibility = View.GONE
    }

    fun showBaseErrorWithMessage(str: String) {
        progressbarLoading.visibility = View.GONE
        if (!str.isEmpty()) {
            if (progressbarLoading != null) {
                progressbarLoading.visibility = View.GONE
                textViewErrorTitle_base.text = str
            }
        }
    }

    fun showBaseProgressBar() {
       // progressbarLoading?.visibility = View.VISIBLE
    }


    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        inflater.inflate(mLayoutId, fragment_container)
        return view
    }


    protected fun setLayout(layoutId: Int) {
        mLayoutId = layoutId
    }

    override fun onDetach() {
        super.onDetach()
        mToolbarListener = null
    }

    abstract fun initialize()

    abstract fun setClickListener()

    /**
     * @return view model instance
     */
    abstract fun getViewModel(): V

    @LayoutRes
    abstract fun getLayoutId(): Int
}