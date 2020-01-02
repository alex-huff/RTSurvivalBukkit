package src.phonis.survival.tasks;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepTask extends BukkitRunnable {
	private World world;
	
	public SleepTask(World world) {
		this.world = world;
	}
	
	public void run() {
		this.world.setTime(0L);
		this.world.setStorm(false);
		this.world.setThundering(false);
		this.cancel();
	}
}
