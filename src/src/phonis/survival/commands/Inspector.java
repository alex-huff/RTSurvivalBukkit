package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;

import javax.annotation.Nonnull;

/**
 * CommandExecutor that handles the /inspect (Player) command
 */
public class Inspector implements CommandExecutor {
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
			Player player = (Player) sender;
			Player playerFind = Bukkit.getServer().getPlayer(args[0]);
			
			if (playerFind != null) {
				Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
				inventory.setContents(playerFind.getInventory().getContents());
				inventory.setMaxStackSize(Integer.MAX_VALUE);
				player.openInventory(inventory);
			}else {
				sender.sendMessage(
					ChatColor.DARK_RED +
					"Player not found"
				);
			}
			
			return true;
		}
		
		return false;
	}
}
