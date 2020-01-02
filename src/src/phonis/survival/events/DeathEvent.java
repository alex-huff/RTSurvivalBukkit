package src.phonis.survival.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import src.phonis.survival.Survival;
import src.phonis.survival.serializable.DeathMessage;

public class DeathEvent implements Listener {
	public DeathEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private String generateDropList(List<ItemStack> drops, ChatColor cc) {
		String list = "";
		
		for (ItemStack is : drops) {
			if (!(list == "")) {
				list += ", " + cc;
			}else {
				list += cc;
			}
			
			list += ChatColor.UNDERLINE + 
				is.getType().name().toLowerCase().replace("_", " ").replace(" item", "").replace(" block", "") + 
				ChatColor.RESET +
				" (x" + is.getAmount() + ")"; 
		}
		
		if (list == "") {
			list += "nothing.";
		}
		
		list += "\n";
		
		return list;
	}
	
	@EventHandler
	public void onDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Location location = player.getLocation();
		String originalName = player.getName();
		DeathMessage deathMessage;
		UUID uuid = player.getUniqueId();
		
		if (!DeathMessage.pd.data.containsKey(uuid)) {
			DeathMessage.pd.data.put(uuid, new DeathMessage(originalName, uuid));
		}
		
		deathMessage = DeathMessage.pd.data.get(uuid);
		
		Bukkit.getServer().broadcastMessage(
			"" + 
			ChatColor.DARK_RED +
			ChatColor.BOLD +
			deathMessage.getName() + ChatColor.RESET + ChatColor.GOLD +
			event.getDeathMessage().replace(originalName, "") + "\n" +
			ChatColor.DARK_RED +
			ChatColor.BOLD + 
			deathMessage.getName() + ChatColor.RESET + ChatColor.DARK_GRAY + " died at: " +
			ChatColor.WHITE +
			location.getWorld().getName() + " " +
			location.getBlockX() + " " +
			location.getBlockY() + " " +
			location.getBlockZ() + "\n" +
			ChatColor.DARK_RED +
			ChatColor.BOLD + 
			deathMessage.getName() + ChatColor.RESET + " dropped " + this.generateDropList(event.getDrops(), ChatColor.BLUE) +
			ChatColor.DARK_RED +
			ChatColor.BOLD + 
			deathMessage.getName() + ChatColor.RESET + ChatColor.AQUA + " " +
			deathMessage.getDeathMessage()
		);
		
		event.setDeathMessage(null);
	}
}
