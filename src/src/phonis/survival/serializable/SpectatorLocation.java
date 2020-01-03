package src.phonis.survival.serializable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Serializable data that represents SpectatorLocations from /spectog use
 */
public class SpectatorLocation implements Serializable {
	private static final long serialVersionUID = -6523678654819956267L;
	public static HashMapData<UUID, SpectatorLocation> pd = new HashMapData<>("plugins/Survival/SpectatorLocation.txt");
	
	private UUID world;
	private double xPos;
	private double yPos;
	private double zPos;
	private float pitch;
	private float yaw;

	/**
	 * SpectatorLocation constructor that takes in position data
	 * @param world UUID of world
	 * @param xPos double x position
	 * @param yPos double y position
	 * @param zPos double z position
	 * @param pitch float pitch
	 * @param yaw float yaw
	 */
	public SpectatorLocation(UUID world, double xPos, double yPos, double zPos, float pitch, float yaw) {
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	/**
	 * Gets a SpectatorLocation from a Player
	 * @param player Player player
	 * @return SpectatorLocation
	 */
	public static SpectatorLocation get(Player player) {
		SpectatorLocation specLoc = SpectatorLocation.pd.data.get(player.getUniqueId());

		if (specLoc == null) {
			Location loc = player.getLocation();
			specLoc = new SpectatorLocation(
				player.getWorld().getUID(),
				loc.getX(),
				loc.getY(),
				loc.getZ(),
				loc.getPitch(),
				loc.getYaw()
			);

			SpectatorLocation.pd.data.put(
				player.getUniqueId(),
				specLoc
			);
		}

		return specLoc;
	}

	/**
	 * Get Bukkit style Location object from SpectatorLocation
	 * @return Location
	 */
	public Location getLocation() {
		return new Location(
			Bukkit.getWorld(this.world),
			this.xPos,
			this.yPos,
			this.zPos,
			this.yaw,
			this.pitch
		);
	}

	/**
	 * Update the SpectatorLocation with Bukkit style Location
	 * @param loc Location location
	 */
	public void updateSpectatorLocation(Location loc) {
		this.setWorld(Objects.requireNonNull(loc.getWorld()).getUID());
		this.setXPos(loc.getX());
		this.setYPos(loc.getY());
		this.setZPos(loc.getZ());
		this.setPitch(loc.getPitch());
		this.setYaw(loc.getYaw());
	}

	/**
	 * Get world UUID
	 * @return UUID
	 */
	public UUID getWorld() {
		return world;
	}

	/**
	 *
	 * Set world UUID
	 * @param world UUID of world
	 */
	public void setWorld(UUID world) {
		this.world = world;
	}

	/**
	 * Set x position
	 * @param xPos double x position
	 */
	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	/**
	 * Set y position
	 * @param yPos double y position
	 */
	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	/**
	 * Set z position
	 * @param zPos double z position
	 */
	public void setZPos(double zPos) {
		this.zPos = zPos;
	}

	/**
	 * Set pitch
	 * @param pitch float pitch
	 */
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	/**
	 * Set yaw
	 * @param yaw float yaw
	 */
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
}
