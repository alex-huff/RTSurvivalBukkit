package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.Survival;

import javax.annotation.Nonnull;

/**
 * CommandExecutor that handles the /sleepdeny command
 */
public class SleepDenier implements CommandExecutor {
	Survival plugin;

	/**
	 * Constructor for SleepDenier that takes in Survival plugin
	 * @param plugin Survival plugin
	 */
	public SleepDenier(Survival plugin) {
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
		Sleeper sleeper = this.plugin.getSleeper();
		
		if(sleeper.task != null && !sleeper.task.isCancelled()) {
			sleeper.task.cancel();
			
			Bukkit.getServer().broadcastMessage(
				ChatColor.RED +
				"Sleep cancelled by: " + 
				ChatColor.WHITE +
				ChatColor.BOLD +
				sender.getName()
			);
		}else {
			sender.sendMessage(
				ChatColor.RED +
				"No current sleep vote"
			);
		}
		
		return true;
	}
}
