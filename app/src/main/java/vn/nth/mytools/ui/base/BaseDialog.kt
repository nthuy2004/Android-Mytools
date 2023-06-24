package vn.nth.mytools.ui.base

import android.app.AlertDialog
import android.content.Context

open class BaseDialog(private var context : Context) {
    public var layout : Int = 0
    set(value) {
        if(value != 0) {
            dialogBuilderWrapper.setView(value)
        }
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
    public var dialogBuilderWrapper : AlertDialog.Builder = AlertDialog.Builder(context)
    public var dialogWrapper : AlertDialog = dialogBuilderWrapper.create()
    public fun create() : AlertDialog{
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