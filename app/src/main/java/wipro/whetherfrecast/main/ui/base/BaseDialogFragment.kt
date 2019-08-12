package wipro.whetherfrecast.main.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialogfragment_base.*
import wipro.whetherfrecast.main.R


/**
 * Created by sibaprasad on 12/08/19.
 */

abstract class BaseDialogFragment : DialogFragment() {

    internal var rlErrorRoot: RelativeLayout? = null
    private val mProgressBar: ProgressBar? = null
    private var mLayoutId: Int = 0


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setWindowAnimations(
                R.style.styleDialogFragment
            )

            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun setToolbarTitle(title: String) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialogfragment_base, container, false)
        val container = view.findViewById<FrameLayout>(R.id.container_dialogfragment)
        inflater.inflate(mLayoutId, container)
        Log.i(TAG, "oncreateVIew Parent")
        return view
    }

    protected fun setLayout(layoutId: Int) {
        mLayoutId = layoutId
    }

    override fun onDestroy() {
        if (dialog != null && retainInstance) {
            dialog!!.setOnDismissListener(null)
        }
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated Parent")
        imageViewBack_basedialogfragment?.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        val TAG = "BaseDialogFragment"
    }
}