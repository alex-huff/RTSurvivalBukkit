package src.phonis.survival.completers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;

/**
 * TabCompleter that handles tab completion for commands like /getcraft that take in a Material
 */
public class ItemTabCompleter implements TabCompleter {
	private static List<String> mats = Stream.of(Material.values())
		.map(Material::name)
		.collect(Collectors.toList());
	private int argSize;

	/**
	 * ItemTabCompleter constructor that takes in an int size of arguments to be completed
	 * @param argSize number of arguments to auto complete
	 */
	public ItemTabCompleter(int argSize) {
		this.argSize = argSize;
	}

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

		if(args.length <= this.argSize) {
			for(String mat : mats) {
				if(mat.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
					ret.add(mat);
				}
			}
		}
		
		return ret;
	}
}
