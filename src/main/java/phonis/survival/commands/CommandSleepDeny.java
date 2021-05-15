package phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;

import java.util.List;

public class CommandSleepDeny extends SubCommand {
    private final Survival plugin;

    public CommandSleepDeny(Survival plugin) {
        super("deny");
        this.plugin = plugin;
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (this.plugin.sleeper != null && !this.plugin.sleeper.isCancelled()) {
            this.plugin.sleeper.cancel();

            Bukkit.getServer().broadcastMessage(
                ChatColor.RED +
                    "Sleep cancelled by: " +
                    ChatColor.GOLD +
                    ChatColor.BOLD +
                    sender.getName()
            );
        } else {
            sender.sendMessage(
                ChatColor.RED +
                    "No current sleep vote"
            );
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
