package tsp.coex;

/**
 * @author TheSilentPro (Silent)
 */
public class CommandInterruptException extends RuntimeException {

    public CommandInterruptException() {
        super("Command failed an assertion!");
    }

}