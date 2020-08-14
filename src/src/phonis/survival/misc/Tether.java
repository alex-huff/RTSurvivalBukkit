package src.phonis.survival.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import src.phonis.survival.serializable.Waypoint;

public class Tether {
    private Object target;

    public Tether(Object target) {
        this.target = target;
    }

    public Location getLocation() {
        if (this.target instanceof Waypoint) {
            Waypoint waypoint = (Waypoint) this.target;

            Location targetLoc = new Location(
                Bukkit.getWorld(waypoint.getWorld()),
                waypoint.getXPos(),
                waypoint.getYPos(),
                waypoint.getZPos()
            );

            return targetLoc;
        } else if (this.target instanceof Player) {
            Player player = (Player) this.target;

            return player.getEyeLocation();
        }

        return null;
    }
}