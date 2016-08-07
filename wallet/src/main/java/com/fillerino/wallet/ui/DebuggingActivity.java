package com.fillerino.wallet.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author John L. Jegutanis
 */
public class DebuggingActivity extends BaseWalletActivity implements UnlockWalletDialog.Listener {

    private static final String DEBUGGING_TAG = "debugging_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fillerino.wallet.R.layout.activity_fragment_wrapper);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(com.fillerino.wallet.R.id.container, new DebuggingFragment(), DEBUGGING_TAG)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onPassword(CharSequence password) {
        Fragment f = getFM().findFragmentByTag(DEBUGGING_TAG);
        if (f != null && f instanceof DebuggingFragment) {
            ((DebuggingFragment) f).setPassword(password);
        }
    }
}
