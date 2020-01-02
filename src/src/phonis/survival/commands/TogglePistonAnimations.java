package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.packets.RedstoneListener;

public class TogglePistonAnimations implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		RedstoneListener.handlePackets = !RedstoneListener.handlePackets;
		
		Bukkit.broadcastMessage(
			ChatColor.DARK_GREEN + 
			"Piston animations are now: " +
			Boolean.toString(!RedstoneListener.handlePackets)
		);
		
		return true;
	}
}
