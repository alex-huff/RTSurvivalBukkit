package phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class CommandChunkClear extends SubCommand {
    public CommandChunkClear() {
        super("clear");
        this.addAlias("c");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(
            ChatColor.WHITE +
                "Clearing all forceloaded chunks"
        );

        for (World world : getServer().getWorlds()) {
            for (Chunk chunk : world.getForceLoadedChunks()) {
                world.setChunkForceLoaded(chunk.getX(), chunk.getZ(), false);
            }
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
