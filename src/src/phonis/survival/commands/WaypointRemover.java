package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import src.phonis.survival.serializable.Waypoint;

public class WaypointRemover implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			if (Waypoint.pd.data.remove(args[0]) != null) {
				sender.sendMessage(
					ChatColor.GREEN +
					"Waypoint '" + args[0] + "' removed."
				);
			}else {
				sender.sendMessage(
					ChatColor.RED +
					"Waypoint '" + args[0] + "' is invalid."
				);
			}
	
			return true;
		}
		
		return false;
	}
}
