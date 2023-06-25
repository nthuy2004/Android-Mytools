package vn.nth.mytools.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import vn.nth.mytools.App
import vn.nth.mytools.BuildConfig
import vn.nth.mytools.data.models.AppModel
import vn.nth.mytools.R
import vn.nth.mytools.Utils.charSequenceToString
import vn.nth.mytools.ui.adapter.ApplicationsAdapter
import vn.nth.mytools.ui.base.BaseActivity
import vn.nth.mytools.ui.base.BaseMaterial3Dialog
import vn.nth.mytools.ui.dialogs.AppDetailDialog
import vn.nth.mytools.databinding.ActivityProgramsBinding

class ProgramsActivity : BaseActivity() {
    private val TAG : String = "NTH_MYTOOLS_PROGRAMSACTIVITY"
    private lateinit var binding : ActivityProgramsBinding
    private var appData : ArrayList<AppModel> = ArrayList<AppModel>()
    private lateinit var baseDialog: BaseMaterial3Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ToolbarHelper(this, binding.appbarLayout.toolbar).apply {
            title = "Applications list"
            enableBack = true
        }
        baseDialog = BaseMaterial3Dialog(this).apply {
            layout = R.layout.layout_loading
            cancelable = false
        }
        var lastInput = 0L // tks to stackoverflow
        binding.searchApp.doOnTextChanged { text, start, before, count ->
            val current = System.currentTimeMillis()
            lastInput = current
            handler.postDelayed({
                if(lastInput == current) {
                    setList(charSequenceToString(text!!))
                }

            }, 500)
        }
        binding.listView.onItemLongClickListener = AdapterView.OnItemLongClickListener() { parent, view, pos, id ->
            Log.d(TAG, "Pos: $pos | Id: $id")
            val data = parent.adapter.getItem(pos) as AppModel
            AppDetailDialog(this, data).show()
            true
        }
        baseDialog.show()
        Thread {
            appData = App.getInstalledApps()
            handler.post {
                setList()
            }
            baseDialog.dismiss()
        }.start()
        //binding.listView.setOnItemClickListener { p0, p1, p2, p3 -> App.toast("Hello") }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_programs_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun setList(keyword : String = "") {
        val adapter = ApplicationsAdapter(this, appData, keyword)
        binding.listView.adapter = adapter
    }
}