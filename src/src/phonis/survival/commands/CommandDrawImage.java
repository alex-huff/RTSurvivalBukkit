package src.phonis.survival.commands;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.FuzzyBlockState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.util.DitherColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CommandDrawImage extends SubCommand {
    private int[][] palette = new int[][]{
//        {Material.WHITE_TERRACOTTA.ordinal(), 210, 178, 161},
//        {Material.ORANGE_TERRACOTTA.ordinal(), 162, 84, 38},
//        {Material.MAGENTA_TERRACOTTA.ordinal(), 150, 88, 109},
//        {Material.LIGHT_BLUE_TERRACOTTA.ordinal(), 113, 109, 138},
//        {Material.YELLOW_TERRACOTTA.ordinal(), 186, 133, 35},
//        {Material.LIME_TERRACOTTA.ordinal(), 104, 118, 53},
//        {Material.PINK_TERRACOTTA.ordinal(), 162, 78, 79},
//        {Material.GRAY_TERRACOTTA.ordinal(), 58, 42, 36},
//        {Material.LIGHT_GRAY_TERRACOTTA.ordinal(), 135, 107, 98},
//        {Material.CYAN_TERRACOTTA.ordinal(), 87, 91, 91},
//        {Material.PURPLE_TERRACOTTA.ordinal(), 118, 70, 86},
//        {Material.BLUE_TERRACOTTA.ordinal(), 74, 60, 91},
//        {Material.BROWN_TERRACOTTA.ordinal(), 77, 51, 36},
//        {Material.GREEN_TERRACOTTA.ordinal(), 76, 83, 42},
//        {Material.RED_TERRACOTTA.ordinal(), 143, 61, 47},
//        {Material.BLACK_TERRACOTTA.ordinal(), 37, 23, 16},
//        {Material.TERRACOTTA.ordinal(), 152, 94, 68},
        {Material.WHITE_WOOL.ordinal(), 234, 236, 237},
        {Material.ORANGE_WOOL.ordinal(), 241, 118, 20},
        {Material.MAGENTA_WOOL.ordinal(), 190, 69, 180},
        {Material.LIGHT_BLUE_WOOL.ordinal(), 58, 175, 217},
        {Material.YELLOW_WOOL.ordinal(), 249, 198, 40},
        {Material.LIME_WOOL.ordinal(), 112, 185, 26},
        {Material.PINK_WOOL.ordinal(), 238, 141, 172},
        {Material.GRAY_WOOL.ordinal(), 63, 68, 72},
        {Material.LIGHT_GRAY_WOOL.ordinal(), 142, 142, 135},
        {Material.CYAN_WOOL.ordinal(), 21, 138, 145},
        {Material.PURPLE_WOOL.ordinal(), 122, 42, 173},
        {Material.BLUE_WOOL.ordinal(), 53, 57, 157},
        {Material.BROWN_WOOL.ordinal(), 114, 72, 41},
        {Material.GREEN_WOOL.ordinal(), 85, 110, 28},
        {Material.RED_WOOL.ordinal(), 161, 39, 35},
        {Material.BLACK_WOOL.ordinal(), 21, 21, 26},
//        {Material.WHITE_CONCRETE.ordinal(), 207, 213, 214},
//        {Material.ORANGE_CONCRETE.ordinal(), 224, 97, 1},
//        {Material.MAGENTA_CONCRETE.ordinal(), 169, 48, 159},
//        {Material.LIGHT_BLUE_CONCRETE.ordinal(), 36, 137, 199},
//        {Material.YELLOW_CONCRETE.ordinal(), 241, 175, 21},
//        {Material.LIME_CONCRETE.ordinal(), 94, 169, 24},
//        {Material.PINK_CONCRETE.ordinal(), 214, 101, 143},
//        {Material.GRAY_CONCRETE.ordinal(), 55, 58, 62},
//        {Material.LIGHT_GRAY_CONCRETE.ordinal(), 125, 125, 115},
//        {Material.CYAN_CONCRETE.ordinal(), 21, 119, 136},
//        {Material.PURPLE_CONCRETE.ordinal(), 100, 32, 156},
//        {Material.BLUE_CONCRETE.ordinal(), 45, 47, 143},
//        {Material.BROWN_CONCRETE.ordinal(), 96, 60, 32},
//        {Material.GREEN_CONCRETE.ordinal(), 73, 91, 36},
//        {Material.RED_CONCRETE.ordinal(), 142, 33, 33},
//        {Material.BLACK_CONCRETE.ordinal(), 8, 10, 15}
    };

    public CommandDrawImage(JavaPlugin plugin) {
        super("drawimage", "(file path) (width) (height)");
        SubCommand.registerCommand(plugin, this);
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
//        double xOff = 18.5;
//        double yOff = 125.5;
//        double zOff = 1197.5;
//        double a = 0.93969;
//        double b = 0.34202;
//
//        for (int r = 64; r <= 95; r += 3) {
//            Set<Vector> vecSet = new HashSet<>();
//
//            for (double i = 0; i <= Math.PI*2; i += .0001) {
//                double x = xOff + r*(Math.cos(i)*a);
//                double y = yOff + r*(Math.cos(i)*b);
//                double z = zOff + r*(Math.sin(i));
//
//                vecSet.add(new Vector(Math.floor(x), Math.floor(y), Math.floor(z)));
//            }
//
//            Material mat;
//
//            if (r % 2 == 0) {
//                mat = Material.SEA_LANTERN;
//            }
//            else {
//                mat = Material.SMOOTH_QUARTZ;
//            }
//
//            for (Vector vec : vecSet) {
//                Block block = player.getWorld().getBlockAt((int) vec.getX(), (int) vec.getY(), (int) vec.getZ());
//                block.setType(mat);
//            }
//        }

//        for (double i = 0; i < Math.PI*2; i += .001) {
//            double x = xOff + r*(Math.cos(i));
//            double z = zOff + r*(Math.sin(i)*t);
//            double y = yOff + r*(Math.sin(i)*t);
//
//            vecSet.add(new Vector(Math.floor(x), Math.floor(y), Math.floor(z)));
//        }

        if (args.length > 2) {
            BufferedImage pic;

            try {
                pic = ImageIO.read(new File(args[0]));
            } catch (IOException e) {
                throw new CommandException("IOException reading image");
            }

            int width = SubCommand.parseInt(args[1]);
            int height = SubCommand.parseInt(args[2]);

            BufferedImage resized = new BufferedImage(width, height, pic.getType());
            Graphics2D gr = resized.createGraphics();
            gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gr.drawImage(pic, 0, 0, width, height, 0, 0, pic.getWidth(),
                pic.getHeight(), null);
            gr.dispose();
            // int rgb = (((((a << 8) + r) << 8) + g) << 8) + b;

            DitherColor[][] imageArray = new DitherColor[width][height];
            int[][] dithered = new int[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int p = resized.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    imageArray[x][y] = new DitherColor(r, g, b);
                }
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    DitherColor current = imageArray[x][y];
                    int best = this.getClosest(current);
                    dithered[x][y] = best;
                    DitherColor bestColor = new DitherColor(
                        this.palette[best][1],
                        this.palette[best][2],
                        this.palette[best][3]
                    );
                    double[] error = this.getDifference(current, bestColor);

                    if (x + 1 < width) {
                        imageArray[x + 1][y].addR(error[0] * 7.0 / 16);
                        imageArray[x + 1][y].addG(error[1] * 7.0 / 16);
                        imageArray[x + 1][y].addB(error[2] * 7.0 / 16);
                    }

                    if (y + 1 < height) {
                        if (x - 1 > 0) {
                            imageArray[x - 1][y + 1].addR(error[0] * 3.0 / 16);
                            imageArray[x - 1][y + 1].addG(error[1] * 3.0 / 16);
                            imageArray[x - 1][y + 1].addB(error[2] * 3.0 / 16);
                        }

                        imageArray[x][y + 1].addR(error[0] * 5.0 / 16);
                        imageArray[x][y + 1].addG(error[1] * 5.0 / 16);
                        imageArray[x][y + 1].addB(error[2] * 5.0 / 16);

                        if (x + 1 < width) {
                            imageArray[x + 1][y + 1].addR(error[0] * 1.0 / 16);
                            imageArray[x + 1][y + 1].addG(error[1] * 1.0 / 16);
                            imageArray[x + 1][y + 1].addB(error[2] * 1.0 / 16);
                        }
                    }
                }
            }

            Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");

            if (plugin instanceof WorldEditPlugin) {
                Material[] values = Material.values();
                WorldEditPlugin wep = (WorldEditPlugin) plugin;
                LocalSession session = wep.getSession(player);
                CuboidRegion region = new CuboidRegion(BlockVector3.at(0, 0, 0), BlockVector3.at(width - 1, 0, height - 1));
                BlockArrayClipboard bac = new BlockArrayClipboard(region);
                // (height - 1) - y
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        try {
                            bac.setBlock(
                                BlockVector3.at(
                                    x,
                                    0,
                                    y
                                ),
                                FuzzyBlockState.builder().type(
                                    Objects.requireNonNull(
                                        BukkitAdapter.asBlockType(
                                            values[this.palette[dithered[x][y]][0]]
                                        )
                                    )
                                ).build()
                            );
                        } catch (WorldEditException e) {
                            player.sendMessage("WorldEdit exception");

                            return;
                        }
                    }
                }

                session.setClipboard(new ClipboardHolder(bac));
                player.sendMessage("Image copied to clipboard");
            } else {
                player.sendMessage("No world edit found");
            }
        } else {
            player.sendMessage(this.getCommandString(0));
        }
    }

    private int getClosest(DitherColor color) {
        double closest = Double.MAX_VALUE;
        int best = 0;

        for (int i = 0; i < this.palette.length; i++) {
            double heuristic =
                Math.pow(color.getR() - this.palette[i][1], 2) +
                    Math.pow(color.getG() - this.palette[i][2], 2) +
                    Math.pow(color.getB() - this.palette[i][3], 2);

            if (heuristic < closest) {
                closest = heuristic;
                best = i;
            }
        }

        return best;
    }

    private double[] getDifference(DitherColor one, DitherColor two) {
        return new double[]{
            one.getR() - two.getR(),
            one.getG() - two.getG(),
            one.getB() - two.getB()
        };
    }
}
