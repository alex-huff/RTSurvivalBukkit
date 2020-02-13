package src.phonis.survival.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import src.phonis.survival.serializable.Todolist;

import javax.annotation.Nonnull;

public class TodoLister implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
		sender.sendMessage(
			ChatColor.AQUA + "Todo:\n" +
			ChatColor.WHITE + Todolist.getTodoList()
		);
		
		return true;
	}
}
