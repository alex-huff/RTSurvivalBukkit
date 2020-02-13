package src.phonis.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.util.DirectionUtil;

import javax.annotation.Nonnull;

public class SlimemapShower implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		Player player = (Player) sender;
		
		DirectionUtil.printSlimeMap(player);
		
		return true;
	}
}
