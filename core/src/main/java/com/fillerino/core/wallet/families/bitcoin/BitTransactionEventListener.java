package com.fillerino.core.wallet.families.bitcoin;

import com.fillerino.core.network.AddressStatus;
import com.fillerino.core.network.interfaces.TransactionEventListener;
import com.fillerino.core.network.ServerClient;

import java.util.List;

/**
 * @author John L. Jegutanis
 */
public interface BitTransactionEventListener extends TransactionEventListener<BitTransaction> {
    void onUnspentTransactionUpdate(AddressStatus status, List<ServerClient.UnspentTx> UnspentTxes);
}
