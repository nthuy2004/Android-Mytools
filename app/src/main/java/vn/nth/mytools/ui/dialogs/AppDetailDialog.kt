package vn.nth.mytools.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.text.Html
import android.view.LayoutInflater
import vn.nth.mytools.App
import vn.nth.mytools.Utils.runApp
import vn.nth.mytools.Utils.runAppInfo
import vn.nth.mytools.Utils.runMarket
import vn.nth.mytools.data.models.AppModel
import vn.nth.mytools.ui.base.BaseMaterial3Dialog
import vn.nth.mytools.databinding.LayoutAppDetailBinding
import vn.nth.mytools.ui.base.BaseDialog

class AppDetailDialog(private var context : Context, private var appData : AppModel): BaseMaterial3Dialog(context) {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var binding : LayoutAppDetailBinding = LayoutAppDetailBinding.inflate(layoutInflater)
    init {
        dialogBuilderWrapper.setView(binding.root)
        if(appData != null) {
            binding.setData(appData)
            if(!appData.canRun) {
                binding.btnRunApp.isEnabled = false
            }
        }
        initEvent()

    }

    private fun initEvent() {
        binding.btnRunApp.setOnClickListener {
            context.runApp(appData.packageName)
        }
        binding.btnAppInfo.setOnClickListener {
            context.runAppInfo(appData.packageName)
        }
        binding.btnGotoMarket.setOnClickListener {
            context.runMarket(appData.packageName)
        }
        binding.btnUninstallMenu.setOnClickListener {
            ConfirmDialog(context).apply {
                fun ahihi(appData: AppModel, result : (type : String, method : String) -> Unit) {
                    when(appData.appType) {
                        AppModel.AppType.SYSTEM -> result("system", "shell")
                        AppModel.AppType.USER -> result("user", "normal")
                        else -> result("unknown", "unknown")
                    }
                }
                ahihi(appData) { type, method ->
                    message = Html.fromHtml("Do you want to uninstall <font color='red'>${appData.appname}</font> ? This is ${type} application, then i'll use ${method} method!")
                }
                event = object : ConfirmDialogListener() {
                    override fun onConfirmed(i: DialogInterface?) {
                        App.toast("Confirmed")
                    }
                }
            }.show()
        }
    }
}