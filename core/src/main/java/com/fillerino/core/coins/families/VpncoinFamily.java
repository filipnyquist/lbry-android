package com.fillerino.core.coins.families;

import com.fillerino.core.messages.MessageFactory;
import com.fillerino.core.wallet.families.vpncoin.VpncoinTxMessage;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 *
 * This family contains Vpncoin
 */
public abstract class VpncoinFamily extends BitFamily {
    {
        family = Families.VPNCOIN;
    }

    @Override
    @Nullable
    public MessageFactory getMessagesFactory() {
        return VpncoinTxMessage.getFactory();
    }
}