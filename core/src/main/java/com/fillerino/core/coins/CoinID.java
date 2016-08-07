package com.fillerino.core.coins;

import com.fillerino.core.exceptions.AddressMalformedException;
import com.fillerino.core.util.GenericUtils;
import com.google.common.collect.ImmutableList;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.Networks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * When adding new coin types the order affects which types will be chosen by default if they share
 * a URI scheme. For example BITCOIN_MAIN and BITCOIN_TEST share the bitcoin: scheme so BITCOIN_MAIN
 * will be chosen by default when we don't have any other information. The same applies to the other
 * testnets and NUBITS_MAIN and NUSHARES_MAIN that share the nu: URI scheme. For anything else the
 * order doesn't matter.
 *
 * @author John L. Jegutanis
 */
public enum CoinID {
    LBRY_MAIN(LbryMain.get())
    ;

    private static List<CoinType> types;
    private static HashMap<String, CoinType> idLookup = new HashMap<>();
    private static HashMap<String, CoinType> symbolLookup = new HashMap<>();
    private static HashMap<String, ArrayList<CoinType>> uriLookup = new HashMap<>();

    static {
        Set<NetworkParameters> bitcoinjNetworks = Networks.get();
        for (NetworkParameters network : bitcoinjNetworks) {
            Networks.unregister(network);
        }

        ImmutableList.Builder<CoinType> coinTypeBuilder = ImmutableList.builder();
        for (CoinID id : values()) {
            Networks.register(id.type);

            if (symbolLookup.containsKey(id.type.symbol)) {
                throw new IllegalStateException(
                        "Coin currency codes must be unique, double found: " + id.type.symbol);
            }
            symbolLookup.put(id.type.symbol, id.type);

            if (idLookup.containsKey(id.type.getId())) {
                throw new IllegalStateException(
                        "Coin IDs must be unique, double found: " + id.type.getId());
            }
            // Coin ids must end with main or test
            if (!id.type.getId().endsWith("main") && !id.type.getId().endsWith("test")) {
                throw new IllegalStateException(
                        "Coin IDs must end with 'main' or 'test': " + id.type.getId());
            }
            idLookup.put(id.type.getId(), id.type);

            if (!uriLookup.containsKey(id.type.uriScheme)) {
                uriLookup.put(id.type.uriScheme, new ArrayList<CoinType>());
            }
            uriLookup.get(id.type.uriScheme).add(id.type);

            coinTypeBuilder.add(id.type);
        }
        types = coinTypeBuilder.build();
    }

    private final CoinType type;

    CoinID(final CoinType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.getId();
    }

    public CoinType getCoinType() {
        return type;
    }

    public static List<CoinType> getSupportedCoins() {
        return types;
    }

    public static CoinType typeFromId(String stringId) {
        if (idLookup.containsKey(stringId)) {
            return idLookup.get(stringId);
        } else {
            throw new IllegalArgumentException("Unsupported ID: " + stringId);
        }
    }

    public static List<CoinType> fromUri(String input) {
        String inputLowercase = input.toLowerCase();
        for (String uri : uriLookup.keySet()) {
            if (inputLowercase.startsWith(uri + "://") || inputLowercase.startsWith(uri + ":")) {
                return uriLookup.get(uri);
            }
        }
        throw new IllegalArgumentException("Unsupported URI: " + input);
    }

    public static List<CoinType> fromUriScheme(String scheme) {
        String schemeLowercase = scheme.toLowerCase();
        if (uriLookup.containsKey(schemeLowercase)) {
            return uriLookup.get(schemeLowercase);
        } else {
            throw new IllegalArgumentException("Unsupported URI scheme: " + scheme);
        }
    }

    public static List<CoinType> typesFromAddress(String address) throws AddressMalformedException {
        return GenericUtils.getPossibleTypes(address);
    }

    public static boolean isSymbolSupported(String symbol) {
        return symbolLookup.containsKey(symbol);
    }

    public static CoinType typeFromSymbol(String symbol) {
        if (symbolLookup.containsKey(symbol.toUpperCase())) {
            return symbolLookup.get(symbol.toUpperCase());
        } else {
            throw new IllegalArgumentException("Unsupported coin symbol: " + symbol);
        }
    }
}
