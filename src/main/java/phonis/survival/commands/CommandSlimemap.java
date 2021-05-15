package phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import phonis.survival.util.DirectionUtil;

import java.util.List;

public class CommandSlimemap extends SubCommand {
    public CommandSlimemap(JavaPlugin plugin) {
        super("slimemap");
        SubCommand.registerCommand(plugin, this);
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
        DirectionUtil.printSlimeMap(player);
    }
}
