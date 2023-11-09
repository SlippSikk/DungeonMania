package dungeonmania.entities.logicalEntities;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private boolean open = false;

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

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open) {
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
