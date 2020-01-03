package src.phonis.survival.tasks;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * BukkitRunnable that emulates player sleep
 */
public class SleepTask extends BukkitRunnable {
	private World world;

	/**
	 * SleepTask constructor that takes in world to sleep for
	 * @param world World world for sleep
	 */
	public SleepTask(World world) {
		this.world = world;
	}

	/**
	 * Method extended from BukkitRunnable that does the emulation of sleep for the world
	 */
	@Override
	public void run() {
		this.world.setTime(0L);
		this.world.setStorm(false);
		this.world.setThundering(false);
		this.cancel();
	}
}
