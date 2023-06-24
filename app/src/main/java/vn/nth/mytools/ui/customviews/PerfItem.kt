package vn.nth.mytools.ui.customviews

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import vn.nth.mytools.R
import vn.nth.mytools.Utils.charSequenceToString
import vn.nth.mytools.databinding.LayoutPrefItemBinding

class PerfItem(context : Context, attrs : AttributeSet) : LinearLayout(context, attrs) {
    private var binding : LayoutPrefItemBinding
    init {
        binding = LayoutPrefItemBinding.inflate(LayoutInflater.from(context))
        val l : LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(binding.root, l)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.PrefItem)
        if(attrs != null) {
            var title = charSequenceToString(attr.getText(R.styleable.PrefItem_title))
            var desc = charSequenceToString(attr.getText(R.styleable.PrefItem_desc))
            val img = attr.getDrawable(R.styleable.PrefItem_headicon)
            if (TextUtils.isEmpty(title)) title = "Default value"
            binding.title.text = title
            binding.desc.text = desc
            binding.icon.setImageDrawable(img)
        }
        attr.recycle()
    }
}