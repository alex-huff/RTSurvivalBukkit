package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import src.phonis.survival.Survival;

import javax.annotation.Nonnull;

public class ToggleKeepInventory implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        Survival.keepInventory = !Survival.keepInventory;

        Bukkit.broadcastMessage("Keep inventory is now: " + Survival.keepInventory);

        return true;
    }
}
