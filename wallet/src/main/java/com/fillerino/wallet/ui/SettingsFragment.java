package com.fillerino.wallet.ui;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

/**
 * @author John L. Jegutanis
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(com.fillerino.wallet.R.xml.preferences);
    }
}
