package dungeonmania.entities;

// import java.util.ArrayList;
// import java.util.List;

import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private boolean open = false;
    // private List<Wire> wires = new ArrayList<>();

    public SwitchDoor(Position pos, String logic) {
        super(pos, logic);
        this.open = false;
    }

    @Override
    public void update() {
        if (checkLogic()) {
            setOpen(true);
        } else {
            setOpen(false);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
