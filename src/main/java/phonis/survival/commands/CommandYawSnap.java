package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.util.DirectionUtil;

import java.util.List;

public class CommandYawSnap extends SubCommand {
    public CommandYawSnap(JavaPlugin plugin) {
        super("yawsnap");
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
        Location newLoc = player.getLocation();
        String direction = DirectionUtil.getCardinalAbsoluteDirection(player);

        if (direction == null) return;

        switch(direction){
            case "N":
                newLoc.setYaw(180);
                player.sendMessage(ChatColor.WHITE + "Snapping " + ChatColor.AQUA + "north");
                break;
            case "E":
                newLoc.setYaw(-90);
                player.sendMessage(ChatColor.WHITE + "Snapping " + ChatColor.AQUA + "east");
                break;
            case "S":
                newLoc.setYaw(0);
                player.sendMessage(ChatColor.WHITE + "Snapping " + ChatColor.AQUA + "south");
                break;
            case "W":
                newLoc.setYaw(90);
                player.sendMessage(ChatColor.WHITE + "Snapping " + ChatColor.AQUA + "west");
                break;
            default:
                break;
        }

        player.teleport(newLoc);
    }
}
