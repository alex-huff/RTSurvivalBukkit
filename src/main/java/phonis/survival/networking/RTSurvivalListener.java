package phonis.survival.networking;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;

public class RTSurvivalListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        System.out.println(s + " " + player.getName() + " " + Arrays.toString(bytes));
    }

}
