package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import src.phonis.survival.Survival;
import src.phonis.survival.tasks.SleepTask;

public class Sleeper implements CommandExecutor {
	Survival plugin;
	BukkitTask task = null;
	
	public Sleeper(Survival plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(this.task == null || this.task.isCancelled()) {
			Player player = (Player) sender;

			if(player.isSleeping()) {
				this.task = new SleepTask(player.getWorld()).runTaskLater((Plugin) this.plugin, 200);
				
				Bukkit.getServer().broadcastMessage(
					ChatColor.AQUA +
					"SLEEP VOTE FOR WORLD: " +
					player.getWorld().getName() +
					"\n" +
					"Sleep will happen in 10 seconds if not denied\n" +
					ChatColor.WHITE +
					"/sleepdeny to cancel worldwide sleep"
				);
			}else {
				sender.sendMessage(
					ChatColor.RED +
					"You must be sleeping to use this command"
				);
			}
		}else {
			sender.sendMessage(
				ChatColor.RED +
				"Sleep survey already running"
			);
		}
	
		return true;
	}
}
