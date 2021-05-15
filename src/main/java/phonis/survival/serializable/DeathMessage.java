package src.phonis.survival.serializable;

import java.io.Serializable;
import java.util.UUID;

public class DeathMessage implements Serializable {
	private static final long serialVersionUID = -6202886297832941695L;
	public static HashMapData<UUID, DeathMessage> pd = new HashMapData<>("plugins/Survival/DeathMessages.txt");
	public static String defaultMessage = "very sad :(";

	private final String name;
	private final String deathMessage;

	public DeathMessage(String name) {
		this.name = name;
		this.deathMessage = DeathMessage.defaultMessage;
	}

	public DeathMessage(String name, String deathMessage) {
		this.name = name;
		this.deathMessage = deathMessage;
	}

	public static void update(UUID uuid, DeathMessage dm) {
		DeathMessage.pd.data.put(
			uuid,
			dm
		);
	}

	public String getName() {
		return this.name;
	}

	public String getDeathMessage() {
		return this.deathMessage;
	}
}
