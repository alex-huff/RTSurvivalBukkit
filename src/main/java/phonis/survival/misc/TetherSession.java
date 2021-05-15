package phonis.survival.misc;

import java.util.ArrayList;
import java.util.List;

public class TetherSession {
    public List<Tether> tethers = new ArrayList<>();

    public TetherSession(Object target) {
        this.add(target);
    }

    public void add(Object target) {
        this.tethers.add(new Tether(target));
    }
}
