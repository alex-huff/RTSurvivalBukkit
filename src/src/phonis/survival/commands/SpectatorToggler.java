package src.phonis.survival.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import src.phonis.survival.serializable.SpectatorLocation;

public class SpectatorToggler implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (player.getGameMode() != GameMode.SPECTATOR) {
			SpectatorLocation.get(player).updateSpectatorLocation(player.getLocation());
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("You are now spectating");
		}else {	
			player.teleport(SpectatorLocation.get(player).getLocation().add(0, .5, 0));
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("You are no longer spectating");
		}
		
		return true;
	}
}
