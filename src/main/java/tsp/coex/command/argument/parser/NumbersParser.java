package tsp.coex.command.argument.parser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

import javax.annotation.Nonnull;

/**
 * @author TheSilentPro (Silent)
 */
public final class NumbersParser {

    private NumbersParser() {
        throw new UnsupportedOperationException("Utility class.");
    }

    @Nonnull
    public static Optional<Number> parse(@Nonnull String s) {
        try {
            return Optional.ofNullable(NumberFormat.getInstance().parse(s));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Integer> parseInteger(@Nonnull String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Long> parseLong(@Nonnull String s) {
        try {
            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Float> parseFloat(@Nonnull String s) {
        try {
            return Optional.of(Float.parseFloat(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Double> parseDouble(@Nonnull String s) {
        try {
            return Optional.of(Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    public static Optional<Byte> parseByte(@Nonnull String s) {
        try {
            return Optional.of(Byte.parseByte(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}