package src.phonis.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import src.phonis.survival.serializable.Todolist;

import javax.annotation.Nonnull;

/**
 * CommandExecutor that handles the /to-do command
 */
public class TodoLister implements CommandExecutor {
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
		sender.sendMessage(
			ChatColor.AQUA + "Todo:\n" +
			ChatColor.WHITE + Todolist.getTodoList()
		);
		
		return true;
	}
}
