package src.phonis.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepTask extends BukkitRunnable {
    private final World world;

    public SleepTask(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        this.world.setTime(0L);
        this.world.setStorm(false);
        this.world.setThundering(false);
        this.cancel();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setStatistic(Statistic.TIME_SINCE_REST, 0);
        }
    }
}
