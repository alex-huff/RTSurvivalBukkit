package src.phonis.survival.commands;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.completers.ItemTabCompleter;
import src.phonis.survival.util.DirectionUtil;

import java.util.List;

public class CommandFindInChest extends SubCommand {
    private ItemTabCompleter completer;

    public CommandFindInChest(JavaPlugin plugin) {
        super("findinchest", "(Material)");
        SubCommand.registerCommand(plugin, this);
        this.completer = new ItemTabCompleter(1);
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

        Material mat = Material.matchMaterial(args[0]);

        if (mat == null) {
            throw new CommandException("Invalid material to find");
        }

        Location playerLoc = player.getLocation();
        World world = player.getWorld();
        Chunk pChunk = playerLoc.getChunk();
        int chunkX = pChunk.getX();
        int chunkZ = pChunk.getZ();
        double closest = Double.MAX_VALUE;
        Location closestLoc = null;

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                Chunk chunk = world.getChunkAt(chunkX + i, chunkZ + j);

                for (BlockState bs : chunk.getTileEntities()) {
                    if (bs instanceof CraftChest) {
                        Location location = bs.getLocation();
                        double distance = playerLoc.distance(location);

                        if (distance > closest) {
                            continue;
                        }

                        CraftChest cc = (CraftChest) bs;

                        for (ItemStack is : cc.getBlockInventory().getContents()) {
                            if (is != null && is.getType() == mat) {
                                closestLoc = location;
                                closest = distance;

                                break;
                            }
                        }
                    }
                }
            }
        }

        if (closestLoc != null) {
            DirectionUtil.faceDirection(player, closestLoc.clone().add(.5, .5, .5));
        } else {
            player.sendMessage(ChatColor.RED + mat.name() + " not found in 2 chunk radius");
        }
    }
}
