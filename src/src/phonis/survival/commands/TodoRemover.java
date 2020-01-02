package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import src.phonis.survival.serializable.Todolist;

public class TodoRemover implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
    	    int num = 0;
    	    
	        try {
	            num = Integer.parseInt(args[0]);
	        }catch(NumberFormatException e) {
	            sender.sendMessage(ChatColor.RED + "Not a valid number!");
	            
	            return false;
	        }
	        
	        if (Todolist.gd.data.size() >= num && num > 0) {
		        String removedString = Todolist.gd.data.get(num - 1).getItem();
		        
				Todolist.gd.data.remove(num - 1);
				
				Bukkit.broadcastMessage(
					ChatColor.DARK_GREEN +
					sender.getName() +
					" removed " +
					"'" +
					removedString +
					"' from the todo list"
					
				);
	        } else {
	        	sender.sendMessage(
	        		ChatColor.RED +
	        		"Out of bounds"
	        	);
	        }
			
			return true;
		}
		
		return false;
	}
}
