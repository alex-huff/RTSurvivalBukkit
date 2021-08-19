package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.networking.RTManager;
import phonis.survival.networking.V1.RTWaypointRemove;
import phonis.survival.serializable.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class CommandWaypointRemove extends SubCommand {
    public CommandWaypointRemove() {
        super("remove", "(Waypoint)");
        this.addAlias("r");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        if (args.length == 1) {
            return Waypoint.getAutoComplete(args[0]);
        }

        return new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(this.getCommandString(0));

            return;
        }

        if (Waypoint.removeWaypoint(args[0]) == null) {
            throw new CommandException("Invalid waypoint for removal");
        }

        sender.sendMessage(
            ChatColor.WHITE +
                "Waypoint '" +
                ChatColor.AQUA + args[0] + ChatColor.WHITE +
                "' removed."
        );

        RTManager.sendToSubscribed(new RTWaypointRemove(args[0]));
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        this.execute((CommandSender) player, args);
    }
}
