package src.phonis.survival;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import src.phonis.survival.commands.*;
import src.phonis.survival.completers.FindCompleter;
import src.phonis.survival.completers.ItemTabCompleter;
import src.phonis.survival.completers.WaypointCompleter;
import src.phonis.survival.events.*;
import src.phonis.survival.packets.RedstoneListener;
import src.phonis.survival.serializable.DeathMessage;
import src.phonis.survival.serializable.SpectatorLocation;
import src.phonis.survival.serializable.Todolist;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.SurvivalSerializationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Survival extends JavaPlugin {
	public static boolean keepInventory = false;

	private Logger log;
	private Sleeper sleeper;
	private RedstoneListener redstoneListener;

	public Sleeper getSleeper() {
		return this.sleeper;
	}

	public RedstoneListener getRedstoneListener() {
		return this.redstoneListener;
	}

	@Override
	public void onEnable() {
		this.log = getLogger();

		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

		//start listeners
		List<PacketType> rTypes = new ArrayList<>();

		rTypes.add(PacketType.Play.Server.BLOCK_ACTION);
		rTypes.add(PacketType.Play.Server.MULTI_BLOCK_CHANGE);

		this.redstoneListener = new RedstoneListener(
			this,
			rTypes
		);
		//end listeners

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
		Objects.requireNonNull(getCommand("togglekeepinventory")).setExecutor(new ToggleKeepInventory());
		Objects.requireNonNull(getCommand("setdeathmessage")).setExecutor(new DeathMessageUpdater());
		Objects.requireNonNull(getCommand("listwaypoints")).setExecutor(new WaypointLister());
		Objects.requireNonNull(getCommand("unloadradius")).setExecutor(new RadiusUnloader());
		Objects.requireNonNull(getCommand("removechunks")).setExecutor(new ChunkRemover());
		Objects.requireNonNull(getCommand("togglepiston")).setExecutor(new TogglePistonAnimations(this));
		Objects.requireNonNull(getCommand("setwaypoint")).setExecutor(new WaypointSetter());
		Objects.requireNonNull(getCommand("loadradius")).setExecutor(new RadiusLoader());
		Objects.requireNonNull(getCommand("todoremove")).setExecutor(new TodoRemover());
		Objects.requireNonNull(getCommand("showchunks")).setExecutor(new ChunkShower());
		Objects.requireNonNull(getCommand("todoupdate")).setExecutor(new TodoUpdater());
		Objects.requireNonNull(getCommand("sleepdeny")).setExecutor(new SleepDenier(this));
		Objects.requireNonNull(getCommand("gettrades")).setExecutor(new TradeGetter());
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

	@Override
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