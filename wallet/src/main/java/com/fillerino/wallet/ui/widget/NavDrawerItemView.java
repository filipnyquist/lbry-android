package com.fillerino.wallet.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author John L. Jegutanis
 */
public class NavDrawerItemView extends LinearLayout implements Checkable {
    private final TextView title;
    private final ImageView icon;
    private final View view;

    private boolean isChecked = false;

    public NavDrawerItemView(Context context) {
        super(context);

        view = LayoutInflater.from(context).inflate(com.fillerino.wallet.R.layout.nav_drawer_item, this, true);
        title = (TextView) findViewById(com.fillerino.wallet.R.id.item_text);
        icon = (ImageView) findViewById(com.fillerino.wallet.R.id.item_icon);
    }

    public void setData(String titleStr, int iconRes) {
        title.setText(titleStr);
        icon.setImageResource(iconRes);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;

        if (isChecked) {
            view.setBackgroundResource(com.fillerino.wallet.R.color.primary_100);
        } else {
            view.setBackgroundResource(0);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
