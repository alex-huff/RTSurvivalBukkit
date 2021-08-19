package phonis.survival.networking;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import phonis.survival.Survival;
import phonis.survival.networking.V1.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class RTSurvivalListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] data) {
        try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
            byte packetID = dis.readByte();
            RTPacket packet = switch (packetID) {
                case Packets.Out.RTRegisterID -> RTRegister.fromBytes(dis);
                case Packets.Out.RTSTogID -> RTSTog.fromBytes(dis);
                case Packets.Out.RTFICClearID -> RTFICClear.fromBytes(dis);
                case Packets.Out.RTTetherClearID -> RTTetherClear.fromBytes(dis);
                case Packets.Out.RTFICID -> RTFIC.fromBytes(dis);
                case Packets.Out.RTTetherOnHoveredWaypointID -> RTTetherOnHoveredWaypoint.fromBytes(dis);
                case Packets.Out.RTSTPToHoveredWaypointID -> RTSTPToHoveredWaypoint.fromBytes(dis);
                default -> null;
            };

            dis.close();
            this.handlePacket(s, player, packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(String s, Player player, RTPacket packet) {
        if (packet instanceof RTRegister register) {

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
        } else if (packet instanceof RTTetherOnHoveredWaypoint tohw) {

            player.performCommand("tether " + tohw.waypoint);
            System.out.println("[RT]: [tether]: " + player.getName() + ": " + tohw.waypoint);
        } else if (packet instanceof RTSTPToHoveredWaypoint stpthw) {

            player.performCommand("s tp " + stpthw.waypoint);
            System.out.println("[RT]: [s tp]: " + player.getName() + ": " + stpthw.waypoint);
        } else {
            System.out.println("Unrecognised packet from player: " + player.getName() + ".");
        }
    }

}
