package src.phonis.survival.completers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ItemTabCompleter implements TabCompleter {
	private static List<String> mats = Stream.of(Material.values())
		.map(Material::name)
		.collect(Collectors.toList());
	private int argSize;
	
	public ItemTabCompleter(int argSize) {
		this.argSize = argSize;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
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
