package src.phonis.survival.commands;

import org.bukkit.ChatColor;

public class CommandException extends Exception {
    public static final String consoleError = "Only players can use this command";
    private static final String prefix = ChatColor.RED + "Survival command usage error ➤ " + ChatColor.WHITE;

    public CommandException(String error) {
        super(prefix + error);
    }
}

