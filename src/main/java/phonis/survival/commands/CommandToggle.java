package src.phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.Survival;

import java.util.List;

public class CommandToggle extends SubCommand {
    public CommandToggle(Survival plugin) {
        super("toggle");
        SubCommand.registerCommand(plugin, this);
        this.addSubCommand(new CommandToggleKeepInv(plugin));
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
