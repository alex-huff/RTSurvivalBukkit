package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationBroadcaster implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		Location location = player.getLocation();
		
		Bukkit.getServer().broadcastMessage(
			ChatColor.GREEN +
			player.getName() + " is at: " +
			location.getWorld().getName() + " " +
			location.getBlockX() + " " +
			location.getBlockY() + " " +
			location.getBlockZ()
		);
		
		return true;
	}
}
