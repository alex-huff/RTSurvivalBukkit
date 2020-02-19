package src.phonis.survival.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandChunkShow extends SubCommand {
    public CommandChunkShow() {
        super("show");
        this.addAlias("s");
    }

    @Override
    public List<String> topTabComplete(String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(
            ChatColor.WHITE +
                "Chunks force loaded" + ChatColor.GRAY + " ➤ "
        );

        for (World world : Bukkit.getServer().getWorlds()) {
            sender.sendMessage(
                ChatColor.WHITE +
                    "World" + ChatColor.GRAY + " ➤ " + ChatColor.AQUA +
                    world.getName()
            );

            for (Chunk chunk : world.getForceLoadedChunks()) {
                sender.sendMessage(
                    ChatColor.GRAY +
                        "X: " +
                        chunk.getX() + " " +
                        "Z: " +
                        chunk.getZ()
                );
            }
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        this.execute((CommandSender) player, args);
    }
}
