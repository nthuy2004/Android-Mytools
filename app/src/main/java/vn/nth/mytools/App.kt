package vn.nth.mytools

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class App : Application() {
    companion object {
        private var handler = Handler(Looper.getMainLooper())
        public lateinit var context : Application
        public fun toast(msg : String) {
            handler.post {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
        public fun post(runnable: Runnable) {
            handler.post(runnable)
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        context = this
    }
}