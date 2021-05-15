package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.serializable.Todolist;

import java.util.List;

public class CommandTodoList extends SubCommand {
    public CommandTodoList() {
        super("list");
        this.addAlias("l");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(
            ChatColor.AQUA + "Todo:\n" +
                ChatColor.WHITE + Todolist.getTodoList()
        );
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
