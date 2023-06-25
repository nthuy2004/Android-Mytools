package vn.nth.mytools.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import vn.nth.mytools.ui.base.BaseMaterial3Dialog

open class ConfirmDialogListener {
    open fun onConfirmed(i : DialogInterface?) {
        i?.dismiss()
    }
    open fun onRejected(i : DialogInterface?) {
        i?.dismiss()
    }
}

class ConfirmDialog(context : Context) : BaseMaterial3Dialog(context) {
    public var event : ConfirmDialogListener = ConfirmDialogListener()
    init {
        title = "Confirm"
        dialogBuilderWrapper.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                event.onRejected(p0)

            }
        })
        dialogBuilderWrapper.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                event.onConfirmed(p0)
            }
        })
    }
}