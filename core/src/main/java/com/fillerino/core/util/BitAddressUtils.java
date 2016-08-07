package com.fillerino.core.util;

import com.fillerino.core.exceptions.AddressMalformedException;
import com.fillerino.core.wallet.AbstractAddress;
import com.fillerino.core.wallet.families.bitcoin.BitAddress;
import com.fillerino.core.Preconditions;

import org.bitcoinj.script.Script;

import static com.fillerino.core.Preconditions.checkArgument;

/**
 * @author John L. Jegutanis
 */
public class BitAddressUtils {
    public static boolean isP2SHAddress(AbstractAddress address) {
        Preconditions.checkArgument(address instanceof BitAddress, "This address cannot be a P2SH address");
        return ((BitAddress) address).isP2SHAddress();
    }

    public static byte[] getHash160(AbstractAddress address) {
        Preconditions.checkArgument(address instanceof BitAddress, "Cannot get hash160 from this address");
        return ((BitAddress) address).getHash160();
    }

    public static boolean producesAddress(Script script, AbstractAddress address) {
        try {
            return BitAddress.from(address.getType(), script).equals(address);
        } catch (AddressMalformedException e) {
            return false;
        }
    }
}
