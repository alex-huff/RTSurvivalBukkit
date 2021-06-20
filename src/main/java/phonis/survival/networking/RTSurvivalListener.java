package phonis.survival.networking;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import phonis.survival.Survival;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RTSurvivalListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            RTPacket packet = (RTPacket) ois.readObject();

            this.handlePacket(s, player, packet);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(String s, Player player, RTPacket packet) {
        if (packet instanceof RTRegister) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                Survival.instance,
                () -> RTManager.sendToPlayer(player, RTAdapter.fromWaypoints()),
                100L
            );
            System.out.println(player.getName() + " is using the modded client.");
            RTManager.addToSubscribed(player.getUniqueId());
        } else {
            System.out.println("Unrecognised packet.");
        }
    }

}
