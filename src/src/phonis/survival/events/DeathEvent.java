package src.phonis.survival.events;

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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DeathEvent implements Listener {
    private Survival plugin;

    public DeathEvent(Survival plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private String generateDropList(List<ItemStack> drops) {
        StringBuilder list = new StringBuilder();

        for (ItemStack is : drops) {
			if (!(list.toString().equals(""))) {
				list.append(", ").append(ChatColor.BLUE);
			}else {
				list.append(ChatColor.BLUE);
			}
			
			list.append(ChatColor.UNDERLINE).append(is.getType().name().toLowerCase().replace("_", " ").replace(" item", "").replace(" block", "")).append(ChatColor.RESET).append(" (x").append(is.getAmount()).append(")");
		}
		
		if (list.toString().equals("")) {
			list.append("nothing.");
		}
		
		list.append("\n");
		
		return list.toString();
	}

	@EventHandler
	public void onDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location location = player.getLocation();
        String originalName = player.getName();
        DeathMessage deathMessage;
        UUID uuid = player.getUniqueId();

        if (this.plugin.keepInventory) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }

        if (!DeathMessage.pd.data.containsKey(uuid)) {
            DeathMessage.pd.data.put(uuid, new DeathMessage(originalName));
        }

		deathMessage = DeathMessage.pd.data.get(uuid);

		Bukkit.getServer().broadcastMessage(
			"" +
				ChatColor.DARK_RED +
			ChatColor.BOLD +
			deathMessage.getName() + ChatColor.RESET + ChatColor.GOLD +
			Objects.requireNonNull(event.getDeathMessage()).replace(originalName, "") + "\n" +
			ChatColor.DARK_RED +
			ChatColor.BOLD + 
			deathMessage.getName() + ChatColor.RESET + ChatColor.DARK_GRAY + " died at: " +
			ChatColor.WHITE +
			Objects.requireNonNull(location.getWorld()).getName() + " " +
			location.getBlockX() + " " +
			location.getBlockY() + " " +
			location.getBlockZ() + "\n" +
			ChatColor.DARK_RED +
			ChatColor.BOLD + 
			deathMessage.getName() + ChatColor.RESET + " dropped " + this.generateDropList(event.getDrops()) +
			ChatColor.DARK_RED +
			ChatColor.BOLD + 
			deathMessage.getName() + ChatColor.RESET + ChatColor.AQUA + " " +
			deathMessage.getDeathMessage()
		);
		
		event.setDeathMessage(null);
	}
}
