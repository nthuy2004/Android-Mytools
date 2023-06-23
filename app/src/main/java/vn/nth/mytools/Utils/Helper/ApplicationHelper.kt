package vn.nth.mytools.Utils.Helper

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import vn.nth.mytools.App
import vn.nth.mytools.Data.Models.AppModel
import vn.nth.mytools.Utils.charSequenceToString
import vn.nth.mytools.Utils.getAppIcon
import java.io.File

class ApplicationHelper(ctx : Context) {
    private val TAG = "NTH_MYTOOLS_APPLICATION_HELPER"
    private val whitelistApps = arrayOf("com.android.theme.", ".overlay")
    val context : Context = ctx
    var packageManager : PackageManager
    init {
        packageManager = context.packageManager
    }
    public fun getApps(hasSystemApp : Boolean) : ArrayList<AppModel> {
        val packageInfo = packageManager.getInstalledApplications(0)
        val list = ArrayList<AppModel>()
        for(i in packageInfo) {
            val appInfo = getApplicationInfo(i, hasSystemApp)
            if(appInfo != null) list.add(appInfo)
        }
        Log.d(TAG, "Total apps: " + packageInfo.size)
        return list
    }
    private fun isAppUpdated(applicationInfo: ApplicationInfo) : Boolean {
        return false
    }
    private fun getApplicationInfo(applicationInfo: ApplicationInfo, hasSystemApp: Boolean): AppModel? {

        val appPath = applicationInfo.sourceDir
        if (appPath == null || isWhitelist(applicationInfo.packageName)) return null
        if(!hasSystemApp && isSystemApp(applicationInfo)) return null
        val file = File(applicationInfo.publicSourceDir)
        if (!file.exists()) return null
        val item = AppModel()

        item.appname = charSequenceToString(applicationInfo.loadLabel(packageManager))
        item.packageName = applicationInfo.packageName
        item.dir = file.parent
        item.path = appPath
        try {
            val packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 0)
            item.version = packageInfo.versionName
            item.versionCode = packageInfo.versionCode
        }catch(ex : Exception) {
            App.toast(ex.message!!)
        }
        item.targetSdkVersion = applicationInfo.targetSdkVersion
        item.minSdkVersion = applicationInfo.minSdkVersion
        item.enabled = applicationInfo.enabled
        item.updated = isAppUpdated(applicationInfo)
        item.isSystem = isSystemApp(applicationInfo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            item.suspended = (applicationInfo.flags and ApplicationInfo.FLAG_SUSPENDED) != 0
        }
        item.appType = when(isSystemApp(applicationInfo)) {
            true -> AppModel.AppType.SYSTEM
            false -> AppModel.AppType.USER
        }
        item.icon = context.getAppIcon(applicationInfo)
        return item
    }

    private fun isWhitelist(str : String) : Boolean {
        for(i in whitelistApps) {
            if(str.contains(i)) return true
        }
        return false
    }
    private fun isSystemApp(applicationInfo : ApplicationInfo) : Boolean {
        val appPath = applicationInfo.sourceDir
        if(!appPath.startsWith("/data")) return true
        if((applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0) return true
        return false
    }
}