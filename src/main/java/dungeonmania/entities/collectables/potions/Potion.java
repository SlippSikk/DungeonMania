package dungeonmania.entities.collectables.potions;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.CollectableEntity;
import dungeonmania.util.Position;

public abstract class Potion extends CollectableEntity implements BattleItem {
    private int duration;

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    @Override
    public void use(Game game) {
        return;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    @Override
    public int getDurability() {
        return 1;
    }

    public abstract void applyEffect(Player player);
}
