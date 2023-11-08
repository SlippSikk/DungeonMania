package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

public class MidnightArmour extends Buildable {
    private int durability = Integer.MAX_VALUE;
    private static final int DEFAULT_VAL = 1;

    private int attack;
    private int defence;

    public MidnightArmour(Position position, int attack, int defence) {
        super(position);
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

    @Override
    public int getDurability() {
        return durability;
    }

}
