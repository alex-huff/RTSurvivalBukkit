package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;

import javax.annotation.Nonnull;

/**
 * CommandExecutor for handling the /spectp (Player or Waypoint) command
 */
public class SpecTper implements CommandExecutor {
    /**
     * Method implemented from CommandExecutor interface
     * @param sender CommandSender object
     * @param cmd Command object
     * @param label String representing label
     * @param args String[] containing command arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if (args.length > 0) {
            Player player = (Player) sender;

            if (player.getGameMode() == GameMode.SPECTATOR) {
                Player playerFind = Bukkit.getServer().getPlayer(args[0]);

                if (playerFind != null) {
                    player.teleport(playerFind);

                    sender.sendMessage(
                        ChatColor.GREEN +
                            "Teleporting to " +
                            playerFind.getName()
                    );
                }else {
                    if (Waypoint.pd.data.containsKey(args[0])) {
                        Waypoint waypoint = Waypoint.pd.data.get(args[0]);

                        Location target = new Location(
                            Bukkit.getWorld(waypoint.getWorld()),
                            waypoint.getXPos(),
                            waypoint.getYPos(),
                            waypoint.getZPos()
                        );

                        player.teleport(target);

                        sender.sendMessage(
                            ChatColor.GREEN +
                                "Teleporting to waypoint " +
                                waypoint.getName()
                        );
                    }else {
                        sender.sendMessage(
                            ChatColor.RED +
                                "Waypoint or player '" + args[0] + "' does not exist"
                        );
                    }
                }
            }else{
                sender.sendMessage(
                    ChatColor.RED +
                        "You must be in spectator mode to use this command"
                );
            }

            return true;
        }

        return false;
    }
}
