package phonis.survival.commands;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.completers.ItemTabCompleter;
import phonis.survival.misc.ChestFindLocation;
import phonis.survival.misc.ChestFindSession;
import phonis.survival.misc.ChunkLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandFindInChest extends SubCommand {
    private final Survival survival;
    private final ItemTabCompleter completer;

    public CommandFindInChest(Survival survival) {
        super("findinchest", "(Material)");
        this.survival = survival;
        SubCommand.registerCommand(this.survival, this);
        this.addSubCommand(new CommandFindInChestClear(this.survival));
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
        int numLocations = 0;
        int radius = 8;

        this.survival.particleMap.put(player.getUniqueId(), new ChestFindSession(
                world.getUID(),
                mat,
                new ChunkLocation(pChunk),
                new HashMap<>()
            )
        );

        ChestFindSession cfs = this.survival.particleMap.get(player.getUniqueId());
        Map<ChunkLocation, List<ChestFindLocation>> cflMap = cfs.getCflMap();

        for (int i = -1 * radius; i <= radius; i++) {
            for (int j = -1 * radius; j <= radius; j++) {
                Chunk chunk = world.getChunkAt(chunkX + i, chunkZ + j);
                ChunkLocation chunkLoc = new ChunkLocation(chunk);

                cflMap.put(chunkLoc, ChestFindLocation.getCfls(chunk, mat));
            }
        }

        double best = Double.MIN_VALUE;
        double bestDistance = Double.NaN;
        ChestFindLocation bestCfl = null;

        for (Map.Entry<ChunkLocation, List<ChestFindLocation>> entry : cflMap.entrySet()) {
            List<ChestFindLocation> cflList = entry.getValue();

            for (ChestFindLocation cfl : cflList) {
                amount += cfl.getNumItems();
                numLocations++;
                double distance = playerLoc.distance(cfl.getCenterLocation());
                double heuristic = (1.0 / Math.pow(Math.max(distance, 10.0), 1.5)) * cfl.getNumItems();

                if (heuristic > best) {
                    best = heuristic;
                    bestCfl = cfl;
                    bestDistance = distance;
                }
            }
        }

        if (bestCfl == null) {
            player.sendMessage(ChatColor.RED + mat.name() + " not found in " + radius + " chunk radius");

            return;
        }

        player.sendMessage(
            ChatColor.GOLD + "" + amount + " " +
                ChatColor.AQUA + mat.name() +
                ChatColor.WHITE + " found in " +
                ChatColor.GOLD + numLocations +
                ChatColor.WHITE + " chests in " +
                ChatColor.GOLD + radius +
                ChatColor.WHITE + " chunk radius"
        );

        player.sendMessage(
            ChatColor.WHITE + "Best location calculated to be " + ChatColor.GOLD + bestDistance + ChatColor.WHITE + " blocks away with " + ChatColor.GOLD +
                bestCfl.getNumItems() + ChatColor.AQUA + " " + mat.name() + "\n" +
                ChatColor.RED + "Red: " + ChatColor.WHITE + "Most items\n" +
                ChatColor.BLUE + "Blue: " + ChatColor.WHITE + "Best in terms of distance and number of items\n" +
                ChatColor.YELLOW + "Yellow: " + ChatColor.WHITE + "Closest\n" +
                ChatColor.WHITE + ChatColor.BOLD + "/findinchest clear" + ChatColor.RESET + ChatColor.WHITE + " to clear particles"
        );
    }
}
