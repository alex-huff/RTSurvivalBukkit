package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.phonis.survival.Survival;
import src.phonis.survival.tasks.SleepTask;

import java.util.List;

public class CommandSleep extends SubCommand {
    private Survival plugin;

    public CommandSleep(Survival plugin) {
        super("sleep");
        this.plugin = plugin;
        SubCommand.registerCommand(this.plugin, this);
        this.addSubCommand(new CommandSleepDeny(this.plugin));
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
        if (this.plugin.sleeper == null || this.plugin.sleeper.isCancelled()) {
            if (player.isSleeping()) {
                this.plugin.sleeper = new SleepTask(player.getWorld()).runTaskLater(
                    this.plugin,
                    200
                );

                Bukkit.getServer().broadcastMessage(
                    ChatColor.WHITE +
                        "SLEEP VOTE FOR WORLD" + ChatColor.GRAY + " ➤ " + ChatColor.GOLD +
                        player.getWorld().getName() + ChatColor.WHITE +
                        "\n" +
                        "Sleep will happen in 10 seconds if not denied\n" +
                        "/sleepdeny to cancel worldwide sleep"
                );
            } else {
                player.sendMessage(
                    ChatColor.RED +
                        "You must be sleeping to use this command"
                );
            }
        } else {
            player.sendMessage(
                ChatColor.RED +
                    "Sleep survey already running"
            );
        }
    }
}
