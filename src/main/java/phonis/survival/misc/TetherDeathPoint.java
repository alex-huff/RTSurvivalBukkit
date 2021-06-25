package phonis.survival.misc;

import org.bukkit.Location;

public class TetherDeathPoint implements Tether {

    private final DeathLocation deathLocation;

    public TetherDeathPoint(DeathLocation deathLocation) {
        this.deathLocation = deathLocation;
    }

    @Override
    public Location getLocation() {
        return this.deathLocation.getLocation();
    }

}
