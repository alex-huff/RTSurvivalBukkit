package src.phonis.survival.serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class SpectatorLocation implements Serializable {
	private static final long serialVersionUID = -6523678654819956267L;
	public static HashMapData<UUID, SpectatorLocation> pd = new HashMapData<>("plugins/Survival/SpectatorLocation.txt");

	private UUID world;
	private double xPos;
	private double yPos;
	private double zPos;
	private float pitch;
	private float yaw;

	public SpectatorLocation(UUID world, double xPos, double yPos, double zPos, float pitch, float yaw) {
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.pitch = pitch;
		this.yaw = yaw;
	}

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

	public void updateSpectatorLocation(Location loc) {
		this.setWorld(Objects.requireNonNull(loc.getWorld()).getUID());
		this.setXPos(loc.getX());
		this.setYPos(loc.getY());
		this.setZPos(loc.getZ());
		this.setPitch(loc.getPitch());
		this.setYaw(loc.getYaw());
	}

	public UUID getWorld() {
		return world;
	}

	public void setWorld(UUID world) {
		this.world = world;
	}

	public void setXPos(double xPos) {
		this.xPos = xPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	public void setZPos(double zPos) {
		this.zPos = zPos;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
}
