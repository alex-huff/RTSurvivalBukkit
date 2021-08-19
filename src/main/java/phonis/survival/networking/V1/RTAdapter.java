package phonis.survival.networking.V1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import phonis.survival.misc.*;
import phonis.survival.serializable.Waypoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RTAdapter {

    public static RTWaypoint fromWaypoint(Waypoint waypoint) {
        return new RTWaypoint(
            waypoint.getName(),
            new RTLocation(
                RTAdapter.fromWorld(Bukkit.getWorld(waypoint.getWorld())),
                waypoint.getXPos(),
                waypoint.getYPos(),
                waypoint.getZPos()
            )
        );
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
        if (tether instanceof TetherWaypoint tetherWaypoint) {
            Waypoint waypoint = Waypoint.pd.data.get(tetherWaypoint.waypoint);

            if (waypoint == null)
                return new RTTetherWaypoint(
                    tetherWaypoint.waypoint,
                    new RTLocation(
                        RTDimension.OTHER,
                        -1,
                        -1,
                        -1
                    )
                );

            return new RTTetherWaypoint(
                waypoint.getName(),
                new RTLocation(
                    RTAdapter.fromWorld(Bukkit.getWorld(waypoint.getWorld())),
                    waypoint.getXPos(),
                    waypoint.getYPos(),
                    waypoint.getZPos()
                )
            );
        } else if (tether instanceof TetherPlayer tetherPlayer) {
            Player player = Bukkit.getPlayer(tetherPlayer.uuid);

            if (player == null)
                return new RTTetherPlayer(
                    tetherPlayer.uuid,
                    new RTLocation(
                        RTDimension.OTHER,
                        -1,
                        -1,
                        -1
                    )
                );

            return new RTTetherPlayer(
                player.getUniqueId(),
                RTAdapter.fromLocation(player.getEyeLocation())
            );
        } else if (tether instanceof TetherDeathPoint) {
            return new RTTetherDeathPoint(
                RTAdapter.fromLocation(tether.getLocation())
            );
        }

        return null;
    }

    public static RTLocation fromChestFindLocation(ChestFindLocation cfl) {
        if (cfl == null) {
            return null;
        }

        Location center = cfl.getCenterLocation();

        return new RTLocation(RTAdapter.fromWorld(center.getWorld()), center.getX(), center.getY(), center.getZ());
    }

    public static RTChestFindSession fromChestFindLocations(ChestFindLocation closest, ChestFindLocation best, ChestFindLocation most) {
        return new RTChestFindSession(
            RTAdapter.fromChestFindLocation(closest),
            RTAdapter.fromChestFindLocation(best),
            RTAdapter.fromChestFindLocation(most)
        );
    }

    public static RTLocation fromLocation(Location location) {
        return new RTLocation(
            RTAdapter.fromWorld(location.getWorld()),
            location.getX(),
            location.getY(),
            location.getZ()
        );
    }

}
