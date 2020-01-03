package src.phonis.survival.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import src.phonis.survival.serializable.SpectatorLocation;

import javax.annotation.Nonnull;

/**
 * CommandExecutor for handling the /spectog command
 */
public class SpectatorToggler implements CommandExecutor {
	/**
	 * Method implemented from CommandExecutor interface
	 * @param sender CommandSender object
	 * @param cmd Command object
	 * @param label String representing label
	 * @param args String[] containing command arguments
	 * @return boolean
	 */
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
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
