package vn.nth.mytools

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import vn.nth.mytools.data.models.AppModel
import vn.nth.mytools.Utils.Helper.ApplicationHelper

class App : Application() {
    companion object {
        private var handler = Handler(Looper.getMainLooper())
        public lateinit var context : Application
        public fun toast(msg : String) {
            handler.post {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
        public fun getInstalledApps(): ArrayList<AppModel> {
            return ApplicationHelper(context).getApps(true)
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