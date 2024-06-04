package tsp.coex.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.coex.command.context.CommandContext;

import java.util.List;
import java.util.Optional;

/**
 * Represents a command that can be executed.
 *
 * @author TheSilentPro (Silent)
 */
public interface Command extends CommandExecutor, TabCompleter {

    /**
     * The name.
     *
     * @return Name
     */
    String getName();

    /**
     * The usage format.
     *
     * @return Usage
     */
    Optional<String> getUsage();

    /**
     * Message sent if the arguments do not confront to the usage format.
     *
     * @return Usage message
     */
    Optional<Component> getUsageMessage();

    /**
     * The permission required to execute this command.
     *
     * @return The permission
     */
    Optional<String> getPermission();

    /**
     * The message sent if the sender does not have permission.
     *
     * @return The permission message
     */
    Optional<Component> getPermissionMessage();

    /**
     * The handler.
     *
     * @param ctx The {@link CommandContext}
     */
    void handler(CommandContext<CommandSender> ctx);

    /**
     * The tab completion handler.
     *
     * @param ctx The {@link CommandContext}
     * @return List of completions
     */
    List<String> onTab(CommandContext<CommandSender> ctx);

    /**
     * Register this command.
     *
     * @param plugin Plugin instance
     */
    void register(JavaPlugin plugin);

}