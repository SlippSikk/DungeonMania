package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Bow extends Buildable {
    private int durability;
    private static final int DEFAULT_MAG = 2;
    private static final int DEFAULT_VAL = 1;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.removeBuildable(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, DEFAULT_MAG, DEFAULT_VAL));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
