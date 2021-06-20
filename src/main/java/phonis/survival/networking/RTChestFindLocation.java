package phonis.survival.networking;

import java.io.Serializable;

public class RTChestFindLocation implements Serializable {

    public final RTDimension dimension;
    public final double x;
    public final double y;
    public final double z;

    public RTChestFindLocation(RTDimension dimension, double x, double y, double z) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
