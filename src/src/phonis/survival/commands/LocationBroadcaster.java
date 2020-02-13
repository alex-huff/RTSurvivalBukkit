package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Objects;

public class LocationBroadcaster implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		Player player = (Player) sender;
		Location location = player.getLocation();
		
		Bukkit.getServer().broadcastMessage(
			ChatColor.GREEN +
			player.getName() + " is at: " +
			Objects.requireNonNull(location.getWorld()).getName() + " " +
			location.getBlockX() + " " +
			location.getBlockY() + " " +
			location.getBlockZ()
		);
		
		return true;
	}
}
