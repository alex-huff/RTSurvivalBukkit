package src.phonis.survival;

import java.util.Objects;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import src.phonis.survival.commands.*;
import src.phonis.survival.completers.*;
import src.phonis.survival.events.*;
import src.phonis.survival.packets.*;
import src.phonis.survival.serializable.DeathMessage;
import src.phonis.survival.serializable.SpectatorLocation;
import src.phonis.survival.serializable.Todolist;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.SurvivalSerializationUtil;

/**
 * Main plugin class
 */
public class Survival extends JavaPlugin {
	private Logger log;
	private Sleeper sleeper;
	public RedstoneListener redstoneListener;

	/**
	 * Gets Sleeper command, used for denying of global sleep by SleepDenier
	 * @return Sleeper
	 */
	public Sleeper getSleeper() {
		return this.sleeper;
	}

	/**
	 * Method extended from JavaPlugin, called on the enabling of the plugin by the server
	 */
	@Override
	public void onEnable() {
		this.log = getLogger();

		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		this.redstoneListener = new RedstoneListener(
			this,
			IgnoreTypes.getRedstoneTypes()
		);
		protocolManager.addPacketListener(this.redstoneListener);
		
		new FireSpreadEvent(this);
		new DeathEvent(this);
		new GamemodeEvent(this);
		new InventoryLock(this);
		new JoinEvent(this);
		new SuffocateEvent(this);
		
		this.log.info("Initializing waypoints.");
        SurvivalSerializationUtil.deserialize(Waypoint.pd, this.log);
        this.log.info("Initializing player messages.");
        SurvivalSerializationUtil.deserialize(DeathMessage.pd, this.log);
        this.log.info("Initializing spectator locations.");
        SurvivalSerializationUtil.deserialize(SpectatorLocation.pd, this.log);

        this.log.info("Initializing todo list.");
        SurvivalSerializationUtil.deserialize(Todolist.gd, this.log);

        this.sleeper = new Sleeper(this);
        Objects.requireNonNull(getCommand("setdeathmessage")).setExecutor(new DeathMessageUpdater());
        Objects.requireNonNull(getCommand("listwaypoints")).setExecutor(new WaypointLister());
        Objects.requireNonNull(getCommand("unloadradius")).setExecutor(new RadiusUnloader());
        Objects.requireNonNull(getCommand("removechunks")).setExecutor(new ChunkRemover());
        Objects.requireNonNull(getCommand("togglepiston")).setExecutor(new TogglePistonAnimations(this));
        Objects.requireNonNull(getCommand("setwaypoint")).setExecutor(new WaypointSetter());
        Objects.requireNonNull(getCommand("loadradius")).setExecutor(new RadiusLoader());
        Objects.requireNonNull(getCommand("todoremove")).setExecutor(new TodoRemover());
        Objects.requireNonNull(getCommand("showchunks")).setExecutor(new ChunkShower());
        Objects.requireNonNull(getCommand("sleepdeny")).setExecutor(new SleepDenier(this));
        Objects.requireNonNull(getCommand("slimemap")).setExecutor(new SlimemapShower());
		Objects.requireNonNull(getCommand("todoadd")).setExecutor(new TodoAdder());
		Objects.requireNonNull(getCommand("yawsnap")).setExecutor(new YawSnapper());
		Objects.requireNonNull(getCommand("spectog")).setExecutor(new SpectatorToggler());
		Objects.requireNonNull(getCommand("inspect")).setExecutor(new Inspector());
		Objects.requireNonNull(getCommand("sleep")).setExecutor(this.sleeper);
		Objects.requireNonNull(getCommand("todo")).setExecutor(new TodoLister());
		Objects.requireNonNull(getCommand("bl")).setExecutor(new LocationBroadcaster());
		PluginCommand waypointPosUpdater = getCommand("updateposwaypoint");
		Objects.requireNonNull(waypointPosUpdater).setExecutor(new WaypointPosUpdater());
		waypointPosUpdater.setTabCompleter(new WaypointCompleter());
		PluginCommand removeWaypointCommand = getCommand("removewaypoint");
		Objects.requireNonNull(removeWaypointCommand).setExecutor(new WaypointRemover());
		removeWaypointCommand.setTabCompleter(new WaypointCompleter());
		PluginCommand craftCommand = getCommand("getcraft");
		Objects.requireNonNull(craftCommand).setExecutor(new CraftGetter());
		craftCommand.setTabCompleter(new ItemTabCompleter(1));
		PluginCommand specTpCommand = getCommand("spectp");
		Objects.requireNonNull(specTpCommand).setExecutor(new SpecTper());
		specTpCommand.setTabCompleter(new FindCompleter());
		PluginCommand findCommand = getCommand("find");
		Objects.requireNonNull(findCommand).setExecutor(new Finder());
		findCommand.setTabCompleter(new FindCompleter());
		
		this.log.info("Survival enable finished.");
	}

	/**
	 * Method extended from JavaPlugin, called on the disabling of the plugin from the server
	 */
	public void onDisable() {
		this.log.info("Saving waypoints.");
		SurvivalSerializationUtil.serialize(Waypoint.pd, this.log);
		this.log.info("Saving player messages.");
		SurvivalSerializationUtil.serialize(DeathMessage.pd, this.log);
		this.log.info("Saving spectator locations.");
		SurvivalSerializationUtil.serialize(SpectatorLocation.pd, this.log);
		
		this.log.info("Saving todolist.");
		SurvivalSerializationUtil.serialize(Todolist.gd, this.log);
		
		this.log.info("Survival disable finished.");
	}
}