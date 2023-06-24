package vn.nth.mytools.Utils

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.File

fun charSequenceToString(ch : CharSequence) : String {
    return "" + ch
}
fun Context.getAppIcon(app : ApplicationInfo) : Drawable? {
    var icon = getAppIcon(app.packageName)
    if(icon == null && app.sourceDir.isNotEmpty()) {
        try {
            val file = File(app.sourceDir.toString())
            if (file.exists() && file.canRead()) {
                icon = packageManager.getPackageArchiveInfo(file.absolutePath, PackageManager.GET_ACTIVITIES)?.applicationInfo?.loadIcon(packageManager)
            }
        } catch (ex: Exception) {
        }
    }
    return icon
}
fun Context.getAppIcon(packageName: String): Drawable? {
    val installInfo = packageManager.getPackageInfo(packageName, 0)
    return installInfo.applicationInfo.loadIcon(packageManager)
}
fun Context.getListActivities(packageName: String) : ArrayList<ActivityInfo> {
    val pInfo : PackageInfo = packageManager.getPackageInfo(packageName, 0)
    val a : ArrayList<ActivityInfo> = pInfo.activities as ArrayList<ActivityInfo>
    return a
}
