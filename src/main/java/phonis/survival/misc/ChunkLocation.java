package phonis.survival.misc;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Chunk;

public class ChunkLocation {
    public int x;
    public int z;

    public ChunkLocation(Chunk chunk) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(this.x).append(this.z).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof final ChunkLocation cl)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        return new EqualsBuilder().append(this.x, cl.x).append(this.z, cl.z).isEquals();
    }
}
