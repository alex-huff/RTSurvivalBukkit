package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.Survival;

import java.util.List;

public class CommandTogglePiston extends SubCommand {
    private Survival plugin;

    public CommandTogglePiston(Survival plugin) {
        super("piston");
        this.plugin = plugin;
        this.addAlias("p");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.plugin.redstoneListener.toggle();

        Bukkit.broadcastMessage(
                ChatColor.WHITE +
                        "Piston animations are now: " + ChatColor.AQUA +
                        !this.plugin.redstoneListener.getHandlePackets()
        );
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
