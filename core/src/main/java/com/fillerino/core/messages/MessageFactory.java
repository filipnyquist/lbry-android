package com.fillerino.core.messages;

import com.fillerino.core.wallet.AbstractTransaction;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 */
public interface MessageFactory {
    int maxMessageSizeBytes();

    boolean canHandlePublicMessages();

    boolean canHandlePrivateMessages();

    TxMessage createPublicMessage(String message);

    @Nullable
    TxMessage extractPublicMessage(AbstractTransaction transaction);
}
