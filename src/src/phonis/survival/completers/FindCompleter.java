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

public class FindCompleter implements TabCompleter {
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
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
