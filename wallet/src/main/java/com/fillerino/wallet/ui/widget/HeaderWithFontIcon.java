package com.fillerino.wallet.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fillerino.wallet.util.Fonts;

/**
 * @author John L. Jegutanis
 */
public class HeaderWithFontIcon extends LinearLayout {
    private final TextView fontIconView;
    private final TextView messageView;

    public HeaderWithFontIcon(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(com.fillerino.wallet.R.layout.header_with_font_icon, this, true);

        messageView = (TextView) findViewById(com.fillerino.wallet.R.id.message);
        fontIconView = (TextView) findViewById(com.fillerino.wallet.R.id.font_icon);
        Fonts.setTypeface(fontIconView, Fonts.Font.COINOMI_FONT_ICONS);
    }

    public void setFontIcon(int resid) {
        fontIconView.setText(resid);
    }

    public void setMessage(int resid) {
        messageView.setText(resid);
    }
}
