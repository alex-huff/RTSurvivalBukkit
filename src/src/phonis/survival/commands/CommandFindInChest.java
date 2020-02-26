package src.phonis.survival.commands;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.completers.ItemTabCompleter;
import src.phonis.survival.misc.ChestFindLocation;
import src.phonis.survival.util.DirectionUtil;

import java.util.ArrayList;
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
        int amount = 0;
        int radius = 8;
        List<ChestFindLocation> cflList = new ArrayList<>();

        for (int i = -1 * radius; i <= radius; i++) {
            for (int j = -1 * radius; j <= radius; j++) {
                Chunk chunk = world.getChunkAt(chunkX + i, chunkZ + j);

                for (BlockState bs : chunk.getTileEntities()) {
                    if (bs instanceof CraftChest) {
                        Location location = bs.getLocation();
                        double distance = playerLoc.distance(location);
                        CraftChest cc = (CraftChest) bs;
                        ChestFindLocation cfl = null;

                        for (ItemStack is : cc.getBlockInventory().getContents()) {
                            if (is != null && is.getType() == mat) {
                                if (cfl == null) {
                                    cfl = new ChestFindLocation(location, 0);
                                }

                                amount += is.getAmount();
                                cfl.addAmount(is.getAmount());
                            }
                        }

                        if (cfl != null) {
                            cflList.add(cfl);
                        }
                    }
                }
            }
        }

        if (!cflList.isEmpty()) {
            player.sendMessage(
                ChatColor.GOLD + "" + amount + " " +
                    ChatColor.AQUA + mat.name() +
                    ChatColor.WHITE + " found in " +
                    ChatColor.GOLD + cflList.size() +
                    ChatColor.WHITE + " chests in " +
                    ChatColor.GOLD + radius +
                    ChatColor.WHITE + " chunk radius"
            );

            double best = Double.MIN_VALUE;
            ChestFindLocation bestCfl;

            for (ChestFindLocation cfl : cflList) {
                double distance = cfl.getLocation().distance(player.getLocation());
                double heuristic = (1.0 / distance) * cfl.getNumItems();

                if (heuristic > best) {
                    best = heuristic;
                    bestCfl = cfl;
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + mat.name() + " not found in " + radius + " chunk radius");
        }
    }
}
