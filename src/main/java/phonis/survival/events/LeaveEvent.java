package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import phonis.survival.Survival;
import phonis.survival.networking.RTManager;

public class LeaveEvent implements Listener {

    public LeaveEvent(Survival plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        RTManager.removeFromSubscribed(event.getPlayer().getUniqueId());
    }

}
