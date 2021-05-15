package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;

import java.util.List;

public class CommandHelp extends SubCommand {
    private final Survival plugin;

    public CommandHelp(Survival plugin) {
        super("survivalhelp");
        this.plugin = plugin;
        SubCommand.registerCommand(this.plugin, this);
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.WHITE + "Commands" + ChatColor.GRAY + " ➤");

        for (SubCommand sub : this.plugin.commands) {
            sender.sendMessage("\n" + sub.getCommandString(1));
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
