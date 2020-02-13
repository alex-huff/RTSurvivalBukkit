package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.util.DirectionUtil;

import javax.annotation.Nonnull;

public class YawSnapper implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		Player player = (Player) sender;
		Location newLoc = player.getLocation();
		String direction = DirectionUtil.getCardinalAbsoluteDirection(player);

		if (direction == null) return false;

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
