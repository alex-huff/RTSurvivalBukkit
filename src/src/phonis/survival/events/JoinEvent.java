package src.phonis.survival.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import src.phonis.survival.Survival;

public class JoinEvent implements Listener {
	public JoinEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.sendMessage(
			ChatColor.AQUA + "New:\n" +
				ChatColor.WHITE + ChatColor.BOLD + "/gettrades (Point at merchant); " + ChatColor.RESET + ChatColor.AQUA + "Get trades of villager you are looking at" + "\n" + ChatColor.RESET +
				ChatColor.WHITE + ChatColor.BOLD + "/findtrade (Material or enchantment); " + ChatColor.RESET + ChatColor.AQUA + "Point to location of villager with selected trade within 100 block bounding box" + "\n" + ChatColor.RESET +
				ChatColor.WHITE + ChatColor.BOLD + "/findinchest (Material); " + ChatColor.RESET + ChatColor.AQUA + "Find material in chest nearby"
		);
	}
}
