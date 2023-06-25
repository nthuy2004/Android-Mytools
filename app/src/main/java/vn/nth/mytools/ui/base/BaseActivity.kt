package vn.nth.mytools.ui.base

import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    val handler : Handler = Handler(Looper.getMainLooper())
    protected fun setBackOnAppbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

    }
    protected fun setTitle(str : String) {
        supportActionBar!!.title = str
    }
    public class ToolbarHelper(act: BaseActivity, toolbar: Toolbar) {
        public val activity : BaseActivity = act
        public val tb : Toolbar = toolbar
        init {
            activity.setSupportActionBar(toolbar)
        }
        public var text : String = ""
            get() = field
            set(value) {
                activity.supportActionBar!!.title = value
            }
        public var enableBack : Boolean = false
            get() = field
            set(value) {
                if(value) {
                    activity.supportActionBar!!.setHomeButtonEnabled(true)
                    activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                    tb.setNavigationOnClickListener {
                        activity.onBackPressed()
                    }
                }
            }
    }

}