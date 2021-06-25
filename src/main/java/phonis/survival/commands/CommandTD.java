package phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.misc.DeathLocation;
import phonis.survival.misc.Tether;
import phonis.survival.misc.TetherDeathPoint;
import phonis.survival.misc.TetherSession;
import phonis.survival.networking.*;

import java.util.List;

public class CommandTD extends SubCommand {

    private final Survival survival;

    public CommandTD(Survival survival) {
        super("td");

        this.survival = survival;

        SubCommand.registerCommand(this.survival, this);
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
    public void execute(Player player, String[] args) throws CommandException {
        TetherSession tetherSession = this.survival.tetherSessionMap.get(player.getUniqueId());
        DeathLocation deathLocation = this.survival.lastDeathMap.get(player.getUniqueId());

        if (deathLocation == null) {
            player.sendMessage("You have not died yet");

            return;
        }

        Tether target = new TetherDeathPoint(deathLocation);

        if (tetherSession == null) {
            this.survival.tetherSessionMap.put(player.getUniqueId(), new TetherSession(target));
        } else {
            tetherSession.add(target);
        }

        player.sendMessage("Do /tether clear to remove tether");
        RTManager.sendToPlayerIfSubscribed(player, new RTTetherUpdate(RTAdapter.fromTether(target)));
    }

}
