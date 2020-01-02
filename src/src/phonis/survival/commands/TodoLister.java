package src.phonis.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.serializable.Todolist;

public class TodoLister implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(
			ChatColor.AQUA + "Todo:\n" +
			ChatColor.WHITE + Todolist.getTodoList()
		);
		
		return true;
	}
}
