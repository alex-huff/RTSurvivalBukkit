package src.phonis.survival.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import src.phonis.survival.Survival;

import javax.annotation.Nonnull;

public class TogglePistonAnimations implements CommandExecutor {
	private Survival plugin;

	public TogglePistonAnimations(Survival plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		this.plugin.getRedstoneListener().toggle();

		Bukkit.broadcastMessage(
			ChatColor.DARK_GREEN +
				"Piston animations are now: " +
				!this.plugin.getRedstoneListener().getHandlePackets()
		);

		return true;
	}
}
