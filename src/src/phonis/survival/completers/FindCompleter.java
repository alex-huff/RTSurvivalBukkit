package src.phonis.survival.completers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FindCompleter implements TabCompleter {
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
