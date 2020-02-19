package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Todolist;

import java.util.List;

public class CommandTodoUpdate extends SubCommand {
    public CommandTodoUpdate() {
        super("update");
        this.addAlias("u");
        this.addArg("(Index) (Message)");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            sender.sendMessage(this.getCommandString(0));

            return;
        }

        int num = SubCommand.parseInt(args[0]);

        if (!(num >= 1 && num <= Todolist.gd.data.size())) {
            throw new CommandException("Index out of bounds");
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
            ChatColor.WHITE +
                "Updated todo item " + ChatColor.GOLD +
                (num + 1) + ChatColor.WHITE +
                " to, '" + ChatColor.AQUA + updateItem + ChatColor.WHITE + "'"
        );
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        this.execute((CommandSender) player, args);
    }
}
