package src.phonis.survival.misc;

import org.bukkit.Location;

public class ChestFindLocation {
    private Location location;
    private int numItems;

    public ChestFindLocation(Location location, int numItems) {
        this.location = location;
        this.numItems = numItems;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getNumItems() {
        return this.numItems;
    }

    public void addAmount(int numItems) {
        this.numItems += numItems;
    }
}
