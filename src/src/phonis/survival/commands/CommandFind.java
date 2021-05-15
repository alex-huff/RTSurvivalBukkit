package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.completers.FindCompleter;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.DirectionUtil;

import java.util.List;
import java.util.Objects;

public class CommandFind extends SubCommand {
    private final FindCompleter completer;

    public CommandFind(JavaPlugin plugin) {
        super("find", "(Player or waypoint)");
        SubCommand.registerCommand(plugin, this);
        this.completer = new FindCompleter(1);
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
        StringBuilder message;

        if (playerFind != null) {
            Location location = playerFind.getEyeLocation();

            message = this.getString(player, location, playerFind.getName());
        } else {
            if (!Waypoint.pd.data.containsKey(args[0])) {
                throw new CommandException("Invalid player name or waypoint");
            }

            Waypoint waypoint = Waypoint.pd.data.get(args[0]);
            Location target = new Location(
                Bukkit.getWorld(waypoint.getWorld()),
                waypoint.getXPos(),
                waypoint.getYPos(),
                waypoint.getZPos()
            );

            message = this.getString(player, target, waypoint.getName());
        }

        player.sendMessage(message.toString());
    }

    private StringBuilder getString(Player player, Location target, String name) {
        StringBuilder message = new StringBuilder();

        if (target.getWorld() == player.getWorld()) {
            DirectionUtil.faceDirection(player, target);

            message.append(ChatColor.GOLD).append("(").append((int) player.getLocation().distance(target)).append("m) ");
        } else {
            message.append(ChatColor.RED).append(name).append(" is in a different world then you. ");
        }

        message.append(ChatColor.AQUA).append(name).append(ChatColor.WHITE).append(" ➤ ").append(ChatColor.GRAY).append(Objects.requireNonNull(target.getWorld()).getName()).append(" ").append(target.getBlockX()).append(" ").append(target.getBlockY()).append(" ").append(target.getBlockZ());

        return message;
    }
}
