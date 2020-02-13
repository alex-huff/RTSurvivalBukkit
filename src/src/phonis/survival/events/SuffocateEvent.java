package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import src.phonis.survival.Survival;

/**
 * Listener that handles EntityDamageEvent
 */
public class SuffocateEvent implements Listener {
	/**
	 * SuffocatedEvent constructor that takes in Survival plugin
	 * @param plugin Survival plugin
	 */
	public SuffocateEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Method decorated by EventHandler that handles EntityDamageEvent
	 * @param event EntityDamageEvent
	 */
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            if (event.getCause() == DamageCause.SUFFOCATION) {
                event.setCancelled(true);
            }
        }
    }
}
