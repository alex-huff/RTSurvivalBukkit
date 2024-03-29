package phonis.survival;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import phonis.survival.commands.*;
import phonis.survival.events.*;
import phonis.survival.misc.ChestFindSession;
import phonis.survival.misc.DeathLocation;
import phonis.survival.misc.TetherSession;
import phonis.survival.networking.RTManager;
import phonis.survival.networking.RTSurvivalListener;
import phonis.survival.serializable.DeathMessage;
import phonis.survival.serializable.SpectatorLocation;
import phonis.survival.serializable.Todolist;
import phonis.survival.serializable.Waypoint;
import phonis.survival.tasks.Tick;
import phonis.survival.util.DynmapAdapter;
import phonis.survival.util.SurvivalSerializationUtil;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class Survival extends JavaPlugin {

    public static final int protocolVersion = 1;
    public static final String path = "plugins/Survival/";
    public boolean keepInventory = false;
    public BukkitTask sleeper;
    public final List<SubCommand> commands = new ArrayList<>();
    public final Map<UUID, ChestFindSession> particleMap = new HashMap<>();
    public final Map<UUID, TetherSession> tetherSessionMap = new HashMap<>();
    public final Map<UUID, DeathLocation> lastDeathMap = new HashMap<>();
    public final Set<Location> updateQueue = new HashSet<>();
    private Logger log;
    private final RTSurvivalListener rtSurvivalListener = new RTSurvivalListener();
    public static Survival instance;

    @Override
    public void onEnable() {
        this.log = getLogger();
        Survival.instance = this;

        //listeners
        new FireSpreadEvent(this);
        new DeathEvent(this);
        new GamemodeEvent(this);
        new InventoryLock(this);
        new JoinEvent(this);
        new LeaveEvent(this);
        new SuffocateEvent(this);
        new BlockEvent(this);
        new FishEvent(this);

        //base commands
        this.commands.add(new CommandWaypoint(this));
        this.commands.add(new CommandFind(this));
        this.commands.add(new CommandTodo(this));
        this.commands.add(new CommandCraftGet(this));
        this.commands.add(new CommandSleep(this));
        this.commands.add(new CommandSpec(this));
        this.commands.add(new CommandSpecTog(this));
        this.commands.add(new CommandToggle(this));
        this.commands.add(new CommandTrade(this));
        this.commands.add(new CommandYawSnap(this));
        this.commands.add(new CommandSlimemap(this));
        this.commands.add(new CommandInspect(this));
        this.commands.add(new CommandBrodLoc(this));
        this.commands.add(new CommandDeathMessage(this));
        this.commands.add(new CommandFindInChest(this));
        this.commands.add(new CommandChunk(this));
        this.commands.add(new CommandHelp(this));
        this.commands.add(new CommandTether(this));
        this.commands.add(new CommandTD(this));

        new Tick(this).start();

        Bukkit.getMessenger().registerIncomingPluginChannel(this, RTManager.RTChannel, this.rtSurvivalListener);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, RTManager.RTChannel);

        File f = new File(Survival.path);

        if (!f.exists()) {
            if (f.mkdirs()) {
                this.log.info("Creating directory: " + path);
            }
        } else {
            //deserialize
            this.log.info("Initializing waypoints.");
            SurvivalSerializationUtil.deserialize(Waypoint.pd, this.log);
            this.log.info("Initializing player messages.");
            SurvivalSerializationUtil.deserialize(DeathMessage.pd, this.log);
            this.log.info("Initializing spectator locations.");
            SurvivalSerializationUtil.deserialize(SpectatorLocation.pd, this.log);
            this.log.info("Initializing todo list.");
            SurvivalSerializationUtil.deserialize(Todolist.gd, this.log);
        }

        try {
            DynmapAdapter.reloadAllWaypointMarkers();
        } catch (DynmapAdapter.DynmapNotInitializedException ignored) { }

        this.log.info("Survival enable finished.");
    }

	@Override
	public void onDisable() {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this, RTManager.RTChannel, this.rtSurvivalListener);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, RTManager.RTChannel);

        //serialize
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