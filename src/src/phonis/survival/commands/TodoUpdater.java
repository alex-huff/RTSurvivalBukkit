package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import src.phonis.survival.serializable.Todolist;

import javax.annotation.Nonnull;

public class TodoUpdater implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if (args.length > 1) {
            int num;

            try {
                num = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Not a valid number!");

                return false;
            }

            if (!(num >= 1 && num <= Todolist.gd.data.size())) {
                sender.sendMessage(
                    ChatColor.RED +
                        "This is not a valid entry"
                );

                return false;
            }

            num -= 1; //switch to zero offset

            StringBuilder dm = new StringBuilder();

            for (int i = 1; i < args.length - 1; i++) {
                dm.append(args[i]).append(" ");
            }
            dm.append(args[args.length - 1]);

            String updateItem = dm.toString();

            Todolist.gd.data.get(num).setItem(updateItem);

            sender.sendMessage(
                ChatColor.GREEN +
                    "Updated todo item " +
                    (num + 1) +
                    " to, '" + updateItem + "'"
            );

            return true;
        } else {
            sender.sendMessage(
                ChatColor.RED +
                    "Not enough arguments given. Usage: /todoupdate (number) (item)"
            );

            return false;
        }
    }
}
