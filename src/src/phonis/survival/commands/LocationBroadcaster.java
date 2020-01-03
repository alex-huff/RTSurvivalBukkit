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

/**
 * CommandExecutor that handles the /bl command
 */
public class LocationBroadcaster implements CommandExecutor {
	/**
	 * Method implemented from CommandExecutor interface
	 * @param sender CommandSender object
	 * @param cmd Command object
	 * @param label String representing label
	 * @param args String[] containing command arguments
	 * @return boolean
	 */
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
