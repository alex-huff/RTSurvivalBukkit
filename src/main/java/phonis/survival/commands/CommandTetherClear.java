package phonis.survival.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;

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
        this.survival.tetherSessionMap.put(player.getUniqueId(), null);
    }
}
