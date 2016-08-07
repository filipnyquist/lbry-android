package com.fillerino.core.wallet;

import com.fillerino.core.Preconditions;

import javax.annotation.Nullable;

import static com.fillerino.core.Preconditions.checkNotNull;

/**
 * @author John L. Jegutanis
 */
final public class SignedMessage {
    public enum Status {
        SignedOK, VerifiedOK, Unknown, AddressMalformed, KeyIsEncrypted, MissingPrivateKey,
        InvalidSigningAddress, InvalidMessageSignature
    }

    final String message;
    final String address;
    String signature;
    Status status = Status.Unknown;

    public SignedMessage(String address, String message, String signature) {
        this.address = Preconditions.checkNotNull(address);
        this.message = Preconditions.checkNotNull(message);
        this.signature = signature;
    }

    public SignedMessage(String address, String message) {
        this(address, message, null);
    }

    public SignedMessage(SignedMessage otherMessage, Status newStatus) {
        message = otherMessage.message;
        address = otherMessage.address;
        signature = otherMessage.signature;
        status = newStatus;
    }

    public String getAddress() {
        return address;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public String getSignature() {
        return signature;
    }

    public Status getStatus() {
        return status;
    }
}
