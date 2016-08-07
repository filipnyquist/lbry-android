package com.fillerino.wallet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fillerino.core.coins.CoinType;
import com.fillerino.core.coins.Value;
import com.fillerino.core.coins.families.NxtFamily;
import com.fillerino.core.util.GenericUtils;
import com.fillerino.core.wallet.AbstractTransaction;
import com.fillerino.core.wallet.AbstractWallet;
import com.fillerino.wallet.ui.widget.SendOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author John L. Jegutanis
 */
public class TransactionAmountVisualizerAdapter extends BaseAdapter {
    private final Context context;
    private final LayoutInflater inflater;

    private final AbstractWallet pocket;
    private boolean isSending;
    private CoinType type;
    private String symbol;
    private List<AbstractTransaction.AbstractOutput> outputs;
    private boolean hasFee;
    private Value feeAmount;

    private int itemCount;

    public TransactionAmountVisualizerAdapter(final Context context, final AbstractWallet walletPocket) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        pocket = walletPocket;
        type = pocket.getCoinType();
        symbol = type.getSymbol();
        outputs = new ArrayList<>();
    }

    public void setTransaction(AbstractTransaction tx) {
        outputs.clear();
        final Value value = tx.getValue(pocket);
        isSending = value.signum() < 0;
        // if sending and all the outputs point inside the current pocket it is an internal transfer
        boolean isInternalTransfer = isSending;

        for (AbstractTransaction.AbstractOutput output : tx.getSentTo()) {
            if (isSending) {
                // When sending hide change outputs
                if (pocket.isAddressMine(output.getAddress())) continue;
                isInternalTransfer = false;
            } else {
                if (pocket.getCoinType() instanceof NxtFamily) {
                    // TODO review the following
                    outputs.add(new AbstractTransaction.AbstractOutput(tx.getReceivedFrom().get(0), tx.getValue(pocket)));
                    break;
                }
                // When receiving hide outputs that are not ours
                if (!pocket.isAddressMine(output.getAddress())) continue;
            }
            outputs.add(output);
        }

        feeAmount = tx.getFee();
        hasFee = feeAmount != null && !feeAmount.isZero();

        itemCount = isInternalTransfer ? 1 : outputs.size();
        itemCount += hasFee ? 1 : 0;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemCount;
    }

    @Override
    public AbstractTransaction.AbstractOutput getItem(int position) {
        if (position < outputs.size()) {
            return outputs.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        if (row == null) {
            row = inflater.inflate(com.fillerino.wallet.R.layout.transaction_details_output_row, null);

            ((SendOutput) row).setSendLabel(context.getString(com.fillerino.wallet.R.string.sent));
            ((SendOutput) row).setReceiveLabel(context.getString(com.fillerino.wallet.R.string.received));
        }

        final SendOutput output = (SendOutput) row;
        final AbstractTransaction.AbstractOutput txo = getItem(position);

        if (txo == null) {
            if (position == 0) {
                output.setLabel(context.getString(com.fillerino.wallet.R.string.internal_transfer));
                output.setSending(isSending);
                output.setAmount(null);
                output.setSymbol(null);
            } else if (hasFee) {
                output.setAmount(GenericUtils.formatCoinValue(type, feeAmount));
                output.setSymbol(symbol);
                output.setIsFee(true);
            } else { // Should not happen
                output.setLabel("???");
                output.setAmount(null);
                output.setSymbol(null);
            }
        } else {
            Value outputAmount = txo.getValue();
            output.setAmount(GenericUtils.formatCoinValue(type, outputAmount));
            output.setSymbol(symbol);
            output.setLabelAndAddress(txo.getAddress());
            output.setSending(isSending);
        }

        return row;
    }
}
