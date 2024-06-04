package tsp.coex.command.context;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.coex.CommandInterruptException;
import tsp.coex.command.Command;
import tsp.coex.command.argument.Argument;
import tsp.coex.command.argument.ArgumentImpl;
import tsp.coex.command.argument.parser.ArgumentParsers;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Implementation of {@link CommandContext}.
 *
 * @author TheSilentPro (Silent)
 */
public class CommandContextImpl<T extends CommandSender> implements CommandContext<T> {

    private final T sender;
    private final List<String> arguments;
    private final Set<String> options;
    private final Command command;

    public CommandContextImpl(T sender, String[] args, Command command) {
        this.sender = sender;
        this.command = command;
        this.arguments = new ArrayList<>(Arrays.asList(args)); // Mutable
        this.options = new HashSet<>();

        // Loop raw arguments to match options and remove them from the arguments list.
        for (Iterator<String> iterator = arguments.iterator(); iterator.hasNext();) {
            String arg = iterator.next();
            if (arg.startsWith(optionPrefix())) {
                options.add(arg.substring(optionPrefix().length()));
                iterator.remove();
            }
        }
    }

    @Override
    public T sender() {
        return sender;
    }

    @Override
    public Command command() {
        return command;
    }

    @Override
    public Set<String> options() {
        return Collections.unmodifiableSet(options);
    }

    @Override
    public List<String> rawArgs() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public Optional<String> rawArg(int index) {
        if (index < 0 || index >= this.arguments.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.arguments.get(index));
    }

    @Override
    public Argument arg(int index) {
        return new ArgumentImpl(index, rawArg(index).orElse(null));
    }

    @Override
    public Optional<Argument> argOpt(int index) {
        return rawArg(index).map(arg -> new ArgumentImpl(index, arg));
    }

    @Override
    public Argument[] args() {
        Argument[] args = new Argument[arguments.size()];
        for (int i = 0; i < arguments.size(); i++) {
            args[i] = arg(i);
        }
        return args;
    }

    @Override
    public String optionPrefix() {
        return "-";
    }

    @Override
    public CommandContext<T> assertion(boolean assertion, @Nullable String failureMessage) {
        if (assertion) {
            return this;
        } else {
            if (failureMessage != null) reply(failureMessage);
            throw new CommandInterruptException();
        }
    }

    @Override
    public boolean hasPermission(@Nonnull String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    @Override
    public boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    @Override
    public boolean isRemoteConsole() {
        return sender instanceof RemoteConsoleCommandSender;
    }

    @Override
    public boolean isArgument(int index, @NotNull Class<?> type) {
        return ArgumentParsers.INSTANCE.find(type).orElseThrow(() -> new NoSuchElementException("Unable to find ArgumentParser for " + type)).parse(rawArg(index).orElse("")).isPresent();
    }

    @Override
    public <U> U validateArgument(int index, @NotNull Class<U> type, @Nullable String failureMessage) {
        Optional<U> result = ArgumentParsers.INSTANCE.find(type).orElseThrow(() -> new NoSuchElementException("Unable to find ArgumentParser for " + type)).parse(rawArg(index).orElse(""));
        if (result.isPresent()) {
            return result.get();
        } else {
            if (failureMessage != null) reply(failureMessage);
            throw new CommandInterruptException();
        }
    }

    @Override
    public <U> U validateArgument(int index, @NotNull Class<U> type, @Nullable UnaryOperator<@Nullable String> failureMessage) {
        Optional<U> result = ArgumentParsers.INSTANCE.find(type).orElseThrow(() -> new NoSuchElementException("Unable to find ArgumentParser for " + type)).parse(rawArg(index).orElse(""));
        if (result.isPresent()) {
            return result.get();
        } else {
            if (failureMessage != null) {
                String arg = failureMessage.apply(rawArg(index).orElse(null));
                if (arg != null) {
                    reply(arg);
                }
            }
            throw new CommandInterruptException();
        }
    }

    @Override
    public CommandContext<T> reply(@NotNull String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        return this;
    }

}