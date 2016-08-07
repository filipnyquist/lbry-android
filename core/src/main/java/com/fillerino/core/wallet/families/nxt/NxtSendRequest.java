package com.fillerino.core.wallet.families.nxt;

import com.fillerino.core.coins.BurstMain;
import com.fillerino.core.coins.CoinType;
import com.fillerino.core.coins.FeePolicy;
import com.fillerino.core.coins.NxtMain;
import com.fillerino.core.coins.Value;
import com.fillerino.core.coins.families.NxtFamily;
import com.fillerino.core.coins.nxt.Appendix;
import com.fillerino.core.coins.nxt.Attachment;
import com.fillerino.core.coins.nxt.Convert;
import com.fillerino.core.coins.nxt.TransactionImpl;
import com.fillerino.core.util.TypeUtils;
import com.fillerino.core.wallet.SendRequest;
import com.fillerino.core.Preconditions;

import static com.fillerino.core.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author John L. Jegutanis
 */
public class NxtSendRequest extends SendRequest<NxtTransaction> {
    public TransactionImpl.BuilderImpl nxtTxBuilder;

    protected NxtSendRequest(CoinType type) {
        super(type);
    }

    public static NxtSendRequest to(NxtFamilyWallet from, NxtAddress destination, Value amount) {
        checkNotNull(destination.getType(), "Address is for an unknown network");
        Preconditions.checkState(from.getCoinType() == destination.getType(), "Incompatible destination address coin type");
        Preconditions.checkState(TypeUtils.is(destination.getType(), amount.type), "Incompatible sending amount type");
        checkTypeCompatibility(destination.getType());

        NxtSendRequest req = new NxtSendRequest(destination.getType());

        byte version = (byte) 1;
        int timestamp;
        if (req.type instanceof NxtMain) {
            timestamp = Convert.toNxtEpochTime(System.currentTimeMillis());
        } else if (req.type instanceof BurstMain) {
            timestamp = Convert.toBurstEpochTime(System.currentTimeMillis());
        } else {
            throw new RuntimeException("Unexpected NXT family type: " + req.type.toString());
        }

        TransactionImpl.BuilderImpl builder = new TransactionImpl.BuilderImpl(version,
                from.getPublicKey(), amount.value, req.fee.value, timestamp,
                NxtFamily.DEFAULT_DEADLINE, Attachment.ORDINARY_PAYMENT);

        builder.recipientId(destination.getAccountId());

        // TODO extra check, query the server if the public key announcement is actually needed
        if (destination.getPublicKey() != null) {
            Appendix.PublicKeyAnnouncement publicKeyAnnouncement
                    = new Appendix.PublicKeyAnnouncement(destination.getPublicKey());
            builder.publicKeyAnnouncement(publicKeyAnnouncement);
        }

        req.nxtTxBuilder = builder;

        return req;
    }

    public static NxtSendRequest emptyWallet(NxtFamilyWallet from, NxtAddress destination) {
        checkNotNull(destination.getType(), "Address is for an unknown network");
        Preconditions.checkState(destination.getType().getFeePolicy() == FeePolicy.FLAT_FEE, "Only flat fee is supported");

        Value allFundsMinusFee = from.getBalance().subtract(destination.getType().getFeeValue());

        return to(from, destination, allFundsMinusFee);
    }

    private static void checkTypeCompatibility(CoinType type) {
        // Only Nxt family coins are supported
        if (!(type instanceof NxtFamily)) {
            throw new RuntimeException("Unsupported type: " + type);
        }
    }
}
