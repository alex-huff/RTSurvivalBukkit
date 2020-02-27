package src.phonis.survival.misc;

import org.bukkit.Material;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChestFindSession {
    private UUID world;
    private Material material;
    private ChunkLocation lastChunk;
    private Map<ChunkLocation, List<ChestFindLocation>> cflMap;

    public ChestFindSession(UUID world, Material material, ChunkLocation lastChunk, Map<ChunkLocation, List<ChestFindLocation>> cflMap) {
        this.world = world;
        this.material = material;
        this.lastChunk = lastChunk;
        this.cflMap = cflMap;
    }

    public UUID getWorld() {
        return this.world;
    }

    public Material getMaterial() {
        return this.material;
    }

    public ChunkLocation getLastChunk() {
        return this.lastChunk;
    }

    public Map<ChunkLocation, List<ChestFindLocation>> getCflMap() {
        return this.cflMap;
    }

    public void updateLastChunk(ChunkLocation chunkLocation) {
        this.lastChunk = chunkLocation;
    }
}
