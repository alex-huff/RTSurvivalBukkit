package src.phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandWaypoint extends SubCommand {
    public CommandWaypoint(JavaPlugin plugin) {
        super("waypoint");
        CommandWaypoint.registerCommand(plugin, this);
        this.addSubCommand(new CommandWaypointSet());
        this.addSubCommand(new CommandWaypointRemove());
        this.addSubCommand(new CommandWaypointList());
        this.addSubCommand(new CommandWaypointUpdate());
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(this.getCommandString(0));
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
