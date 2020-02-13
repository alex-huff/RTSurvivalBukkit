package src.phonis.survival.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MultiBlockChangeInfo;
import org.bukkit.Material;
import src.phonis.survival.Survival;

import java.util.ArrayList;
import java.util.List;

public class RedstoneListener extends PacketAdapter {
	private volatile boolean handlePackets = true;

	public RedstoneListener(Survival plugin, Iterable<PacketType> types) {
		super(plugin, ListenerPriority.NORMAL, types);
	}

	public boolean getHandlePackets() {
		return this.handlePackets;
	}

	public synchronized void toggle() {
		this.handlePackets = !this.handlePackets;
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if (this.handlePackets) {
			if (event.getPacketType() == PacketType.Play.Server.BLOCK_ACTION) {
				Material mat = event.getPacket().getBlocks().getValues().get(0);
	    		
	    		if (mat == Material.PISTON || mat == Material.STICKY_PISTON) {
			    	event.setCancelled(true);
	    		}
		    }else if (event.getPacketType() == PacketType.Play.Server.MULTI_BLOCK_CHANGE) {
		    	StructureModifier<MultiBlockChangeInfo[]> smmbcis = event.getPacket().getMultiBlockChangeInfoArrays();
		    	MultiBlockChangeInfo[] data = smmbcis.getValues().get(0);
		    	List<Integer> removeList = new ArrayList<>();
		    	
		    	for (int i = 0; i < data.length; i++) {
		    		Material mat = data[i].getData().getType();
		    		
		    		if(mat == Material.PISTON || mat == Material.STICKY_PISTON || mat == Material.MOVING_PISTON || mat == Material.PISTON_HEAD) {
		    			removeList.add(i);
		    		}
		    	}
		    	
		    	for (int remove : removeList) {
		    		data[remove] = null;
		    	}
		    	
		    	MultiBlockChangeInfo[] newData = new MultiBlockChangeInfo[data.length - removeList.size()];
		    	int rCount = 0;
		    	
		    	for (int i = 0; i < data.length; i++) {
		    		if (data[i] != null) {
		    			newData[i - rCount] = data[i];
		    		}else {
		    			rCount++;
		    		}
		    	}
		    	
		    	smmbcis.write(0, newData);
		    }
		}
	}
}
