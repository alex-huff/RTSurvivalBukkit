package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
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
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
//            enderpearl: {
//                Player player = (Player) entity;
//                Location pLoc = player.getLocation();
//
//                for (int i = 0; i < 3; i++) {
//                    EnderPearl pearl = (EnderPearl) pLoc.getWorld().spawnEntity(pLoc, EntityType.ENDER_PEARL);
//                    pearl.setShooter(player);
//                    pearl.setVelocity(new Vector(Math.random(), 1, Math.random()));
//                }
//            }

//            bomber: {
//                Player player = (Player) entity;
//                Location pLoc = player.getLocation();
//
//                if ((event.getCause().equals(DamageCause.FALL) || event.getCause().equals(DamageCause.FLY_INTO_WALL)) && (player.getHealth() - event.getDamage()) < 0) {
//                    if (player.getInventory().getItem(38).getType().equals(Material.ELYTRA)) {
//                        TNTPrimed tnt = (TNTPrimed) pLoc.getWorld().spawnEntity(pLoc, EntityType.PRIMED_TNT);
//                        tnt.setFuseTicks(0);
//                    }
//                }
//            }

            if (event.getCause() == DamageCause.SUFFOCATION) {
                event.setCancelled(true);
            }
        }
    }
}
