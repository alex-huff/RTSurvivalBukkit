package phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.completers.FindCompleter;
import phonis.survival.misc.Tether;
import phonis.survival.misc.TetherPlayer;
import phonis.survival.misc.TetherSession;
import phonis.survival.misc.TetherWaypoint;
import phonis.survival.networking.V1.RTAdapter;
import phonis.survival.networking.RTManager;
import phonis.survival.networking.V1.RTTetherUpdate;
import phonis.survival.serializable.Waypoint;
import phonis.survival.util.DirectionUtil;

import java.util.List;
import java.util.Objects;

public class CommandTether extends SubCommand {

    private final Survival survival;
    private final FindCompleter completer;

    public CommandTether(Survival survival) {
        super("tether", "(Player or waypoint) or clear");

        this.survival = survival;
        this.completer = new FindCompleter(1);

        this.addSubCommand(new CommandTetherClear(this.survival));
        SubCommand.registerCommand(this.survival, this);
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

        Tether target;
        Player playerFind = Bukkit.getServer().getPlayer(args[0]);
        StringBuilder message;

        if (playerFind != null) {
            Location location = playerFind.getEyeLocation();
            target = new TetherPlayer(playerFind.getUniqueId());
            message = this.getString(player, location, playerFind.getName());
        } else {
            if (!Waypoint.pd.data.containsKey(args[0])) {
                throw new CommandException("Invalid player name or waypoint");
            }

            Waypoint waypoint = Waypoint.pd.data.get(args[0]);
            target = new TetherWaypoint(waypoint.getName());
            Location targetLoc = new Location(
                Bukkit.getWorld(waypoint.getWorld()),
                waypoint.getXPos(),
                waypoint.getYPos(),
                waypoint.getZPos()
            );
            message = this.getString(player, targetLoc, waypoint.getName());
        }

        player.sendMessage(message.toString());
        player.sendMessage("Do /tether clear to remove tether");

        TetherSession tetherSession = this.survival.tetherSessionMap.get(player.getUniqueId());

        if (tetherSession == null) {
            this.survival.tetherSessionMap.put(player.getUniqueId(), new TetherSession(target));
        } else {
            tetherSession.add(target);
        }

        RTManager.sendToPlayerIfSubscribed(player, new RTTetherUpdate(RTAdapter.fromTether(target)));
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
