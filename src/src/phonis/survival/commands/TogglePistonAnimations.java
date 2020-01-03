package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.Survival;

import javax.annotation.Nonnull;

/**
 * CommandExecutor that handles the /togglepiston command
 */
public class TogglePistonAnimations implements CommandExecutor {
	private Survival plugin;

	/**
	 * TogglePistonAnimations constructor that takes in the Survival plugin
	 * @param plugin Survival plugin
	 */
	public TogglePistonAnimations(Survival plugin) {
		this.plugin = plugin;
	}

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
		synchronized (this) {
			this.plugin.redstoneListener.handlePackets = !this.plugin.redstoneListener.handlePackets;
		}

		Bukkit.broadcastMessage(
			ChatColor.DARK_GREEN + 
			"Piston animations are now: " +
			!this.plugin.redstoneListener.handlePackets
		);
		
		return true;
	}
}
