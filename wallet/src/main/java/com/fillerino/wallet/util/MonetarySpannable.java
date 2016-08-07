package com.fillerino.wallet.util;

/*
 * Copyright 2014 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.fillerino.core.util.MonetaryFormat;
import com.fillerino.wallet.Constants;

import org.bitcoinj.core.Monetary;

import java.util.regex.Matcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Andreas Schildbach
 */
public final class MonetarySpannable extends SpannableString {
    public MonetarySpannable(final MonetaryFormat format, final boolean signed, @Nullable final Monetary value) {
        super(format(format, signed, value));
    }

    public MonetarySpannable(final MonetaryFormat format, @Nullable final Monetary value) {
        super(format(format, false, value));
    }

    private static CharSequence format(final MonetaryFormat format, final boolean signed,
                                       final Monetary value) {
        if (value == null)
            return "";

        checkArgument(value.signum() >= 0 || signed);

        int smallestUnitExponent = value.smallestUnitExponent();

        if (signed)
            return format.negativeSign(Constants.CURRENCY_MINUS_SIGN).positiveSign(Constants.CURRENCY_PLUS_SIGN).format(value, smallestUnitExponent);
        else
            return format.format(value, smallestUnitExponent);
    }

    public MonetarySpannable applyMarkup(@Nullable final Object prefixSpan1, @Nullable final Object prefixSpan2,
                                         @Nullable final Object insignificantSpan) {
        applyMarkup(this, prefixSpan1, prefixSpan2, BOLD_SPAN, insignificantSpan);
        return this;
    }

    public static final Object BOLD_SPAN = new StyleSpan(Typeface.BOLD);
    public static final RelativeSizeSpan SMALLER_SPAN = new RelativeSizeSpan(0.85f);

    public static void applyMarkup(@Nonnull final Spannable spannable, @Nullable final Object prefixSpan1, @Nullable final Object prefixSpan2,
                                   @Nullable final Object significantSpan, @Nullable final Object insignificantSpan) {
        if (prefixSpan1 != null)
            spannable.removeSpan(prefixSpan1);
        if (prefixSpan2 != null)
            spannable.removeSpan(prefixSpan2);
        if (significantSpan != null)
            spannable.removeSpan(significantSpan);
        if (insignificantSpan != null)
            spannable.removeSpan(insignificantSpan);

        final Matcher m = Formats.PATTERN_MONETARY_SPANNABLE.matcher(spannable);
        if (m.find()) {
            int i = 0;

            if (m.group(Formats.PATTERN_GROUP_PREFIX) != null) {
                final int end = m.end(Formats.PATTERN_GROUP_PREFIX);
                if (prefixSpan1 != null)
                    spannable.setSpan(prefixSpan1, i, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (prefixSpan2 != null)
                    spannable.setSpan(prefixSpan2, i, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                i = end;
            }

            if (m.group(Formats.PATTERN_GROUP_SIGNIFICANT) != null) {
                final int end = m.end(Formats.PATTERN_GROUP_SIGNIFICANT);
                if (significantSpan != null)
                    spannable.setSpan(significantSpan, i, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                i = end;
            }

            if (m.group(Formats.PATTERN_GROUP_INSIGNIFICANT) != null) {
                final int end = m.end(Formats.PATTERN_GROUP_INSIGNIFICANT);
                if (insignificantSpan != null)
                    spannable.setSpan(insignificantSpan, i, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                i = end;
            }
        }
    }
}
