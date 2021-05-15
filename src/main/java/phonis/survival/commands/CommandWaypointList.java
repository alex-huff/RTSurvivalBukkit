package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;

import java.util.List;
import java.util.Objects;

public class CommandWaypointList extends SubCommand {
    public CommandWaypointList() {
        super("list");
        this.addAlias("l");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (Waypoint.pd.data.size() != 0) {
            sender.sendMessage(
                ChatColor.WHITE +
                    "Waypoints:"
            );

            for (String key : Waypoint.pd.data.keySet()) {
                Waypoint waypoint = Waypoint.pd.data.get(key);

                sender.sendMessage(
                    ChatColor.AQUA +
                        key + ChatColor.WHITE + " ➤ " + ChatColor.GRAY +
                        Objects.requireNonNull(
                            Bukkit.getServer().getWorld(
                                waypoint.getWorld()
                            )
                        ).getName() + " " +
                        waypoint.getXPos() + " " +
                        waypoint.getYPos() + " " +
                        waypoint.getZPos()
                );
            }
        } else {
            sender.sendMessage(
                ChatColor.RED +
                    "There are no current waypoints"
            );
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
