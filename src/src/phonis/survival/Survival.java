package src.phonis.survival;

import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;

import src.phonis.survival.commands.*;
import src.phonis.survival.completers.FindCompleter;
import src.phonis.survival.completers.ItemTabCompleter;
import src.phonis.survival.completers.WaypointCompleter;
import src.phonis.survival.events.DeathEvent;
import src.phonis.survival.events.FireSpreadEvent;
import src.phonis.survival.events.GamemodeEvent;
import src.phonis.survival.events.InventoryLock;
import src.phonis.survival.events.JoinEvent;
import src.phonis.survival.events.SuffocateEvent;
//import src.phonis.survival.events.SleepEvent;
//import src.phonis.survival.events.TeleportEvent;
import src.phonis.survival.packets.IgnoreTypes;
import src.phonis.survival.packets.RedstoneListener;
import src.phonis.survival.serializable.DeathMessage;
import src.phonis.survival.serializable.SpectatorLocation;
import src.phonis.survival.serializable.Todolist;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.SurvivalSerializationUtil;

public class Survival extends JavaPlugin {
	private Logger log;
	private Sleeper sleeper;
    private ProtocolManager protocolManager;
	
	public Sleeper getSleeper() {
		return this.sleeper;
	}
	
	public void onEnable() {
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		this.protocolManager.addPacketListener(
			(PacketAdapter) (new RedstoneListener(this, IgnoreTypes.getRedstoneTypes(), getLogger()))
		);
		this.log = getLogger();
		
		new FireSpreadEvent(this);
		new DeathEvent(this);
		new GamemodeEvent(this);
		new InventoryLock(this);
		new JoinEvent(this);
		new SuffocateEvent(this);
//      new SleepEvent(this);
//      new TeleportEvent(this);
		
		this.log.info("Initializing waypoints.");
		SurvivalSerializationUtil.deserialize(Waypoint.pd, this.log);
		this.log.info("Initializing player messages.");
		SurvivalSerializationUtil.deserialize(DeathMessage.pd, this.log);
		this.log.info("Initializing spectator locations.");
		SurvivalSerializationUtil.deserialize(SpectatorLocation.pd, this.log);
		
		this.log.info("Initializing todo list.");
		SurvivalSerializationUtil.deserialize(Todolist.gd, this.log);
		
		this.sleeper = new Sleeper(this);
		getCommand("listwaypoints").setExecutor(new WaypointLister());
		getCommand("unloadradius").setExecutor(new RadiusUnloader());
		getCommand("removechunks").setExecutor(new ChunkRemover());
		getCommand("togglepiston").setExecutor(new TogglePistonAnimations());
		getCommand("setwaypoint").setExecutor(new WaypointSetter());
		getCommand("loadradius").setExecutor(new RadiusLoader());
		getCommand("todoremove").setExecutor(new TodoRemover());
		getCommand("showchunks").setExecutor(new ChunkShower());
		getCommand("sleepdeny").setExecutor(new SleepDenier(this));
		getCommand("slimemap").setExecutor(new SlimemapShower());
		getCommand("todoadd").setExecutor(new TodoAdder());
		getCommand("yawsnap").setExecutor(new YawSnapper());
		getCommand("spectog").setExecutor(new SpectatorToggler());
		getCommand("inspect").setExecutor(new Inspector());
		getCommand("sleep").setExecutor(this.sleeper);
		getCommand("todo").setExecutor(new TodoLister());
		getCommand("bl").setExecutor(new LocationBroadcaster());
		PluginCommand waypointPosUpdater = getCommand("updateposwaypoint");
		waypointPosUpdater.setExecutor(new WaypointPosUpdater());
		waypointPosUpdater.setTabCompleter(new WaypointCompleter());
		PluginCommand removeWaypointCommand = getCommand("removewaypoint");
		removeWaypointCommand.setExecutor(new WaypointRemover());
		removeWaypointCommand.setTabCompleter(new WaypointCompleter());
		PluginCommand craftCommand = getCommand("getcraft");
		craftCommand.setExecutor(new CraftGetter());
		craftCommand.setTabCompleter(new ItemTabCompleter(1));
		PluginCommand specTpCommand = getCommand("spectp");
		specTpCommand.setExecutor(new SpecTper());
		specTpCommand.setTabCompleter(new FindCompleter());
		PluginCommand findCommand = getCommand("find");
		findCommand.setExecutor(new Finder());
		findCommand.setTabCompleter(new FindCompleter());
		
		this.log.info("Survial enable finished.");
	}
	
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