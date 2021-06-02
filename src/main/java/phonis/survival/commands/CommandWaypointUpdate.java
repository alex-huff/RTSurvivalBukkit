package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.serializable.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class CommandWaypointUpdate extends SubCommand {
    public CommandWaypointUpdate() {
        super("update", "(Waypoint) (X) (Y) (Z)");
        this.addAlias("u");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        if (args.length == 1) {
            return Waypoint.getAutoComplete(args[0]);
        }

        return new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length < 4) {
            sender.sendMessage(this.getCommandString(0));

            return;
        }

        if (!Waypoint.pd.data.containsKey(args[0])) {
            throw new CommandException("Invalid waypoint to update");
        }

        int xPos, yPos, zPos;

        xPos = SubCommand.parseInt(args[1]);
        yPos = SubCommand.parseInt(args[2]);
        zPos = SubCommand.parseInt(args[3]);

        Waypoint.updateWaypoint(args[0], xPos, yPos, zPos);

        sender.sendMessage(
            ChatColor.WHITE +
                "Position of '" + ChatColor.AQUA + args[0] + ChatColor.WHITE + "' ➤ " + ChatColor.GRAY +
                xPos + ", " +
                yPos + ", " +
                zPos
        );
    }

    @Override
    public void execute(Player player, String[] args) throws CommandException {
        this.execute((CommandSender) player, args);
    }
}
