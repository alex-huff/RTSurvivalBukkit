package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import phonis.survival.Survival;

public class GamemodeEvent implements Listener {
	public GamemodeEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onFermSpiderEyeUse(PlayerInteractEvent event) {
        EquipmentSlot e = event.getHand();
        
        if (e != null && e.equals(EquipmentSlot.HAND)) {
    		Player player = event.getPlayer();
    		
    		if (player.isOp()) {
        		Action action = event.getAction();
        		
        		if (player.getInventory().getItemInMainHand().getType() == Material.FERMENTED_SPIDER_EYE && 
        			(
    	    			action == Action.RIGHT_CLICK_AIR || 
    	    			action == Action.RIGHT_CLICK_BLOCK
        			)
        		) {
        			if (player.getGameMode() == GameMode.SURVIVAL) player.setGameMode(GameMode.CREATIVE);
        			else if (player.getGameMode() == GameMode.CREATIVE) player.setGameMode(GameMode.SURVIVAL);
        			
        			if (event.useItemInHand() == Result.ALLOW) event.setUseItemInHand(Result.DENY);
        			if (event.useInteractedBlock() == Result.ALLOW) event.setUseInteractedBlock(Result.DENY);
        		}
    		}
        }
	}
}
