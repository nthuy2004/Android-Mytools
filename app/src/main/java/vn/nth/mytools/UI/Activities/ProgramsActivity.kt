package vn.nth.mytools.UI.Activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import vn.nth.mytools.App
import vn.nth.mytools.Data.Models.AppModel
import vn.nth.mytools.R
import vn.nth.mytools.UI.Adapters.ApplicationsAdapter
import vn.nth.mytools.UI.Base.BaseActivity
import vn.nth.mytools.UI.Base.BaseDialog
import vn.nth.mytools.UI.Base.BaseMaterial3DIalog
import vn.nth.mytools.UI.Dialogs.AppDetailDialog
import vn.nth.mytools.databinding.ActivityProgramsBinding

class ProgramsActivity : BaseActivity() {
    private lateinit var binding : ActivityProgramsBinding
    private val handler : Handler = Handler(Looper.getMainLooper())
    private var appData : ArrayList<AppModel> = ArrayList<AppModel>()
    private lateinit var baseDialog: BaseMaterial3DIalog
    private lateinit var appDetailDialog: AppDetailDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ToolbarHelper(this, binding.appbarLayout.toolbar).apply {
            title = "Applications list"
            enableBack = true
        }
        baseDialog = BaseMaterial3DIalog(this).apply {
            layout = R.layout.layout_loading
            cancelable = false
        }

        binding.listView.onItemLongClickListener = AdapterView.OnItemLongClickListener() { parent, view, pos, id ->
            val data = appData!!.get(pos)
            AppDetailDialog(this, data).apply {

            }.show()
            true
        }
        baseDialog.show()
        Thread {
            appData = App.getInstalledApps()
            val adapter = ApplicationsAdapter(this, appData, "")
            handler.post {
                binding.listView.adapter = adapter

            }
            baseDialog.dismiss()
        }.start()
        //binding.listView.setOnItemClickListener { p0, p1, p2, p3 -> App.toast("Hello") }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_programs_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}