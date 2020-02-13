package src.phonis.survival.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import src.phonis.survival.serializable.Waypoint;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WaypointCompleter implements TabCompleter {
	@Override
	public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String alias, @Nonnull String[] args) {
		if(args.length == 1) {
			return Waypoint.getAutoComplete(args[0]);
		}
		
		return new ArrayList<>();
	}
}
