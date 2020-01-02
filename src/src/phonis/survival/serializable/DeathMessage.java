package src.phonis.survival.serializable;
import java.io.Serializable;
import java.util.UUID;

public class DeathMessage implements Serializable {
	private static final long serialVersionUID = -6202886297832941695L;
	public static HashMapData<UUID, DeathMessage> pd = new HashMapData<UUID, DeathMessage>("plugins/Survival/DeathMessages.txt");
	public static String defaultMessage = "very sad :(";
	public static String onDeath = "WHAT?!?!?!?...HOW?!?!?!? *Indian accent*";
	
	private String name;
	private String deathMessage;
	
	public DeathMessage(String name, String deathMessage) {
		this.name = name;
		this.deathMessage = deathMessage;
	}
	
	public DeathMessage(String name, UUID uuid) {
		this.name = name;
		this.deathMessage = DeathMessage.defaultMessage;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDeathMessage() {
		return this.deathMessage;
	}
	
	public static void addDeathMessage(UUID uuid, DeathMessage deathMessage) {
		DeathMessage.pd.data.put(
			uuid,
			deathMessage
		);
	}
}
