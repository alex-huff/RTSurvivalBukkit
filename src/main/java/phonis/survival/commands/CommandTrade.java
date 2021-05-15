package phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandTrade extends SubCommand {
    public CommandTrade(JavaPlugin plugin) {
        super("trade");
        SubCommand.registerCommand(plugin, this);
        this.addSubCommand(new CommandTradeFind());
        this.addSubCommand(new CommandTradeGet());
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
    public void execute(Player player, String[] args) {
        player.sendMessage(this.getCommandString(0));
    }
}
