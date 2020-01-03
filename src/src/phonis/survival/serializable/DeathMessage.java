package src.phonis.survival.serializable;
import java.io.Serializable;
import java.util.UUID;

/**
 * Serializable data that represents the custom DeathMessages for each user;
 * TODO implement runtime plugin command for setting of these DeathMessages, as they can no longer be configured in JSON since the move to serialization
 */
public class DeathMessage implements Serializable {
	private static final long serialVersionUID = -6202886297832941695L;
	public static HashMapData<UUID, DeathMessage> pd = new HashMapData<>("plugins/Survival/DeathMessages.txt");
	public static String defaultMessage = "very sad :(";
	
	private String name;
	private String deathMessage;

	/**
	 * DeathMessage constructor that takes in player name
	 * @param name String player name
	 */
	public DeathMessage(String name) {
		this.name = name;
		this.deathMessage = DeathMessage.defaultMessage;
	}

	/**
	 * Gets player name
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets death message
	 * @return String
	 */
	public String getDeathMessage() {
		return this.deathMessage;
	}
}
