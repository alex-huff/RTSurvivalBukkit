package src.phonis.survival;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import src.phonis.survival.commands.*;
import src.phonis.survival.events.*;
import src.phonis.survival.misc.ChestFindSession;
import src.phonis.survival.misc.TetherSession;
import src.phonis.survival.serializable.DeathMessage;
import src.phonis.survival.serializable.SpectatorLocation;
import src.phonis.survival.serializable.Todolist;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.tasks.Tick;
import src.phonis.survival.util.SurvivalSerializationUtil;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class Survival extends JavaPlugin {
    public static final String path = "plugins/Survival/";
    public boolean keepInventory = false;
    public BukkitTask sleeper;
    public List<SubCommand> commands = new ArrayList<>();
    public Map<UUID, ChestFindSession> particleMap = new HashMap<>();
    public Map<UUID, TetherSession> tetherSessionMap = new HashMap<>();
    public Set<Location> updateQueue = new HashSet<>();
    private Logger log;

    @Override
    public void onEnable() {
        this.log = getLogger();

        //listeners
        new FireSpreadEvent(this);
        new DeathEvent(this);
        new GamemodeEvent(this);
        new InventoryLock(this);
        new JoinEvent(this);
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
        this.commands.add(new CommandDrawImage(this));

        new Tick(this).start();

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

        this.log.info("Survival enable finished.");
    }

	@Override
	public void onDisable() {
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