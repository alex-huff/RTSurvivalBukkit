package src.phonis.survival;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import src.phonis.survival.commands.*;
import src.phonis.survival.events.*;
import src.phonis.survival.packets.RedstoneListener;
import src.phonis.survival.serializable.DeathMessage;
import src.phonis.survival.serializable.SpectatorLocation;
import src.phonis.survival.serializable.Todolist;
import src.phonis.survival.serializable.Waypoint;
import src.phonis.survival.util.SurvivalSerializationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Survival extends JavaPlugin {
    public boolean keepInventory = false;
    public BukkitTask sleeper;
    public RedstoneListener redstoneListener;
    public List<SubCommand> commands = new ArrayList<>();
    private Logger log;

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

        protocolManager.addPacketListener(this.redstoneListener);

        //listeners
        new FireSpreadEvent(this);
        new DeathEvent(this);
        new GamemodeEvent(this);
        new InventoryLock(this);
        new JoinEvent(this);
        new SuffocateEvent(this);

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

        //deserialize
        this.log.info("Initializing waypoints.");
        SurvivalSerializationUtil.deserialize(Waypoint.pd, this.log);
        this.log.info("Initializing player messages.");
        SurvivalSerializationUtil.deserialize(DeathMessage.pd, this.log);
        this.log.info("Initializing spectator locations.");
        SurvivalSerializationUtil.deserialize(SpectatorLocation.pd, this.log);
        this.log.info("Initializing todo list.");
        SurvivalSerializationUtil.deserialize(Todolist.gd, this.log);

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