package vn.nth.mytools.UI.Dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import vn.nth.mytools.Data.Models.AppModel
import vn.nth.mytools.R
import vn.nth.mytools.UI.Base.BaseMaterial3DIalog
import vn.nth.mytools.databinding.LayoutAppDetailBinding

class AppDetailDialog(private var context : Context, private var appData : AppModel): BaseMaterial3DIalog(context) {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var binding : LayoutAppDetailBinding = LayoutAppDetailBinding.inflate(layoutInflater)
    init {
        dialogBuilderWrapper.setView(binding.root)
        if(appData != null) {
            binding.appIcon.setImageDrawable(appData.icon)
            binding.itemTitle.text = appData.appname
            binding.itemPkgname.text = appData.packageName
        }
    }
}