package com.fillerino.core.coins.families;

import com.fillerino.core.messages.MessageFactory;
import com.fillerino.core.wallet.families.clams.ClamsTxMessage;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 *
 * This family contains Clams
 */
public class ClamsFamily extends BitFamily {
    {
        family = Families.CLAMS;
    }

    @Override
    @Nullable
    public MessageFactory getMessagesFactory() {
        return ClamsTxMessage.getFactory();
    }
}
