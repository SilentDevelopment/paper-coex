package tsp.coex.command.argument.parser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author TheSilentPro (Silent)
 */
public final class NumbersParser {

    private NumbersParser() {
        throw new UnsupportedOperationException("Utility class.");
    }

    @Nonnull
    public static Optional<Number> parse(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(NumberFormat.getInstance().parse(s));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Integer> parseInteger(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Long> parseLong(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Float> parseFloat(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Float.parseFloat(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Double> parseDouble(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Byte> parseByte(@Nullable String s) {
        if (s == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Byte.parseByte(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}