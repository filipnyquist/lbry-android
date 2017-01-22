package com.fillerino.core.coins;

import com.fillerino.core.coins.families.BitFamily;

/**
 * @author Filip Nyquist @ 2016
 */
public class LbryMain extends BitFamily {
    private LbryMain() {
        id = "lbry.main";

        addressHeader = 85;
        p2shHeader = 5;  //is this correct? shouldn't it be 122 for lbry? 
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 28;

        name = "LBRY";
        symbol = "LBC";
        uriScheme = "lbry";
        bip44Index = 150;
        unitExponent = 8;
        feeValue = value(12000);
        minNonDust = value(5460);
        softDustLimit = value(1000000); // 0.01 BTC
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT;
        signedMessageHeader = toBytes("Lbry Signed Message:\n");
    }

    private static LbryMain instance = new LbryMain();
    public static synchronized CoinType get() {
        return instance;
    }
}
