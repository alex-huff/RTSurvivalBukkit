package src.phonis.survival.completers;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemTabCompleter implements TabCompleter {
	private static List<String> mats = Stream.of(Material.values())
		.map(Material::name)
		.collect(Collectors.toList());
	private int argSize;

	public ItemTabCompleter(int argSize) {
		this.argSize = argSize;
	}

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
