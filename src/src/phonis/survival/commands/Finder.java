package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.DirectionUtil;

public class Finder implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			Player player = (Player) sender;
			Player playerFind = Bukkit.getServer().getPlayer(args[0]);
			
			if (playerFind != null) {
				Location location = playerFind.getEyeLocation();
				
				if (location.getWorld() == player.getWorld()) {
					DirectionUtil.faceDirection(player, location);
					
					sender.sendMessage(
						ChatColor.YELLOW +
						"(" + ((int) player.getLocation().distance(location)) + "m)"
					);
				}else {
					sender.sendMessage(
						ChatColor.RED +
						playerFind.getName() + " is in a different world then you."
					);
				}
				
				sender.sendMessage(
					ChatColor.GREEN +
					playerFind.getName() + " is at: " +
					location.getWorld().getName() + " " +
					location.getBlockX() + " " +
					location.getBlockY() + " " +
					location.getBlockZ()
				);
			}else {
				if (Waypoint.pd.data.containsKey(args[0])) {
					Waypoint waypoint = Waypoint.pd.data.get(args[0]);
					World world = Bukkit.getWorld(waypoint.getWorld());
					
					if (world == player.getWorld()) {
						Location target = new Location(
							world, 
							(double) waypoint.getXPos(), 
							(double) waypoint.getYPos(),
							(double) waypoint.getZPos()
						);
						
						DirectionUtil.faceDirection(player, target);
						
						sender.sendMessage(
							ChatColor.YELLOW +
							"(" + ((int) player.getLocation().distance(target)) + "m)"
						);
					}else {
						sender.sendMessage(
							ChatColor.RED +
							args[0] + " is in a different world then you."
						);
					}
					
					sender.sendMessage(
						ChatColor.GREEN +
						args[0] + " is at: " +
						world.getName() + " " +
						waypoint.getXPos() + " " +
						waypoint.getYPos() + " " +
						waypoint.getZPos()
					);
				}else {
					sender.sendMessage(
						ChatColor.RED +
						"Invalid name or waypoint."
					);
				}
			}

			return true;
		}
		
		return false;
	}
}