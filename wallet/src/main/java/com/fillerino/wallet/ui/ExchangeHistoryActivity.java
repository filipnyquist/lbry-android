package com.fillerino.wallet.ui;

import android.os.Bundle;

/**
 * @author John L. Jegutanis
 */
public class ExchangeHistoryActivity extends BaseWalletActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fillerino.wallet.R.layout.activity_fragment_wrapper);

        if (savedInstanceState == null) {
            ExchangeHistoryFragment fragment = new ExchangeHistoryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(com.fillerino.wallet.R.id.container, fragment)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}
