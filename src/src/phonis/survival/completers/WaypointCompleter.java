package src.phonis.survival.completers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import src.phonis.survival.serializable.Waypoint;

import javax.annotation.Nonnull;

/**
 * TabCompleter that handles tab completion for commands like /removewaypoint and /updateposwaypoint that take in a Waypoint
 */
public class WaypointCompleter implements TabCompleter {
	/**
	 * Method implemented from the TabCompleter interface
	 * @param sender CommandSender object
	 * @param cmd Command object
	 * @param alias String representing alias
	 * @param args String[] containing command arguments
	 * @return boolean
	 */
	@Override
	public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String alias, @Nonnull String[] args) {
		if(args.length == 1) {
			return Waypoint.getAutoComplete(args[0]);
		}
		
		return new ArrayList<>();
	}
}
