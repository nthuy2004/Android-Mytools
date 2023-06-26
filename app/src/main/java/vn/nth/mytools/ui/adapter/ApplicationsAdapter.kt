package vn.nth.mytools.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vn.nth.mytools.App
import vn.nth.mytools.BuildConfig
import vn.nth.mytools.Utils.Constant.AppSelectConstant
import vn.nth.mytools.data.models.AppModel
import vn.nth.mytools.databinding.LayoutAppItemBinding

class ApplicationsAdapter(private val context : Context, apps : ArrayList<AppModel>, private var keyword : String, private val flags : Int) : BaseAdapter() {
    private val TAG = "NTH_MYTOOLS_APP_ADAPTER"
    private var list : ArrayList<AppModel>
    @SuppressLint("UseSparseArrays")
    private var selectedItems : HashMap<Int, Boolean> = HashMap()
    private lateinit var holder : ViewHolder
    private lateinit var binding : LayoutAppItemBinding
    private var layoutInflater: LayoutInflater

    private var selectUserApp = (flags and AppSelectConstant.USER != 0)
    private var selectAllApp = (flags and AppSelectConstant.ALL != 0)

    init {
        list = sort(filterApps(apps, keyword))
        initSelectedItem(false)
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
                root = binding.root
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
            itemTitle!!.text = hightLightKeyword(item.appname, keyword)
            itemPkgname!!.text = hightLightKeyword(item.packageName, keyword)
            itemIcon.setImageDrawable(item.icon)
            checkState.setOnCheckedChangeListener { _, isChecked ->
                Log.d(TAG, "Item pos $p0 | selected state: $isChecked")
                // because of viewholder's feature, then set check state in activity, not adapter
                selectedItems[p0] = isChecked
            }
            checkState.isChecked = selectedItems[p0] == true
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
    private fun searchFromString(item: AppModel, text: String): Boolean {
        if(text.equals("")) return true
        return item.packageName.toLowerCase().contains(text)
                || item.appname.toLowerCase().contains(text)
    }

    private fun filterApps(items : ArrayList<AppModel>, keyword: String) : ArrayList<AppModel> {
        val text = keyword.toLowerCase()
        return ArrayList(items.filter {
            searchFromString(it, text) && filterType(it)
        })
    }
    private fun hightLightKeyword(string: String, substr : String) : Spanned? {
        val start : Int = string.toLowerCase().indexOf(substr)
        if(start == -1) return Html.fromHtml(string)
        val end : Int = start + substr.length
        if(end > string.length) if(start == -1) return Html.fromHtml(string)
        val toReplace : String = string.substring(start, end)
        val newStr = string.replace(toReplace, "<font color='red'>$toReplace</font>")
        return Html.fromHtml(newStr)
    }
    private fun filterType(item: AppModel) : Boolean {
        var res = false
        if(item.packageName.equals(BuildConfig.APPLICATION_ID)) res = false
        if(selectAllApp) res = true
        if(selectUserApp && !item.isSystem) res = true
        //Log.d(TAG, "PKG NAME: ${item.packageName} | ret val : $res")
        return res
    }
    private fun initSelectedItem(defaultValue : Boolean) {
        for(i in list.indices) {
            selectedItems[i] = defaultValue
        }
    }
    public fun getSelectedItems() : ArrayList<AppModel> {
        return selectedItems.keys.filter {
            selectedItems[it] == true
        }.mapTo(ArrayList()){getItem(it)}
    }
    private fun sort(list : ArrayList<AppModel>) : ArrayList<AppModel> {
        list.sortWith(Comparator { l, r ->
            val les = l.appname
            val res = r.appname
            when {
                les < res -> -1
                les > res -> 1
                else -> {
                    val lp = l.packageName
                    val rp = r.packageName
                    when {
                        lp < rp -> -1
                        lp > rp -> 1
                        else -> 0
                    }
                }
            }
        })
        return list
    }
    inner class ViewHolder {
        internal lateinit var root : LinearLayout
        internal lateinit var checkState : CheckBox
        internal lateinit var itemTitle : TextView
        internal lateinit var itemPkgname : TextView
        internal lateinit var itemStatus : TextView
        internal lateinit var itemIcon : ImageView
    }
}