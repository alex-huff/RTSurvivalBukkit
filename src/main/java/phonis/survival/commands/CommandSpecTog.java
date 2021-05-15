package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import phonis.survival.serializable.SpectatorLocation;

import java.util.List;

public class CommandSpecTog extends SubCommand {
    public CommandSpecTog(JavaPlugin plugin) {
        super("spectog");
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
        if (player.getGameMode() != GameMode.SPECTATOR) {
            SpectatorLocation.get(player).updateSpectatorLocation(player.getLocation());
            player.setGameMode(GameMode.SPECTATOR);
            player.setFlySpeed(.1f);
            player.sendMessage(ChatColor.WHITE + "You are now spectating");
        } else {
            player.teleport(SpectatorLocation.get(player).getLocation().add(0, .5, 0));
            player.setGameMode(GameMode.SURVIVAL);
            player.setFlySpeed(.1f);
            player.sendMessage(ChatColor.WHITE + "You are no longer spectating");
        }
    }
}
