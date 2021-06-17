package phonis.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import phonis.survival.Survival;
import phonis.survival.networking.RTAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class JoinEvent implements Listener {
	public JoinEvent(Survival plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.sendMessage(
            ChatColor.WHITE + "Dynmap URL: http://mc.alexfh.com:25583/" + '\n'
			+ "Test server ip: mctest.alexfh.com"
        );

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(RTAdapter.fromWaypoints());
			oos.flush();
			Bukkit.getScheduler().scheduleSyncDelayedTask(
				Survival.instance,
				() -> player.sendPluginMessage(Survival.instance, "rtsurvival:main", baos.toByteArray()),
				20L
			);
		} catch (IOException e) {
			player.sendMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
