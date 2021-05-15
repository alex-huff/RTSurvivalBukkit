package phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class CommandBrodLoc extends SubCommand {
    public CommandBrodLoc(JavaPlugin plugin) {
        super("bl");
        SubCommand.registerCommand(plugin, this);
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException(CommandException.consoleError);
    }

    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();

        Bukkit.broadcastMessage(
            ChatColor.GOLD +
                player.getName() + ChatColor.WHITE + " ➤ " + ChatColor.GRAY +
                Objects.requireNonNull(location.getWorld()).getName() + " " +
                location.getBlockX() + " " +
                location.getBlockY() + " " +
                location.getBlockZ()
        );
    }
}
