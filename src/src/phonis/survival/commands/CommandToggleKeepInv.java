package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.Survival;

import java.util.List;

public class CommandToggleKeepInv extends SubCommand {
    private Survival plugin;

    public CommandToggleKeepInv(Survival plugin) {
        super("keepinventory");
        this.plugin = plugin;
        this.addAlias("ki");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.plugin.keepInventory = !this.plugin.keepInventory;

        Bukkit.broadcastMessage(ChatColor.WHITE + "Keep inventory is now: " + ChatColor.AQUA + this.plugin.keepInventory);
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
