package com.fillerino.wallet.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fillerino.core.coins.Value;
import com.fillerino.wallet.Configuration;
import com.fillerino.wallet.WalletApplication;
import com.fillerino.wallet.ui.adaptors.FeesListAdapter;
import com.fillerino.wallet.ui.dialogs.EditFeeDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Fragment that restores a wallet
 */
public class FeesSettingsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String EDIT_FEE_DIALOG = "edit_fee_dialog";

    @Bind(com.fillerino.wallet.R.id.coins_list) ListView coinList;

    private Configuration config;
    private Context context;
    private FeesListAdapter adapter;

    public FeesSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FeesListAdapter(context, config);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.fillerino.wallet.R.layout.fragment_fees_settings_list, container, false);
        ButterKnife.bind(this, view);
        coinList.setAdapter(adapter);

        return view;
    }

    @OnItemClick(com.fillerino.wallet.R.id.coins_list)
    void editFee(int currentSelection) {
        Value fee = (Value) coinList.getItemAtPosition(currentSelection);
        // Create the fragment and show it as a dialog.
        DialogFragment editFeeDialog = EditFeeDialog.newInstance(fee.type);
        editFeeDialog.show(getFragmentManager(), EDIT_FEE_DIALOG);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.context = context;
        WalletApplication application = (WalletApplication) context.getApplicationContext();
        config = application.getConfiguration();
        config.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        config.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Configuration.PREFS_KEY_FEES.equals(key)) {
            adapter.update();
        }
    }
}