package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Shield extends Buildable {
    private static final int DEFAULT_VAL = 1;
    private int durability;
    private double defence;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = durability;
        this.defence = defence;
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
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence, DEFAULT_VAL, DEFAULT_VAL));
    }

    @Override
    public int getDurability() {
        return durability;
    }

}
