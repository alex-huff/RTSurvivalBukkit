package phonis.survival.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;
import phonis.survival.serializable.Waypoint;

import java.util.Map;

public class DynmapAdapter {

    private static DynmapCommonAPI api;

    public static DynmapCommonAPI getAPI() {
        if (api == null) {
            Plugin dynmapPlugin = Bukkit.getPluginManager().getPlugin("dynmap");

            if (dynmapPlugin instanceof DynmapCommonAPI) {
                DynmapAdapter.api = (DynmapCommonAPI) dynmapPlugin;
            } else {
                throw DynmapNotInitializedException.instance;
            }
        }

        return api;
    }

    public static void reloadAllWaypointMarkers() {
        DynmapAdapter.deleteAllWaypointMarkers();
        DynmapAdapter.addAllWaypointMarkers();
    }

    private static void deleteAllWaypointMarkers() {
        MarkerAPI markerAPI = DynmapAdapter.getAPI().getMarkerAPI();
        MarkerSet markerSet = markerAPI.getMarkerSet("Waypoints");

        if (markerSet == null) {
            return;
        }

        markerSet.deleteMarkerSet();
    }

    private static void addAllWaypointMarkers() {
        DynmapAdapter.getAPI().getMarkerAPI().createMarkerSet("Waypoints", "Waypoints", null, true);

        for (Map.Entry<String, Waypoint> entry : Waypoint.pd.data.entrySet()) {
            DynmapAdapter.createMarker(entry.getKey(), entry.getValue());
        }
    }

    public static void createMarker(String name, Waypoint waypoint) {
        MarkerAPI markerAPI = DynmapAdapter.getAPI().getMarkerAPI();
        MarkerSet markerSet = markerAPI.getMarkerSet("Waypoints");

        markerSet.createMarker(
            name,
            name,
            Bukkit.getWorld(waypoint.getWorld()).getName(),
            waypoint.getXPos(),
            waypoint.getYPos(),
            waypoint.getZPos(),
            markerAPI.getMarkerIcon("redflag"),
            true
        );
    }

    public static void updateMarker(String name, Waypoint waypoint) {
        MarkerAPI markerAPI = DynmapAdapter.getAPI().getMarkerAPI();
        MarkerSet markerSet = markerAPI.getMarkerSet("Waypoints");

        markerSet.findMarker(name).setLocation(Bukkit.getWorld(waypoint.getWorld()).getName(), waypoint.getXPos(), waypoint.getYPos(), waypoint.getZPos());
    }

    public static void deleteMarker(String name) {
        MarkerAPI markerAPI = DynmapAdapter.getAPI().getMarkerAPI();
        MarkerSet markerSet = markerAPI.getMarkerSet("Waypoints");

        markerSet.findMarker(name).deleteMarker();
    }

    public static class DynmapNotInitializedException extends RuntimeException {

        private static final DynmapNotInitializedException instance = new DynmapNotInitializedException();

        private DynmapNotInitializedException() {
            super("Dynmap not initialized.");
        }

    }

}
