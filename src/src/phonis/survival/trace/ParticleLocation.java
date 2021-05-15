package src.phonis.survival.trace;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

public class ParticleLocation {
    private final Location location;
    private final ParticleType type;

    public ParticleLocation(Location location, ParticleType type) {
        this.location = location;
        this.type = type;
    }

    public Location getLocation() {
        return this.location;
    }

    public ParticleType getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                                              append(this.getLocation()).
                                                                            append(this.getType()).
                                                                                                      toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleLocation)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        final ParticleLocation pl = (ParticleLocation) obj;

        return new EqualsBuilder().
                                      append(this.getLocation(), pl.getLocation()).
                                                                                      append(this.getType(), pl.getType()).
                                                                                                                              isEquals();
    }
}
