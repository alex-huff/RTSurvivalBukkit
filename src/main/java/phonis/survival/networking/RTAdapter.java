package phonis.survival.networking;

import phonis.survival.serializable.Waypoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RTAdapter {

    public static RTWaypoint fromWaypoint(Waypoint waypoint) {
        return new RTWaypoint(waypoint.getName(), waypoint.getWorld(), waypoint.getXPos(), waypoint.getYPos(), waypoint.getZPos());
    }

    public static RTWaypointInitialize fromWaypoints() {
        Collection<Waypoint> currentWaypoints = Waypoint.pd.data.values();
        List<RTWaypoint> waypoints = new ArrayList<>(currentWaypoints.size());

        for (Waypoint waypoint : currentWaypoints) {
            waypoints.add(RTAdapter.fromWaypoint(waypoint));
        }

        return new RTWaypointInitialize(waypoints);
    }

}
