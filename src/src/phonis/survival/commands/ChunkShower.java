package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChunkShower implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(
			ChatColor.BLUE +
			"Chunks force loaded:"
		);
		
		for(World world : Bukkit.getServer().getWorlds()) {
			sender.sendMessage(
				ChatColor.GREEN +
				"World: " +
				world.getName()
			);
			
			for(Chunk chunk : world.getForceLoadedChunks()) {
				sender.sendMessage(
					ChatColor.RED +
					"X: " +
					chunk.getX() + " " +
					"Z: " +
					chunk.getZ()
				);
			}
		}
		
		return true;
	}
}
