package src.phonis.survival.serializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

public class Waypoint implements Serializable {
	private static final long serialVersionUID = 2341287428191016497L;
	public static HashMapData<String, Waypoint> pd = new HashMapData<String, Waypoint>("plugins/Survival/Waypoint.txt");
	
	private String name;
	private UUID world;
	private int xPos;
	private int yPos;
	private int zPos;
	
	public Waypoint(String name, UUID world, int xPos, int yPos, int zPos) {
		this.name = name;
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}
	
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
	
	public static void addWaypoint(String name, Location location) {		
		Waypoint.addWaypoint(
			name,
			new Waypoint(
				name,
				location.getWorld().getUID(), 
				location.getBlockX(), 
				location.getBlockY(), 
				location.getBlockZ()
			)
		);
	}
	
	public static void addWaypoint(String name, Waypoint waypoint) {
		Waypoint.pd.data.put(
			name, 
			waypoint
		);
	}
	
	public void updateLocation(int xPos, int yPos, int zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}
	
	public UUID getWorld() {
		return this.world;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public int getZPos() {
		return this.zPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
