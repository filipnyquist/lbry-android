package com.fillerino.core.coins.families;

import com.fillerino.core.coins.CoinType;
import com.fillerino.core.exceptions.AddressMalformedException;
import com.fillerino.core.messages.MessageFactory;
import com.fillerino.core.wallet.AbstractAddress;
import com.fillerino.core.wallet.families.nxt.NxtAddress;
import com.fillerino.core.wallet.families.nxt.NxtTxMessage;

import javax.annotation.Nullable;


/**
 * @author John L. Jegutanis
 *
 * Coins that belong to this family are: NXT, Burst, etc
 */
public abstract class NxtFamily extends CoinType {
    public static final short DEFAULT_DEADLINE = 1440;

    {
        family = Families.NXT;
    }

    @Override
    public AbstractAddress newAddress(String addressStr) throws AddressMalformedException {
        return NxtAddress.fromString(this, addressStr);
    }

    @Override
    public boolean canHandleMessages() {
        return true;
    }

    @Override
    @Nullable
    public MessageFactory getMessagesFactory() {
        return NxtTxMessage.getFactory();
    }
}