package vn.nth.mytools.ui.base

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class BaseMaterial3Dialog(private val context : Context) {
    public var layout : Int = 0
        set(value) {
            if(value != 0) {
                dialogBuilderWrapper.setView(value)
            }
        }
    public var view : View? = null
        set(value) {
            dialogBuilderWrapper.setView(view)
        }
    public var title : String = ""
        set(value) {
            dialogBuilderWrapper.setTitle(value)
        }
    public var message : String = ""
        set(value) {
            dialogBuilderWrapper.setMessage(value)
        }
    public var cancelable : Boolean = false
        set(value) {
            dialogBuilderWrapper.setCancelable(value)
        }
    public var dialogBuilderWrapper : MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
    public var dialogWrapper : AlertDialog = dialogBuilderWrapper.create()
    public fun create() : AlertDialog {
        dialogWrapper = dialogBuilderWrapper.create()
        return dialogWrapper
    }
    public fun show() {
        dialogWrapper = dialogBuilderWrapper.create()
        dialogWrapper.show()
    }
    public fun hide() {
        dialogWrapper.hide()
    }
    public fun dismiss() {
        dialogWrapper.dismiss()
    }
}