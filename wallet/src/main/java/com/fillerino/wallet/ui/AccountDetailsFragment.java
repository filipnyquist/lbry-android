package com.fillerino.wallet.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fillerino.core.wallet.WalletAccount;
import com.fillerino.wallet.Constants;
import com.fillerino.wallet.WalletApplication;
import com.fillerino.wallet.util.QrUtils;
import com.fillerino.wallet.util.UiUtils;
import com.fillerino.core.Preconditions;

import static com.fillerino.core.Preconditions.checkNotNull;

/**
 * @author John L. Jegutanis
 */
public class AccountDetailsFragment extends Fragment {
    private String publicKeySerialized;

    public static AccountDetailsFragment newInstance(WalletAccount account) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_ACCOUNT_ID, account.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public AccountDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preconditions.checkNotNull(getArguments(), "Must provide arguments with an account id.");

        WalletApplication application = (WalletApplication) getActivity().getApplication();
        WalletAccount account =
                application.getAccount(getArguments().getString(Constants.ARG_ACCOUNT_ID));
        if (account == null) {
            Toast.makeText(getActivity(), com.fillerino.wallet.R.string.no_such_pocket_error, Toast.LENGTH_LONG).show();
            getActivity().finish();
            return;
        }

        publicKeySerialized = account.getPublicKeySerialized();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.fillerino.wallet.R.layout.fragment_account_details, container, false);

        TextView publicKey = (TextView) view.findViewById(com.fillerino.wallet.R.id.public_key);
        publicKey.setOnClickListener(getPubKeyOnClickListener());
        publicKey.setText(publicKeySerialized);

        ImageView qrView = (ImageView) view.findViewById(com.fillerino.wallet.R.id.qr_code_public_key);
        QrUtils.setQr(qrView, getResources(), publicKeySerialized);

        return view;
    }

    private View.OnClickListener getPubKeyOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                UiUtils.startCopyShareActionMode(publicKeySerialized, activity);
            }
        };
    }
}
