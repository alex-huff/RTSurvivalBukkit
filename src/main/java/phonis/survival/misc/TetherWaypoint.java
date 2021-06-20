package phonis.survival.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import phonis.survival.serializable.Waypoint;

public class TetherWaypoint implements Tether {

    public final String waypoint;

    public TetherWaypoint(String waypoint) {
        this.waypoint = waypoint;
    }

    @Override
    public Location getLocation() {
        Waypoint waypoint = Waypoint.pd.data.get(this.waypoint);

        return waypoint != null ?
            new Location(
                Bukkit.getWorld(
                    waypoint.getWorld()
                ),
                waypoint.getXPos(),
                waypoint.getYPos(),
                waypoint.getZPos()
            ) : null;
    }

}
