package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * CommandExecutor that handles the /unloadradius (int) command
 */
public class RadiusUnloader implements CommandExecutor {
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
		if (args.length > 0) {
    	    int radius;
    	    
	        try {
	            radius = Integer.parseInt(args[0]);
	        }catch(NumberFormatException e) {
	            sender.sendMessage(ChatColor.RED + "Not a valid number!");
	            
	            return false;
	        }
	        
			Player player = (Player) sender;
	        Chunk chunk = player.getLocation().getChunk();
	        World world = chunk.getWorld();
	        int chunkX = chunk.getX();
	        int chunkZ = chunk.getZ();
	        int chunkCount = 0;
	        
	        if(radius > 100) {
	        	sender.sendMessage(ChatColor.DARK_RED + "Max radius is 100 - phonis.");
	        	
	        	return true;
	        }
	        
	        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
	        	for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
	        		if (world.isChunkForceLoaded(x, z)){
	    				world.setChunkForceLoaded(x, z, false);
	    				chunkCount++;
	        		}
	        	}
	        }
	        
			sender.sendMessage(
				ChatColor.DARK_AQUA +
				"Removed " + 
				chunkCount +
				" chunks from ChunkLoader."
			);
			
			return true;
		}
		
		return false;
	}
}
