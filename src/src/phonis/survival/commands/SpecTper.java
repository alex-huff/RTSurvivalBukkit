package src.phonis.survival.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.DirectionUtil;

public class SpecTper implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
                                (double) waypoint.getXPos(),
                                (double) waypoint.getYPos(),
                                (double) waypoint.getZPos()
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
                                        "Waypoint does not exist"
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
