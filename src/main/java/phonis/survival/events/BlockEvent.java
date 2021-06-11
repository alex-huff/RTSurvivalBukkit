package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import phonis.survival.Survival;

public class BlockEvent implements Listener {
    private final Survival survival;

    public BlockEvent(Survival survival) {
        this.survival = survival;

        Bukkit.getServer().getPluginManager().registerEvents(this, this.survival);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            BlockState bs = event.getBlock().getState();

            if (bs instanceof CraftChest) {
                this.survival.updateQueue.add(bs.getLocation());
            }
        }
    }
}
