package src.phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandTodo extends SubCommand {
    public CommandTodo(JavaPlugin plugin) {
        super("todo");
        SubCommand.registerCommand(plugin, this);
        this.addSubCommand(new CommandTodoAdd());
        this.addSubCommand(new CommandTodoList());
        this.addSubCommand(new CommandTodoRemove());
        this.addSubCommand(new CommandTodoUpdate());
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(this.getCommandString(0));
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
