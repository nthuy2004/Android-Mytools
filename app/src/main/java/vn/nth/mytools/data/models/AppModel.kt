package vn.nth.mytools.data.models

import android.graphics.drawable.Drawable
import java.lang.StringBuilder

public class AppModel : BaseModel() {
    public var appname : String = ""
    public var packageName : String = ""
    public var className : String? = null
    public var dir : String = ""
    public var path : String = ""
    public var version : String = ""
    public var versionCode : Int = 0
    public var targetSdkVersion = 0
    public var minSdkVersion = 0
    public var canRun : Boolean = true
    public var enabled : Boolean = false
    public var suspended : Boolean = false
    public var updated : Boolean = false
    public var isSystem : Boolean = false
    public var appType : AppType = AppType.UNKNOWN
    public var icon : Drawable? = null
    public enum class AppType : java.io.Serializable {
        UNKNOWN, USER, SYSTEM
    }
    public fun isNormal() : Boolean{
        return !suspended && !updated
    }
    public fun getStatus() : String {
        val a : java.lang.StringBuilder = StringBuilder()
        if(suspended || updated || isSystem) {
            if(suspended) a.append( "❄ Suspended " )
            if(updated) a.append("\uD83D\uDC4C Updated ")
            if(isSystem) a.append("\uD83D\uDCF1 System ")
            return a.toString()
        }
        else return "Normal"
    }
    public override fun toString() : String {
        val a = StringBuilder()
        a.run {
            append("App name: $appname\n")
            append("Package name: $packageName\n")
            append("Class name: $className\n")
            append("Dir: $dir\n")
            append("Path: $path\n")
            append("Version: $version\n")
            append("Version code: $versionCode\n")
            append("Target SDK version: $targetSdkVersion\n")
            append("Min SDK Version: $minSdkVersion\n")
            append("Status: ${getStatus()}\n")
        }
        return a.toString()
    }
}