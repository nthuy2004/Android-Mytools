package vn.nth.mytools.ui.activities

import android.os.Bundle
import vn.nth.mytools.ui.base.BaseActivity
import vn.nth.mytools.databinding.ActivityAppDetailBinding

class AppDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityAppDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ToolbarHelper(this, binding.appbarLayout.toolbar).apply {
            title = "Appinfo"
            enableBack = true
        }
    }
}