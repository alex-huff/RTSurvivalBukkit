package phonis.survival.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TetherPlayer implements Tether {

    public final UUID uuid;

    public TetherPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Location getLocation() {
        Player player = Bukkit.getPlayer(this.uuid);

        return player != null ? player.getEyeLocation() : null;
    }

}
