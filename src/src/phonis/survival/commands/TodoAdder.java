package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.serializable.Todolist;

public class TodoAdder implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			String item = "";
			
			for (int i = 0; i < args.length - 1; i++) {
				item += args[i] + " ";
			} item += args[args.length - 1];
			
			Todolist.addTodo(item);
			
			Bukkit.broadcastMessage(
				ChatColor.DARK_GREEN +
				sender.getName() +
				" added '" +
				item +
				"' to the todo list"
			);
			
			return true;
		}
		
		return false;
	}
}
