package phonis.survival.networking;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

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
            RTManager.sendToPlayer(player, RTAdapter.fromWaypoints());
            RTManager.addToSubscribed(player.getUniqueId());
            System.out.println(player.getName() + " is using the modded client.");
        } else if (packet instanceof RTSTog) {
            player.performCommand("s tog");
        } else if (packet instanceof RTFICClear) {
            player.performCommand("fic clear");
        } else if (packet instanceof RTTetherClear) {
            player.performCommand("tether clear");
        } else if (packet instanceof RTFIC) {
            player.performCommand("fic " + player.getInventory().getItemInMainHand().getType().name());
        } else if (packet instanceof RTTetherOnHoveredWaypoint) {
            RTTetherOnHoveredWaypoint tohw = (RTTetherOnHoveredWaypoint) packet;

            player.performCommand("tether " + tohw.waypoint);
        } else if (packet instanceof RTSTPToHoveredWaypoint) {
            RTSTPToHoveredWaypoint stpthw = (RTSTPToHoveredWaypoint) packet;

            player.performCommand("s tp " + stpthw.waypoint);
        } else {
            System.out.println("Unrecognised packet.");
        }
    }

}
