package tsp.coex.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.coex.CommandInterruptException;
import tsp.coex.command.context.CommandContext;
import tsp.coex.command.context.CommandContextImpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * An abstract implementation of a {@link Command}.
 *
 * @author TheSilentPro (Silent)
 */
public abstract class AbstractCommand implements Command {

    private static final Pattern USAGE_PATTERN = Pattern.compile(" ");

    @NotNull
    private final String name;
    @Nullable
    private final String usage;
    @Nullable
    private final String usageMessage;
    @Nullable
    private final String permission;
    @Nullable
    private final String permissionMessage;

    public AbstractCommand(@NotNull String name, @Nullable String usage, @Nullable String usageMessage, @Nullable String permission, @Nullable String permissionMessage) {
        this.name = name;
        this.usage = usage;
        this.usageMessage = usageMessage;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
    }

    public AbstractCommand(@NotNull String name, @Nullable String usage, @Nullable String usageMessage, @Nullable String permission) {
        this(name, usage, usageMessage, permission, null);
    }

    public AbstractCommand(@NotNull String name, @Nullable String usage, @Nullable String usageMessage) {
        this(name, usage, usageMessage, null, null);
    }

    public AbstractCommand(@NotNull String name, @Nullable String usage) {
        this(name, usage, null, null, null);
    }

    public AbstractCommand(@NotNull String name) {
        this(name, null);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public Optional<String> getUsage() {
        return Optional.ofNullable(usage);
    }

    @NotNull
    @Override
    public Optional<String> getUsageMessage() {
        return Optional.ofNullable(usageMessage);
    }

    @NotNull
    @Override
    public Optional<String> getPermission() {
        return Optional.ofNullable(permission);
    }

    @NotNull
    @Override
    public Optional<String> getPermissionMessage() {
        return Optional.ofNullable(permissionMessage);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        // Validate the user has permission
        if (permission != null) {
            if (!sender.hasPermission(permission)) {
                if (permissionMessage != null) sender.sendMessage(permissionMessage);
                return true;
            }
        }

        // Validate usage format
        if (usage != null) {
            String[] parts = USAGE_PATTERN.split(usage);

            int required = 0;
            for (String part : parts) {
                if (!part.startsWith("[") && !part.endsWith("]")) { // Even if it doesn't have arrow brackets("<>"), assume the argument is required.
                    required++;
                }
            }

            if (args.length < required) {
                if (usageMessage != null) sender.sendMessage(usageMessage.replace("{usage}", "/" + label + " " + usage));
                return true;
            }
        }

        // Fire command handler, ignore thrown exceptions
        try {
            handler(new CommandContextImpl<>(sender, args, this));
        } catch (CommandInterruptException ex) {
            onAssertionFailure(ex);
        }
        return true;
    }

    // TODO: Improve tab completions
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        return onTab(new CommandContextImpl<>(sender, args, this));
    }

    @Override
    public List<String> onTab(CommandContext<CommandSender> ctx) {
        return null;
    }

    public void onAssertionFailure(CommandInterruptException ex) {}

    @Override
    public void register(JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(getName());
        if (pluginCommand == null) {
            throw new NoSuchElementException("Missing plugin.yml command: " + getName());
        }

        pluginCommand.setExecutor(this);
    }

}