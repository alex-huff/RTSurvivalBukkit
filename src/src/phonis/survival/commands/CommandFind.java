package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.DirectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandFind extends SubCommand {
    public CommandFind(JavaPlugin plugin) {
        super("find");
        SubCommand.registerCommand(plugin, this);
        this.args.add("(Player or waypoint)");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        List<String> ret = new ArrayList<>();

        if (args.length == 1) {
            ret.addAll(Waypoint.getAutoComplete(args[0]));

            for (World world : Bukkit.getServer().getWorlds()) {
                for (Player player : world.getPlayers()) {
                    String name = player.getName();

                    if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
                        ret.add(name);
                    }
                }
            }
        }

        return ret;
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
