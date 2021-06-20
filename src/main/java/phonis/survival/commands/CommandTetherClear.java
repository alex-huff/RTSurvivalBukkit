package phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.misc.Tether;
import phonis.survival.misc.TetherSession;
import phonis.survival.networking.RTAdapter;
import phonis.survival.networking.RTManager;
import phonis.survival.networking.RTTetherRemove;

import java.util.List;

public class CommandTetherClear extends SubCommand {
    private final Survival survival;

    public CommandTetherClear(Survival survival) {
        super("clear");

        this.survival = survival;
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
        TetherSession session = this.survival.tetherSessionMap.get(player.getUniqueId());

        if (session == null) return;

        for (Tether tether : session.tethers) {
            RTManager.sendToPlayerIfSubscribed(player, new RTTetherRemove(RTAdapter.fromTether(tether)));
        }

        this.survival.tetherSessionMap.put(player.getUniqueId(), null);
    }
}
