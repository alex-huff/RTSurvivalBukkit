package phonis.survival.networking;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import phonis.survival.misc.Tether;
import phonis.survival.misc.TetherPlayer;
import phonis.survival.misc.TetherWaypoint;
import phonis.survival.serializable.Waypoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RTAdapter {

    public static RTWaypoint fromWaypoint(Waypoint waypoint) {
        return new RTWaypoint(waypoint.getName(), waypoint.getWorld(), RTAdapter.fromWorld(Bukkit.getWorld(waypoint.getWorld())), waypoint.getXPos(), waypoint.getYPos(), waypoint.getZPos());
    }

    public static RTDimension fromWorld(World world) {
        return world == null ? RTDimension.OTHER : RTAdapter.fromEnvironment(world.getEnvironment());
    }

    public static RTDimension fromEnvironment(World.Environment environment) {
        if (environment.equals(World.Environment.NORMAL)) {
            return RTDimension.OVERWORLD;
        } else if (environment.equals(World.Environment.NETHER)) {
            return RTDimension.NETHER;
        } else if (environment.equals(World.Environment.THE_END)) {
            return RTDimension.END;
        } else {
            return RTDimension.OTHER;
        }
    }

    public static RTWaypointInitialize fromWaypoints() {
        Collection<Waypoint> currentWaypoints = Waypoint.pd.data.values();
        List<RTWaypoint> waypoints = new ArrayList<>(currentWaypoints.size());

        for (Waypoint waypoint : currentWaypoints) {
            waypoints.add(RTAdapter.fromWaypoint(waypoint));
        }

        return new RTWaypointInitialize(waypoints);
    }

    public static RTTether fromTether(Tether tether) {
        if (tether instanceof TetherWaypoint) {
            TetherWaypoint tetherWaypoint = (TetherWaypoint) tether;
            Waypoint waypoint = Waypoint.pd.data.get(tetherWaypoint.waypoint);

            if (waypoint == null)
                return new RTTetherWaypoint(
                    tetherWaypoint.waypoint,
                    RTDimension.OTHER,
                    -1,
                    -1,
                    -1
                );

            return new RTTetherWaypoint(
                waypoint.getName(),
                RTAdapter.fromWorld(Bukkit.getWorld(waypoint.getWorld())),
                waypoint.getXPos(),
                waypoint.getYPos(),
                waypoint.getZPos()
            );
        } else if (tether instanceof TetherPlayer) {
            TetherPlayer tetherPlayer = (TetherPlayer) tether;
            Player player = Bukkit.getPlayer(tetherPlayer.uuid);

            if (player == null)
                return new RTTetherPlayer(
                    tetherPlayer.uuid,
                    RTDimension.OTHER,
                    -1,
                    -1,
                    -1
                );

            return new RTTetherPlayer(
                player.getUniqueId(),
                RTAdapter.fromWorld(player.getWorld()),
                player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ()
            );
        }

        return null;
    }

}
