package dungeonmania.entities.logicalEntities;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private boolean on;

    public LightBulb(Position pos, String logic) {
        super(pos, logic);
        this.on = false;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
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
