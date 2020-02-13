package src.phonis.survival.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import src.phonis.survival.Survival;

import javax.annotation.Nonnull;

public class SleepDenier implements CommandExecutor {
	Survival plugin;

	public SleepDenier(Survival plugin) {
		this.plugin = plugin;
	}

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
