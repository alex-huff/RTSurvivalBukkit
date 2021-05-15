package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import phonis.survival.Survival;

public class FishEvent implements Listener {
    private final Survival survival;

    public FishEvent(Survival survival) {
        this.survival = survival;

        Bukkit.getServer().getPluginManager().registerEvents(this, this.survival);
    }

    @EventHandler
    public void onBite(PlayerFishEvent event) {
        if (!event.isCancelled()) {
            if (event.getState().equals(PlayerFishEvent.State.BITE)) {
                event.getPlayer().sendMessage("Bite!");
            }
        }
    }
}
