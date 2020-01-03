package src.phonis.survival.serializable;
import java.io.Serializable;
import java.util.UUID;

/**
 * Serializable data that represents the custom DeathMessages for each user;
 */
public class DeathMessage implements Serializable {
	private static final long serialVersionUID = -6202886297832941695L;
	public static HashMapData<UUID, DeathMessage> pd = new HashMapData<>("plugins/Survival/DeathMessages.txt");
	public static String defaultMessage = "very sad :(";
	
	private String name;
	private String deathMessage;

	/**
	 * DeathMessage constructor that takes in player name
	 *
	 * @param name String player name
	 */
	public DeathMessage(String name) {
		this.name = name;
		this.deathMessage = DeathMessage.defaultMessage;
	}

	/**
	 * DeathMessage constructor that takes in a player name and death message
	 *
	 * @param name         String player name
	 * @param deathMessage String death message
	 */
	public DeathMessage(String name, String deathMessage) {
		this.name = name;
		this.deathMessage = deathMessage;
	}

	/**
	 * Updates or adds DeathMessage to HashMapData
	 *
	 * @param uuid Player UUID
	 * @param dm   DeathMessage to be assigned to Player
	 */
	public static void update(UUID uuid, DeathMessage dm) {
		DeathMessage.pd.data.put(
			uuid,
			dm
		);
	}

	/**
	 * Gets player name
	 *
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
