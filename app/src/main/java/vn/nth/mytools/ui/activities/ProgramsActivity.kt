package vn.nth.mytools.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import vn.nth.mytools.App
import vn.nth.mytools.BuildConfig
import vn.nth.mytools.data.models.AppModel
import vn.nth.mytools.R
import vn.nth.mytools.Utils.Constant.AppSelectConstant
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
    private var keyword : String = ""
    private var showSystem : Boolean = false
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
                    keyword = charSequenceToString(text!!)
                    setList(keyword, if(showSystem) AppSelectConstant.ALL else AppSelectConstant.USER)
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
                setList(keyword, if(showSystem) AppSelectConstant.ALL else AppSelectConstant.USER)
            }
            baseDialog.dismiss()
        }.start()
        //binding.listView.setOnItemClickListener { p0, p1, p2, p3 -> App.toast("Hello") }


    }

    override fun onResume() {
        super.onResume()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_programs_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.show_system -> {
                item.isChecked = !item.isChecked
                showSystem = item.isChecked
                setList(keyword, if(showSystem) AppSelectConstant.ALL else AppSelectConstant.USER)
            }
        }
        return true
    }
    private fun setList(keyword : String = "", flags : Int = AppSelectConstant.USER) {
        val adapter = ApplicationsAdapter(this, appData, keyword, flags)
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, pos, l ->
            Log.d(TAG, "$pos")
            val cb = view.findViewById<CheckBox>(R.id.select_state)
            cb.isChecked = !cb.isChecked
            binding.multiappFab.visibility = if(adapter.getSelectedItems().size == 0) View.GONE else View.VISIBLE
        }
    }
}