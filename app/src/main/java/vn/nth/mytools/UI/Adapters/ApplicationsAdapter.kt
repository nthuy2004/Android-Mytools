package vn.nth.mytools.UI.Adapters

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import vn.nth.mytools.Data.Models.AppModel
import vn.nth.mytools.R
import vn.nth.mytools.databinding.LayoutAppItemBinding

class ApplicationsAdapter(private val context : Context, apps : ArrayList<AppModel>, keyword : String) : BaseAdapter() {
    private var list : ArrayList<AppModel>
    private lateinit var holder : ViewHolder
    private lateinit var binding : LayoutAppItemBinding
    private var layoutInflater: LayoutInflater
    init {
        list = sort(apps)
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): AppModel {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup): View {
        binding = LayoutAppItemBinding.inflate(layoutInflater)
        var convertView = p1
        if(convertView == null) {
            holder = ViewHolder()
            convertView = binding.root
            holder.run {
                checkState = binding.selectState
                itemTitle = binding.itemTitle
                itemPkgname = binding.itemPkgname
                itemStatus = binding.itemStatus
                itemIcon = binding.itemIcon
                itemIcon!!.tag = getItem(p0).packageName
            }
            convertView!!.tag = holder
        }else {
            holder = convertView.tag as ViewHolder
        }
        holder.run {
            val item = getItem(p0)
            itemTitle!!.text = item.appname
            itemPkgname!!.text = item.packageName
            itemIcon.setImageDrawable(item.icon)
            itemStatus.run {
                text = item.getStatus()
                visibility = when(item.getStatus()) {
                    "Normal" -> View.GONE
                    else -> View.VISIBLE
                }
            }
        }
        return convertView
    }
    private fun sort(list : ArrayList<AppModel>) : ArrayList<AppModel> {
        list.sortWith(compareBy({it.appname}))
        return list
    }
    inner class ViewHolder {
        internal lateinit var checkState : CheckBox
        internal lateinit var itemTitle : TextView
        internal lateinit var itemPkgname : TextView
        internal lateinit var itemStatus : TextView
        internal lateinit var itemIcon : ImageView
    }
}