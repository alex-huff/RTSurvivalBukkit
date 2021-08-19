package phonis.survival.networking;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import phonis.survival.Survival;
import phonis.survival.networking.V1.RTPacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RTManager {

    private static final Set<UUID> subscribed = new HashSet<>();
    public static final String RTChannel = "rtsurvival:main";

    public static void sendToPlayer(Player player, RTPacket packet) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream das = new DataOutputStream(baos);

            das.writeByte(packet.getID());
            packet.toBytes(das);
            das.close();
            player.sendPluginMessage(Survival.instance, RTManager.RTChannel, baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToSubscribed(RTPacket packet) {
        for (UUID uuid : RTManager.subscribed) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null) continue;

            RTManager.sendToPlayer(player, packet);
        }
    }

    public static void addToSubscribed(UUID uuid) {
        RTManager.subscribed.add(uuid);
    }

    public static boolean isSubscribed(UUID uuid) {
        return RTManager.subscribed.contains(uuid);
    }

    public static void removeFromSubscribed(UUID uuid) {
        RTManager.subscribed.remove(uuid);
    }

    public static void sendToPlayerIfSubscribed(Player player, RTPacket packet) {
        if (RTManager.subscribed.contains(player.getUniqueId())) {
            RTManager.sendToPlayer(player, packet);
        }
    }

}
