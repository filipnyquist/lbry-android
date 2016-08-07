package com.fillerino.wallet.ui;

import android.content.DialogInterface;
import android.os.Bundle;

/**
 * @author John L. Jegutanis
 */
public class ShowSeedActivity extends BaseWalletActivity implements ShowSeedFragment.Listener {

    private static final String SHOW_SEED_TAG = "show_seed_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fillerino.wallet.R.layout.activity_fragment_wrapper);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(com.fillerino.wallet.R.id.container, new ShowSeedFragment(), SHOW_SEED_TAG)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onSeedNotAvailable() {
        DialogBuilder.warn(this, com.fillerino.wallet.R.string.seed_not_available_title)
                .setMessage(com.fillerino.wallet.R.string.seed_not_available)
                .setPositiveButton(com.fillerino.wallet.R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create().show();
    }

    @Override
    public void onPassword(CharSequence password) {
        ShowSeedFragment f = (ShowSeedFragment) getFM().findFragmentByTag(SHOW_SEED_TAG);
        if (f != null) {
            f.setPassword(password);
        }
    }
}
