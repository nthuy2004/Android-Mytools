package vn.nth.mytools.Utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
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
fun Context.runApp(packageName: String) {
    var intent = packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        // We found the activity now start the activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } else {
        // Bring user to the market or let them choose an app?
        runMarket(packageName)
    }
}
fun Context.runAppInfo(packageName: String) {
    try {
        //Open the specific App Info page:
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        //e.printStackTrace();
        //Open the generic Apps page:
        val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
        startActivity(intent)
    }
}
fun Context.runMarket(packageName: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
}