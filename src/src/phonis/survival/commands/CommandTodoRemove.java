package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Todolist;

import java.util.List;

public class CommandTodoRemove extends SubCommand {
    public CommandTodoRemove() {
        super("remove", "(Index)");
        this.addAlias("r");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(this.getCommandString(0));
        }

        int num = SubCommand.parseInt(args[0]);

        if (!(Todolist.gd.data.size() >= num && num > 0)) {
            throw new CommandException("Index out of bounds");
        }

        String removedString = Todolist.gd.data.get(num - 1).getItem();

        Todolist.gd.data.remove(num - 1);

        Bukkit.broadcastMessage(
            ChatColor.GOLD +
                sender.getName() + ChatColor.WHITE +
                " removed " +
                "'" + ChatColor.AQUA +
                removedString + ChatColor.WHITE +
                "' from the todo list"

        );
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        this.execute((CommandSender) player, args);
    }
}
