package src.phonis.survival.completers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import src.phonis.survival.serializable.Waypoint;

import javax.annotation.Nonnull;

/**
 * TabCompleter that handles tab completion for commands like /find, /spectp that take in args of Waypoints OR Players
 */
public class FindCompleter implements TabCompleter {
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
		List<String> ret = new ArrayList<>();
		
		if(args.length == 1) {
			ret.addAll(Waypoint.getAutoComplete(args[0]));
			
			for(World world : Bukkit.getServer().getWorlds()) {
				for(Player player : world.getPlayers()) {
					String name = player.getName();
					
					if(name.toLowerCase().startsWith(args[0].toLowerCase())) {
						ret.add(name);
					}
				}
			}
		}
		
		return ret;
	}
}
