package src.phonis.survival.completers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class FindCompleter {
	private final int argSize;

	public FindCompleter(int argSize) {
		this.argSize = argSize;
	}

	public List<String> onTabComplete(String[] args) {
		List<String> ret = new ArrayList<>();

		if (args.length <= this.argSize) {
			ret.addAll(Waypoint.getAutoComplete(args[0]));

			for (World world : Bukkit.getServer().getWorlds()) {
				for (Player player : world.getPlayers()) {
					String name = player.getName();

					if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
						ret.add(name);
					}
				}
			}
		}
		
		return ret;
	}
}
