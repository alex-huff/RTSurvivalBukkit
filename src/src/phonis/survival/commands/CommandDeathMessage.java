package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.completers.PlayerCompleter;
import src.phonis.survival.serializable.DeathMessage;

import java.util.List;
import java.util.UUID;

public class CommandDeathMessage extends SubCommand {
    private PlayerCompleter completer;

    public CommandDeathMessage(JavaPlugin plugin) {
        super("setdeathmessage", "(Name or UUID) (Message)");
        SubCommand.registerCommand(plugin, this);
        this.completer = new PlayerCompleter(1);
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return this.completer.onTabComplete(args);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            sender.sendMessage(this.getCommandString(0));

            return;
        }

        if (!sender.isOp()) {
            sender.sendMessage(
                ChatColor.RED +
                    "You must be an operator to use this command"
            );

            return;
        }

        Player playerFind = Bukkit.getServer().getPlayer(args[0]);
        StringBuilder dm = new StringBuilder();
        String message;

        for (int i = 1; i < args.length - 1; i++) {
            dm.append(args[i]).append(" ");
        } dm.append(args[args.length - 1]);

        if (playerFind != null) {
            DeathMessage.update(
                    playerFind.getUniqueId(),
                    new DeathMessage(
                            playerFind.getName(),
                            dm.toString()
                    )
            );

            message = this.getString(playerFind.getName(), dm.toString());
        } else {
            UUID playerUUID;

            try {
                playerUUID = UUID.fromString(args[0]);
            } catch (IllegalArgumentException e) {
                throw new CommandException("Invalid player or UUID");
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

            message = this.getString(oName, dm.toString());
        }

        sender.sendMessage(message);
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        this.execute((CommandSender) player, args);
    }

    private String getString(String name, String message) {
        return ChatColor.WHITE + "Set " + ChatColor.GOLD + name + ChatColor.WHITE + "'s death message to '" + ChatColor.AQUA + message + ChatColor.WHITE + "'";
    }
}
