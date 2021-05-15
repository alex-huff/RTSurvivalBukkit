package src.phonis.survival.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandChunkUnload extends SubCommand {
    public CommandChunkUnload() {
        super("unload", "(Radius)");
        this.addAlias("u");
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
        if (args.length == 0) {
            player.sendMessage(this.getCommandString(0));

            return;
        }

        int radius = SubCommand.parseInt(args[0]);
        Chunk chunk = player.getLocation().getChunk();
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int chunkCount = 0;

        if (radius > 100) {
            player.sendMessage(ChatColor.DARK_RED + "Max radius is 100 - phonis.");

            return;
        }

        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                if (world.isChunkForceLoaded(x, z)) {
                    world.setChunkForceLoaded(x, z, false);
                    chunkCount++;
                }
            }
        }

        player.sendMessage(
            ChatColor.WHITE +
                "Removed " + ChatColor.GOLD +
                chunkCount + ChatColor.WHITE +
                " chunks from ChunkLoader."
        );
    }
}
