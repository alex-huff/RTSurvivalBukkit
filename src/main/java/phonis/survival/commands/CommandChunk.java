package src.phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandChunk extends SubCommand {
    public CommandChunk(JavaPlugin plugin) {
        super("force");
        SubCommand.registerCommand(plugin, this);
        this.addSubCommand(new CommandChunkShow());
        this.addSubCommand(new CommandChunkClear());
        this.addSubCommand(new CommandChunkLoad());
        this.addSubCommand(new CommandChunkUnload());
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
