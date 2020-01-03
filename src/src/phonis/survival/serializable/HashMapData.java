package src.phonis.survival.serializable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Serializable collection used to HashMap like plugin data
 * Example: Spectator locations, Waypoints, DeathMessages
 * @param <K>
 * @param <V>
 */
public class HashMapData<K, V extends Serializable> {
	public HashMap<K, V> data = new HashMap<>();
	public String filename;

	/**
	 * HashMapData constructor that sets filename for serialization
	 * @param filename String filename of out file
	 */
	public HashMapData(String filename) {
		this.filename = filename;
	}
}
