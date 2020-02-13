package src.phonis.survival.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import src.phonis.survival.serializable.Todolist;

import javax.annotation.Nonnull;

public class TodoAdder implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		if (args.length > 0) {
			StringBuilder item = new StringBuilder();
			
			for (int i = 0; i < args.length - 1; i++) {
				item.append(args[i]).append(" ");
			} item.append(args[args.length - 1]);
			
			Todolist.addTodo(item.toString());
			
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
