package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import src.phonis.survival.Survival;

public class InventoryLock implements Listener {
	public InventoryLock(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

    @EventHandler
    public void LockInventory(InventoryClickEvent event) {
    	if(event.getInventory().getMaxStackSize() == Integer.MAX_VALUE) {
    		if(!event.isCancelled()) {
    			event.setCancelled(true);
    		}
    	}
    }
}