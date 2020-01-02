package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import src.phonis.survival.Survival;

public class SleepEvent implements Listener {
	private Survival instance;
	public static boolean sleeping = false;
	
	public SleepEvent(Survival plugin) {
		this.instance = plugin;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent event) {
		if (!SleepEvent.sleeping) {
			SleepEvent.sleeping = true;
			
		    Bukkit.getServer().getScheduler().runTaskLater(this.instance, 
		    	new Runnable() {
		    		public void run() {
		    			SleepEvent.sleeping = false;
		    			
		    			event.getPlayer().getWorld().setTime(0L);
		    			event.getPlayer().getWorld().setStorm(false);
		    			event.getPlayer().getWorld().setThundering(false);
		    		}
		    	}, 
		    	100L
		    );
		}
	}
}
