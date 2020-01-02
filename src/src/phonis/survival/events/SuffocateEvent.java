package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import src.phonis.survival.Survival;

public class SuffocateEvent implements Listener {
	public SuffocateEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.SUFFOCATION) {
			if (event.getEntity() instanceof Player) {
				event.setCancelled(true);
			}
		}
	}
}
