package phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.networking.V1.RTAdapter;
import phonis.survival.networking.RTManager;
import phonis.survival.networking.V1.RTChestFindSessionClear;

import java.util.List;

public class CommandFindInChestClear extends SubCommand {
    Survival survival;

    public CommandFindInChestClear(Survival survival) {
        super("clear");
        this.survival = survival;
        this.addAlias("c");
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
        this.survival.particleMap.put(player.getUniqueId(), null);
        player.sendMessage("Cleared fic session.");
        RTManager.sendToPlayerIfSubscribed(player, new RTChestFindSessionClear());
    }
}
