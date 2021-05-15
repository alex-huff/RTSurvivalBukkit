package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import phonis.survival.Survival;

public class FireSpreadEvent implements Listener{
	public FireSpreadEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockIgnite(BlockIgniteEvent event){
		if (!event.isCancelled()) {
			IgniteCause cause = event.getCause();
			
		    if (cause == IgniteCause.SPREAD || cause == IgniteCause.LAVA){
		        event.setCancelled(true);
		    }
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBurn(BlockBurnEvent event) {
		if (!event.isCancelled()) {
			event.setCancelled(true);
		}
	}
}