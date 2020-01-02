package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import src.phonis.survival.Survival;

public class TeleportEvent implements Listener {
	public TeleportEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		
		for(Entity entity : player.getWorld().getEntitiesByClasses(Wolf.class, Parrot.class, Ocelot.class)) {
			if(((Tameable) entity).isTamed() && ((Tameable) entity).getOwner().getUniqueId().equals(player.getUniqueId())) {
				entity.teleport(event.getTo());
			}
		}
	}
}
