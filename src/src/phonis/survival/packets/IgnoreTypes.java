package src.phonis.survival.packets;

import java.util.ArrayList;
import java.util.List;

import com.comphenix.protocol.PacketType;

public class IgnoreTypes {
	public static Iterable<PacketType> getRedstoneTypes() {
		List<PacketType> rTypes = new ArrayList<>();
		
		rTypes.add(PacketType.Play.Server.BLOCK_ACTION);
		rTypes.add(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
		
		return rTypes;
	}
}
