package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import src.phonis.survival.Survival;

/**
 * Listener that handles InventoryClickEvent
 */
public class InventoryLock implements Listener {
	/**
	 * InventoryLock that takes in Survival plugin
	 * @param plugin Survival plugin
	 */
	public InventoryLock(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Method decorated by EventHandler that handles InventoryClickEvent
	 * @param event InventoryClickEvent
	 */
    @EventHandler
    public void LockInventory(InventoryClickEvent event) {
    	if(event.getInventory().getMaxStackSize() == Integer.MAX_VALUE) {
    		if(!event.isCancelled()) {
    			event.setCancelled(true);
    		}
    	}
    }
}