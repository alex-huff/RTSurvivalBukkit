package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.DeathMessage;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * CommandExecutor that handles the /setdeathmessage command
 */
public class DeathMessageUpdater implements CommandExecutor {
    /**
     * Method implemented from CommandExecutor interface
     *
     * @param sender CommandSender object
     * @param cmd    Command object
     * @param label  String representing label
     * @param args   String[] containing command arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        Player player = (Player) sender;

        if (player.isOp()) {
            if (args.length > 1) {
                Player playerFind = Bukkit.getServer().getPlayer(args[0]);
                StringBuilder dm = new StringBuilder();

                for (int i = 1; i < args.length - 1; i++) {
                    dm.append(args[i]).append(" ");
                }
                dm.append(args[args.length - 1]);

                if (playerFind != null) {
                    DeathMessage.update(
                        playerFind.getUniqueId(),
                        new DeathMessage(
                            playerFind.getName(),
                            dm.toString()
                        )
                    );

                    sender.sendMessage(
                        ChatColor.GREEN +
                            "Set " + playerFind.getName() + "'s death message to '" + dm + "'"
                    );
                } else {
                    UUID playerUUID;

                    try {
                        playerUUID = UUID.fromString(args[0]);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(
                            ChatColor.RED +
                                "Invalid player or UUID '" + args[0] + "'"
                        );

                        return false;
                    }

                    OfflinePlayer oPlayerFind = Bukkit.getOfflinePlayer(playerUUID);

                    DeathMessage.update(
                        oPlayerFind.getUniqueId(),
                        new DeathMessage(
                            oPlayerFind.getName(),
                            dm.toString()
                        )
                    );

                    String oName = oPlayerFind.getName();

                    if (oName == null) {
                        oName = "Unknown Player";
                    }

                    sender.sendMessage(
                        ChatColor.GREEN +
                            "Set " + oName + "'s death message to '" + dm + "'"
                    );
                }
            } else {
                sender.sendMessage(
                    ChatColor.RED +
                        "Not enough arguments given. Usage: /setdeathmessage (name or UUID) (message)"
                );

                return false;
            }
        } else {
            sender.sendMessage(
                ChatColor.RED +
                    "You must be an operator to use this command"
            );

            return false;
        }

        return true;
    }
}
