package phonis.survival.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class DeathLocation {

    private final UUID world;
    private final double x;
    private final double y;
    private final double z;

    public DeathLocation(Location location) {
        this.world = location.getWorld().getUID();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
    }

}
