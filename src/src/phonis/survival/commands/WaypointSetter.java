package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;

import javax.annotation.Nonnull;
import java.util.Objects;

public class WaypointSetter implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		if (args.length > 0) {
			Player player = (Player) sender;
			Location location = player.getLocation();
			
			Waypoint.addWaypoint(args[0], location);
			
			sender.sendMessage(
				ChatColor.GREEN +
				"Waypoint '" + args[0] + "' set at: " +
				Objects.requireNonNull(location.getWorld()).getName() + " " +
				location.getBlockX() + " " +
				location.getBlockY() + " " +
				location.getBlockZ()
			);
			
			return true;
		}
		
		return false;
	}
}
