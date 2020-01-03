package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

import src.phonis.survival.Survival;

/**
 * Listener that handles BlockIgniteEvent, BlockBurnEvent
 */
public class FireSpreadEvent implements Listener{
	/**
	 * DeathEvent constructor that takes in Survival plugin
	 * @param plugin Survival plugin
	 */
	public FireSpreadEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Method decorated by EventHandler that handles BlockIgniteEvent
	 * @param event BlockIgniteEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockIgnite(BlockIgniteEvent event){
		if (!event.isCancelled()) {
			IgniteCause cause = event.getCause();
			
		    if (cause == IgniteCause.SPREAD || cause == IgniteCause.LAVA){
		        event.setCancelled(true);
		    }
		}
	}

	/**
	 * Method decorated by EventHandler that handles BlockBurnEvent
	 * @param event BlockBurnEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBurn(BlockBurnEvent event) {
		if (!event.isCancelled()) {
			event.setCancelled(true);
		}
	}
}