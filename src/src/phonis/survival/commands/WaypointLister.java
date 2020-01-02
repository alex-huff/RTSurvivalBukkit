package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import src.phonis.survival.serializable.Waypoint;

public class WaypointLister implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (Waypoint.pd.data.size() != 0) {
			sender.sendMessage(
				ChatColor.BLUE +
				"Waypoints:"
			);
			
			for (String key : Waypoint.pd.data.keySet()) {
				Waypoint waypoint = Waypoint.pd.data.get(key);
				
				sender.sendMessage(
					ChatColor.AQUA +
					key + ": " + ChatColor.GRAY +
					Bukkit.getServer().getWorld(
						waypoint.getWorld()
					).getName() + " " +
					waypoint.getXPos() + " " +
					waypoint.getYPos() + " " +
					waypoint.getZPos()
				);
			}
		}else {
			sender.sendMessage(
				ChatColor.RED +
				"There are no current waypoints"
			);
		}
		
		return true;
	}
}
