package tsp.coex.command.argument.parser;

import org.jetbrains.annotations.NotNull;
import tsp.coex.CommandInterruptException;
import tsp.coex.command.argument.Argument;

import java.util.Optional;

/**
 * Represents a parser for an {@link Argument}.
 *
 * @author TheSilentPro (Silent)
 */
public interface ArgumentParser<T> {

    /**
     * Parses the given string using this parser.
     *
     * @param s The argument string
     * @return The parsed argument, if present
     */
    Optional<T> parse(String s);

    /**
     * Parses the given argument using this parser, otherwise fail.
     *
     * @param s The string argument
     * @return The parsed argument
     */
    @NotNull
    default T parseOrFail(@NotNull String s) throws CommandInterruptException {
        Optional<T> ret = parse(s);
        if (ret.isEmpty()) {
            throw new CommandInterruptException("&cCould not parse argument: " + s);
        }
        return ret.get();
    }

    /**
     * Parses the given argument using this parser.
     *
     * @param argument The argument
     * @return The parsed argument, if present
     */
    @NotNull
    default Optional<T> parse(@NotNull Argument argument) {
        return argument.value().flatMap(this::parse);
    }

    /**
     * Parses the given argument using this parser, otherwise fail.
     *
     * @param argument The argument
     * @return The parsed argument
     */
    @NotNull
    default T parseOrFail(@NotNull Argument argument) throws CommandInterruptException {
        Optional<String> value = argument.value();
        if (value.isEmpty()) {
            throw new CommandInterruptException("&cArgument " + argument.index() + " is missing.");
        }
        return parseOrFail(value.get());
    }

}