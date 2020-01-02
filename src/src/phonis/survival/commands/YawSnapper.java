package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import src.phonis.survival.util.DirectionUtil;

public class YawSnapper implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		Location newLoc = player.getLocation();
		String direction = DirectionUtil.getCardinalAbsoluteDirection(player);
        
        switch(direction){
        	case "N":
        		newLoc.setYaw(180);
        		sender.sendMessage(ChatColor.AQUA + "Snapping north");
        		break;
        	case "E":
        		newLoc.setYaw(-90);
        		sender.sendMessage(ChatColor.AQUA + "Snapping east");
        		break;
        	case "S":
        		newLoc.setYaw(0);
        		sender.sendMessage(ChatColor.AQUA + "Snapping south");
        		break;
        	case "W":
        		newLoc.setYaw(90);
        		sender.sendMessage(ChatColor.AQUA + "Snapping west");
        		break;
        	default:
        		break;
        }
		
        player.teleport(newLoc);
		
		return true;
	}
}
