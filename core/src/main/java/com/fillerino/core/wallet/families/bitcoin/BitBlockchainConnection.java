package com.fillerino.core.wallet.families.bitcoin;

import com.fillerino.core.network.AddressStatus;
import com.fillerino.core.network.interfaces.BlockchainConnection;

/**
 * @author John L. Jegutanis
 */
public interface BitBlockchainConnection extends BlockchainConnection<BitTransaction> {
    void getUnspentTx(AddressStatus status, BitTransactionEventListener listener);
}
