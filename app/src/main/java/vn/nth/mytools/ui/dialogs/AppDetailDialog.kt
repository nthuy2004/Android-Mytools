package vn.nth.mytools.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import vn.nth.mytools.data.models.AppModel
import vn.nth.mytools.ui.base.BaseMaterial3DIalog
import vn.nth.mytools.databinding.LayoutAppDetailBinding

class AppDetailDialog(private var context : Context, private var appData : AppModel): BaseMaterial3DIalog(context) {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var binding : LayoutAppDetailBinding = LayoutAppDetailBinding.inflate(layoutInflater)
    init {
        dialogBuilderWrapper.setView(binding.root)
        if(appData != null) {
            binding.setData(appData)
        }
    }
}