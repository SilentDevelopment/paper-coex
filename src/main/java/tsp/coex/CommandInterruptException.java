package tsp.coex;

/**
 * @author TheSilentPro (Silent)
 */
public class CommandInterruptException extends RuntimeException {

    public CommandInterruptException() {
        super("Command failed an assertion!");
    }

    public CommandInterruptException(String message) {
        super(message);
    }

}