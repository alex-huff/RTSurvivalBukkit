package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import phonis.survival.Survival;

public class JoinEvent implements Listener {
	public JoinEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

//		player.sendMessage(
//            ChatColor.WHITE + "/survivalhelp"
//        );
	}
}
