package vn.nth.mytools.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import vn.nth.mytools.R;

public class DrawableButton extends LinearLayout {
    public DrawableButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_drawable_button, this, true);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.DrawableButton);
        String text = "" + attr.getText(R.styleable.DrawableButton_text);
        Drawable img = attr.getDrawable(R.styleable.DrawableButton_drawable);
        if(TextUtils.isEmpty(text)) text = "Default value";
        ((TextView)this.findViewById(R.id.title)).setText(text);
        ((ImageView)this.findViewById(R.id.icon)).setImageDrawable(img);
        attr.recycle();
    }
}
