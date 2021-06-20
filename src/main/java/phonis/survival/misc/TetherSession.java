package phonis.survival.misc;

import java.util.ArrayList;
import java.util.List;

public class TetherSession {
    public List<Tether> tethers = new ArrayList<>();

    public TetherSession(Tether tether) {
        this.add(tether);
    }

    public void add(Tether tether) {
        this.tethers.add(tether);
    }
}
