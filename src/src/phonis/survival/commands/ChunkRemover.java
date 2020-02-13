package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class ChunkRemover implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		sender.sendMessage(
			ChatColor.DARK_AQUA +
			"Clearing all forceloaded chunks."
		);
		
		for(World world : Bukkit.getServer().getWorlds()) {
			for(Chunk chunk : world.getForceLoadedChunks()) {
				world.setChunkForceLoaded(chunk.getX(), chunk.getZ(), false);
			}
		}
		
		return true;
	}
}
