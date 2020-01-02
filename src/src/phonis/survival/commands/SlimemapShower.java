package src.phonis.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import src.phonis.survival.util.DirectionUtil;

public class SlimemapShower implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		DirectionUtil.printSlimeMap(player);
		
		return true;
	}
}
