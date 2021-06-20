package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.networking.RTAdapter;
import phonis.survival.networking.RTManager;
import phonis.survival.networking.RTWaypointUpdate;
import phonis.survival.serializable.Waypoint;

import java.util.List;
import java.util.Objects;

public class CommandWaypointSet extends SubCommand {
    public CommandWaypointSet() {
        super("set", "(Waypoint)");
        this.addAlias("s");
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
        if (args.length == 0) {
            player.sendMessage(this.getCommandString(0));

            return;
        }

        Location location = player.getLocation();

        Waypoint.addWaypoint(args[0], location);

        player.sendMessage(
            ChatColor.WHITE + "Waypoint '" +
                ChatColor.AQUA + args[0] +
                ChatColor.WHITE + "' ➤ " + ChatColor.GRAY +
                Objects.requireNonNull(location.getWorld()).getName() + " " + ChatColor.GRAY +
                location.getBlockX() + " " +
                location.getBlockY() + " " +
                location.getBlockZ()
        );

        RTManager.sendToSubscribed(new RTWaypointUpdate(RTAdapter.fromWaypoint(Waypoint.pd.data.get(args[0]))));
    }
}
