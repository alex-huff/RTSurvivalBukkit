package phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.completers.FindCompleter;
import phonis.survival.serializable.Waypoint;

import java.util.List;

public class CommandSpecTp extends SubCommand {
    private final FindCompleter completer;

    public CommandSpecTp() {
        super("tp", "(Player or waypoint)");
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

        if (player.getGameMode() == GameMode.SPECTATOR) {
            Player playerFind = Bukkit.getServer().getPlayer(args[0]);
            String message;

            if (playerFind != null) {
                player.teleport(playerFind);

                message = this.getString(playerFind.getName());
            }else {
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

                player.teleport(target);

                message = this.getString(waypoint.getName());
            }

            player.sendMessage(message);
        }else{
            player.sendMessage(
                    ChatColor.RED +
                            "You must be in spectator mode to use this command"
            );
        }
    }

    private String getString(String name) {
        return ChatColor.WHITE +
                "Telecommuting to " + ChatColor.GOLD +
                name;
    }
}
