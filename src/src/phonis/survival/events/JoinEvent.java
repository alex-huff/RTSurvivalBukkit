package src.phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.Survival;

/**
 * Listener that handles PlayerJoinEvent
 */
public class JoinEvent implements Listener {
	/**
	 * JoinEvent constructor that takes in Survival plugin
	 * @param plugin Survival plugin
	 */
	public JoinEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Method decorated by EventHandler that handles PlayerJoinEvent
	 * @param event PlayerJoinEvent
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.sendMessage(
			ChatColor.AQUA + "New:\n" +
				ChatColor.WHITE + ChatColor.BOLD + "/spectog; " + ChatColor.RESET + ChatColor.AQUA + "Use to toggle between spectator mode and survival" + "\n" + ChatColor.RESET +
				ChatColor.WHITE + ChatColor.BOLD + "/todo; " + ChatColor.RESET + ChatColor.AQUA + "View the todo list" + "\n" + ChatColor.RESET +
				ChatColor.WHITE + ChatColor.BOLD + "/todoadd (item); " + ChatColor.RESET + ChatColor.AQUA + "Add an item to the todo list" + "\n" + ChatColor.RESET +
				ChatColor.WHITE + ChatColor.BOLD + "/todoremove (number); " + ChatColor.RESET + ChatColor.AQUA + "Remove an item from the todo list" + "\n" + ChatColor.RESET +
				ChatColor.WHITE + ChatColor.BOLD + "/spectp (Player or Waypoint); " + ChatColor.RESET + ChatColor.AQUA + "Teleport to place while in spectator mode"
		);
	}
}
