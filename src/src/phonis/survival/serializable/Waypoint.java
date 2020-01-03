package src.phonis.survival.serializable;
import java.io.Serializable;
import java.util.*;

import org.bukkit.Location;

/**
 * Serializable data representing Waypoint
 */
public class Waypoint implements Serializable {
	private static final long serialVersionUID = 2341287428191016497L;
	public static HashMapData<String, Waypoint> pd = new HashMapData<>("plugins/Survival/Waypoint.txt");
	
	private String name;
	private UUID world;
	private int xPos;
	private int yPos;
	private int zPos;

	/**
	 * Waypoint constructor taking in name and position data
	 * @param name String name of waypoint
	 * @param world UUID of world
	 * @param xPos int x position
	 * @param yPos int y position
	 * @param zPos int z position
	 */
	public Waypoint(String name, UUID world, int xPos, int yPos, int zPos) {
		this.name = name;
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}

	/**
	 * Gets waypoint autocompletion from String
	 *
	 * @param start String that contains what user has typed for far for argument
	 * @return List
	 */
	public static List<String> getAutoComplete(String start){
		Set<String> names = Waypoint.pd.data.keySet();
		List<String> aC = new ArrayList<>();
		
		for(String name : names) {
			if(name.startsWith(start)) {
				aC.add(name);
			}
		}
		
		return aC;
	}

	/**
	 * Adds Waypoint to HashMapData from name and location
	 * @param name String name of waypoint
	 * @param location Location location of waypoint
	 */
	public static void addWaypoint(String name, Location location) {		
		Waypoint.addWaypoint(
			name,
			new Waypoint(
				name,
				Objects.requireNonNull(location.getWorld()).getUID(),
				location.getBlockX(), 
				location.getBlockY(), 
				location.getBlockZ()
			)
		);
	}

	/**
	 * Adds Waypoint to HashMap data from name and waypoint
	 * @param name String name of waypoint
	 * @param waypoint Waypoint waypoint
	 */
	public static void addWaypoint(String name, Waypoint waypoint) {
		Waypoint.pd.data.put(
			name, 
			waypoint
		);
	}

	/**
	 * Updates location for waypoint from xyz coordinates
	 * @param xPos int x position
	 * @param yPos int y position
	 * @param zPos int z position
	 */
	public void updateLocation(int xPos, int yPos, int zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}

	/**
	 * Gets world
	 * @return UUID
	 */
	public UUID getWorld() {
		return this.world;
	}

	/**
	 * Gets x position
	 * @return int
	 */
	public int getXPos() {
		return this.xPos;
	}

	/**
	 * Gets y position
	 * @return int
	 */
	public int getYPos() {
		return this.yPos;
	}

	/**
	 * Gets z position
	 * @return int
	 */
	public int getZPos() {
		return this.zPos;
	}

	/**
	 * Get name of waypoint
	 * @return String
	 */
	public String getName() {
		return name;
	}
}
