package src.phonis.survival.packets;

import java.util.ArrayList;
import java.util.List;

import com.comphenix.protocol.PacketType;

/**
 * Class used to get redstone packet types for filtering by /togglepiston command;
 * TODO store reference to types somewhere so a new ArrayList is not recreated every time called, event though it is only called once
 */
public class IgnoreTypes {
	public static Iterable<PacketType> getRedstoneTypes() {
		List<PacketType> rTypes = new ArrayList<>();
		
		rTypes.add(PacketType.Play.Server.BLOCK_ACTION);
		rTypes.add(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
		
		return rTypes;
	}
}
