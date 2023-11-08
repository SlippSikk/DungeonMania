package dungeonmania.entities;

// import java.util.ArrayList;
// import java.util.List;

import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private boolean on;
    // private List<Wire> wires = new ArrayList<>();

    public LightBulb(Position pos, String logic) {
        super(pos, logic);
        this.on = false;
    }

    @Override
    public void update() {
        if (checkLogic()) {
            setOn(true);
        } else {
            setOn(false);
        }
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
