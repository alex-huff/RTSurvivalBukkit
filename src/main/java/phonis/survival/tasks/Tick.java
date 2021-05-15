package phonis.survival.tasks;

import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import phonis.survival.Survival;
import phonis.survival.misc.*;
import phonis.survival.trace.ParticleLocation;
import phonis.survival.trace.ParticleType;

import java.util.*;

public class Tick implements Runnable {
    Survival survival;

    public Tick(Survival survival) {
        this.survival = survival;
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.survival, this, 0L, 1L);
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, ChestFindSession> entry : this.survival.particleMap.entrySet()) {
            UUID uuid = entry.getKey();
            Player player = Bukkit.getPlayer(uuid);

            if (player == null) {
                this.survival.particleMap.put(uuid, null);

                continue;
            }

            ChestFindSession cfs = entry.getValue();

            if (cfs == null) {
                continue;
            }

            Map<ChunkLocation, List<ChestFindLocation>> cflMap = cfs.getCflMap();

            if (!cfs.getWorld().equals(player.getWorld().getUID())) {
                this.survival.particleMap.put(uuid, null);

                continue;
            }

            for (Location location : this.survival.updateQueue) {
                ChunkLocation chunkLoc = new ChunkLocation(location.getChunk());

                if (cflMap.containsKey(chunkLoc)) {
                    List<ChestFindLocation> cflList = cflMap.get(chunkLoc);
                    Iterator<ChestFindLocation> iterator = cflList.iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        ChestFindLocation cfl = iterator.next();

                        if (cfl.getLocation().equals(location)) {
                            found = true;

                            if (!cfl.update(cfs.getMaterial())) {
                                iterator.remove();
                            }

                            break;
                        }
                    }

                    if (!found) {
                        ChestFindLocation cfl = new ChestFindLocation(location, 0);
                        cfl.update(cfs.getMaterial());

                        if (cfl.getNumItems() != 0) {
                            cflList.add(cfl);
                        }
                    }
                }
            }

            World world = player.getWorld();
            Chunk pChunk = player.getLocation().getChunk();
            ChunkLocation pChunkLoc = new ChunkLocation(pChunk);

            if (!cfs.getLastChunk().equals(pChunkLoc)) {
                cfs.updateLastChunk(pChunkLoc);
                int radius = 8;
                int chunkX = pChunk.getX();
                int chunkZ = pChunk.getZ();

                for (int i = -1 * radius; i <= radius; i++) {
                    for (int j = -1 * radius; j <= radius; j++) {
                        Chunk chunk = world.getChunkAt(chunkX + i, chunkZ + j);
                        ChunkLocation chunkLoc = new ChunkLocation(chunk);

                        if (!cflMap.containsKey(chunkLoc)) {
                            cflMap.put(chunkLoc, ChestFindLocation.getCfls(chunk, cfs.getMaterial()));
                        }
                    }
                }
            }

            this.drawCfls(cflMap, player);
        }

        this.survival.updateQueue.clear();

        for (Map.Entry<UUID, TetherSession> entry : this.survival.tetherSessionMap.entrySet()) {
            UUID uuid = entry.getKey();
            Player player = Bukkit.getPlayer(uuid);

            if (player == null) {
                this.survival.tetherSessionMap.put(uuid, null);

                continue;
            }

            TetherSession tetherSession = entry.getValue();

            if (tetherSession == null) {
                continue;
            }

            for (Tether tether : tetherSession.tethers) {
                Location target = tether.getLocation();

                if (target != null && target.getWorld().equals(player.getWorld())) {
                    PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                    Location playerLoc = player.getEyeLocation();
                    Vector direction = playerLoc.getDirection();
                    Location start = playerLoc.clone().add(
                        direction.clone().normalize().multiply(2)
                    );
                    List<ParticleLocation> pLocations = this.getLineParticles(
                        start,
                        target.clone().subtract(start).toVector(),
                        Math.min(10, start.distance(target)),
                        ParticleType.TNT
                    );

                    this.sendParticleLocations(pLocations, connection);
                }
            }
        }
    }

    private void drawCfls(Map<ChunkLocation, List<ChestFindLocation>> cflMap, Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        Location playerLoc = player.getEyeLocation();
        Vector direction = playerLoc.getDirection();
        Location start = playerLoc.clone().add(
            direction.clone().normalize().multiply(2)
        );

        ChestFindLocation best = null;
        double bestHeuristic = Double.MIN_VALUE;
        ChestFindLocation closest = null;
        double bestDistance = Double.MAX_VALUE;
        ChestFindLocation biggest = null;
        int biggestItems = Integer.MIN_VALUE;

        for (Map.Entry<ChunkLocation, List<ChestFindLocation>> chunkEntry : cflMap.entrySet()) {
            List<ChestFindLocation> cflList = chunkEntry.getValue();

            for (ChestFindLocation cfl : cflList) {
                double distance = playerLoc.distance(cfl.getCenterLocation());
                int numItems = cfl.getNumItems();
                double heuristic = (1.0 / Math.pow(Math.max(distance, 10.0), 1.5)) * numItems;

                if (heuristic > bestHeuristic) {
                    bestHeuristic = heuristic;
                    best = cfl;
                }

                if (distance < bestDistance) {
                    bestDistance = distance;
                    closest = cfl;
                }

                if (numItems > biggestItems) {
                    biggestItems = numItems;
                    biggest = cfl;
                }
            }
        }

        if (best != null)
            this.drawCflLine(start, best, connection, ParticleType.PLAYER);
        if (closest != null)
            this.drawCflLine(start, closest, connection, ParticleType.SAND);
        if (biggest != null)
            this.drawCflLine(start, biggest, connection, ParticleType.TNT);
    }

    private void drawCflLine(Location start, ChestFindLocation cfl, PlayerConnection connection, ParticleType pType) {
        List<ParticleLocation> pLocations = this.getLineParticles(
            start,
            cfl.getCenterLocation().clone().subtract(start).toVector(),
            Math.min(10, start.distance(cfl.getCenterLocation())),
            pType
        );

        this.sendParticleLocations(pLocations, connection);
    }

    private void sendParticleLocations(List<ParticleLocation> pLocations, PlayerConnection connection) {
        for (ParticleLocation pLocation : pLocations) {
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                org.bukkit.craftbukkit.v1_16_R3.CraftParticle.toNMS(
                    Particle.REDSTONE,
                    new Particle.DustOptions(
                        org.bukkit.Color.fromRGB(
                            pLocation.getType().getRGB().getR(),
                            pLocation.getType().getRGB().getG(),
                            pLocation.getType().getRGB().getB()
                        ),
                        .3f
                    )
                ),
                true,
                pLocation.getLocation().getX(),
                pLocation.getLocation().getY(),
                pLocation.getLocation().getZ(),
                0f,
                0f,
                0f,
                0f,
                0
            );

            connection.sendPacket(packet);
        }
    }

    private List<ParticleLocation> getLineParticles(Location start, Vector direction, double length, ParticleType type) {
        Vector intervalDirection = direction.clone().normalize().multiply(.1);
        Vector di2 = intervalDirection.clone();
        Location finish = start.clone().add(intervalDirection.clone().multiply(10 * length));

        List<ParticleLocation> ret = new ArrayList<>();

        ret.add(new ParticleLocation(finish, type));
        ret.add(new ParticleLocation(start, ParticleType.TNTENDPOS));
        di2.add(intervalDirection);

        while (di2.length() < length) {
            ret.add(new ParticleLocation(start.clone().add(di2.getX(), di2.getY(), di2.getZ()), type));
            di2.add(intervalDirection);
        }

        return ret;
    }
}