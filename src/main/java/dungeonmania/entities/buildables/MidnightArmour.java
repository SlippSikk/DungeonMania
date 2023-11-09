package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    private static int durability = Integer.MAX_VALUE;
    private static final int DEFAULT_VAL = 1;

    private int attack;
    private int defence;

    public MidnightArmour(int attack, int defence) {
        super(null, durability);
        this.attack = attack;
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        return;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, attack, defence, DEFAULT_VAL, DEFAULT_VAL));
    }

}
