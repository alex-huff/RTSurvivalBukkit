package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import src.phonis.survival.serializable.Waypoint;

public class WaypointPosUpdater implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 4) {
			if (Waypoint.pd.data.containsKey(args[0])) {
				int xPos, yPos, zPos;
				
		        try {
		        	xPos = Integer.parseInt(args[1]);
		        }catch(NumberFormatException e) {
		            sender.sendMessage(ChatColor.RED + "Not a valid number!");
		            
		            return false;
		        }
		        
		        try {
		        	yPos = Integer.parseInt(args[2]);
		        }catch(NumberFormatException e) {
		            sender.sendMessage(ChatColor.RED + "Not a valid number!");
		            
		            return false;
		        }
		        
		        try {
		        	zPos = Integer.parseInt(args[3]);
		        }catch(NumberFormatException e) {
		            sender.sendMessage(ChatColor.RED + "Not a valid number!");
		            
		            return false;
		        }
		        
		        Waypoint wp = Waypoint.pd.data.get(args[0]);
		        wp.updateLocation(xPos, yPos, zPos);
		        
		        sender.sendMessage(
		        	ChatColor.GREEN +
		        	"Position updated to: " +
		        	Integer.toString(xPos) + ", " +
		        	Integer.toString(yPos) + ", " +
		        	Integer.toString(zPos)
		        );
			}else {
				sender.sendMessage(
					ChatColor.RED +
					"Waypoint '" + args[0] + "' is invalid."
				);
			}
			return true;
		}
		
		return false;
	}
}
