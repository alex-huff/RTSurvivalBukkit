package phonis.survival.networking;

import org.bukkit.ChatColor;
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
            RTRegister register = (RTRegister) packet;

            if (register.protocolVersion != Survival.protocolVersion) {
                RTManager.sendToPlayer(player, new RTUnsupported(Survival.protocolVersion));
                player.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "You are using an unsupported version of RTSurvival: " + register.protocolVersion + ". Server is on: " + Survival.protocolVersion + ".");
                System.out.println(player.getName() + " is using an unsupported version.");
            } else {
                RTManager.sendToPlayer(player, RTAdapter.fromWaypoints());
                RTManager.addToSubscribed(player.getUniqueId());
                System.out.println(player.getName() + " is using the modded client.");
            }
        } else if (packet instanceof RTSTog) {
            player.performCommand("s tog");
            System.out.println("[RT]: [s tog]: " + player.getName());
        } else if (packet instanceof RTFICClear) {
            player.performCommand("fic clear");
            System.out.println("[RT]: [fic clear]: " + player.getName());
        } else if (packet instanceof RTTetherClear) {
            player.performCommand("tether clear");
            System.out.println("[RT]: [tether clear]: " + player.getName());
        } else if (packet instanceof RTFIC) {
            String material = player.getInventory().getItemInMainHand().getType().name();

            player.performCommand("fic " + material);
            System.out.println("[RT]: [fic]: " + player.getName() + ": " + material);
        } else if (packet instanceof RTTetherOnHoveredWaypoint) {
            RTTetherOnHoveredWaypoint tohw = (RTTetherOnHoveredWaypoint) packet;

            player.performCommand("tether " + tohw.waypoint);
            System.out.println("[RT]: [tether]: " + player.getName() + ": " + tohw.waypoint);
        } else if (packet instanceof RTSTPToHoveredWaypoint) {
            RTSTPToHoveredWaypoint stpthw = (RTSTPToHoveredWaypoint) packet;

            player.performCommand("s tp " + stpthw.waypoint);
            System.out.println("[RT]: [s tp]: " + player.getName() + ": " + stpthw.waypoint);
        } else {
            System.out.println("Unrecognised packet from player: " + player.getName() + ".");
        }
    }

}
