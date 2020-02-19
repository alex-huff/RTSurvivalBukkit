package src.phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandSpec extends SubCommand {
    public CommandSpec(JavaPlugin plugin) {
        super("spec");
        SubCommand.registerCommand(plugin, this);
        this.addSubCommand(new CommandSpecToggler());
        this.addSubCommand(new CommandSpecTp());
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException(CommandException.consoleError);
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        player.sendMessage(this.getCommandString(0));
    }
}
