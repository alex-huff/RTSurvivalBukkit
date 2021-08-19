package phonis.survival.misc;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftChest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChestFindLocation {
    private final Location location;
    private final Location centerLocation;
    private int numItems;

    public ChestFindLocation(Location location, int numItems) {
        this.location = location;
        this.centerLocation = this.location.clone().add(.5, .5, .5);
        this.numItems = numItems;
    }

    public Location getLocation() {
        return this.location;
    }

    public static List<ChestFindLocation> getCfls(Chunk chunk, Material material) {
        List<ChestFindLocation> cflList = new ArrayList<>();

        for (BlockState bs : chunk.getTileEntities()) {
            if (bs instanceof CraftChest cc) {
                Location location = bs.getLocation();
                ChestFindLocation cfl = null;

                for (ItemStack is : cc.getBlockInventory().getContents()) {
                    if (is != null && is.getType() == material) {
                        if (cfl == null) {
                            cfl = new ChestFindLocation(location, 0);
                        }

                        cfl.addAmount(is.getAmount());
                    }
                }

                if (cfl != null) {
                    cflList.add(cfl);
                }
            }
        }

        return cflList;
    }

    public int getNumItems() {
        return this.numItems;
    }

    public void addAmount(int numItems) {
        this.numItems += numItems;
    }

    public Location getCenterLocation() {
        return this.centerLocation;
    }

    public boolean update(Material material) {
        BlockState bs = Objects.requireNonNull(this.location.getWorld()).getBlockAt(this.location).getState();
        int amount = 0;

        if (bs instanceof CraftChest cc) {

            for (ItemStack is : cc.getBlockInventory().getContents()) {
                if (is != null && is.getType() == material) {
                    amount += is.getAmount();
                }
            }

            this.numItems = amount;

            return this.numItems != 0;
        }

        return false;
    }
}
