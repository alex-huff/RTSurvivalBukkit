package src.phonis.survival.completers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import src.phonis.survival.serializable.Waypoint;

public class WaypointCompleter implements TabCompleter {
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(args.length == 1) {
			return Waypoint.getAutoComplete(args[0]);
		}
		
		return new ArrayList<>();
	}
}
