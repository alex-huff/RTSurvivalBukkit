package phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.serializable.Todolist;

import java.util.List;

public class CommandTodoAdd extends SubCommand {
    public CommandTodoAdd() {
        super("add", "(Todo item)");
        this.addAlias("a");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(this.getCommandString(0));

            return;
        }

        StringBuilder item = new StringBuilder();

        for (int i = 0; i < args.length - 1; i++) {
            item.append(args[i]).append(" ");
        }
        item.append(args[args.length - 1]);

        Todolist.addTodo(item.toString());

        Bukkit.broadcastMessage(
            ChatColor.GOLD +
                sender.getName() + ChatColor.WHITE +
                " added '" + ChatColor.AQUA +
                item + ChatColor.WHITE +
                "' to the todo list"
        );
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
