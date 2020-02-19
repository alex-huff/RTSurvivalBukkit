package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.completers.PlayerCompleter;

import java.util.List;

public class CommandInspect extends SubCommand {
    private PlayerCompleter completer;

    public CommandInspect(JavaPlugin plugin) {
        super("inspect", "(Player)");
        SubCommand.registerCommand(plugin, this);
        this.completer = new PlayerCompleter(1);
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return this.completer.onTabComplete(args);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException(CommandException.consoleError);
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        if (args.length == 0) {
            player.sendMessage(this.getCommandString(0));

            return;
        }

        Player playerFind = Bukkit.getServer().getPlayer(args[0]);

        if (playerFind == null) {
            throw new CommandException("Invalid player to inspect");
        }

        Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        inventory.setContents(playerFind.getInventory().getContents());
        inventory.setMaxStackSize(Integer.MAX_VALUE);
        player.openInventory(inventory);
    }
}
