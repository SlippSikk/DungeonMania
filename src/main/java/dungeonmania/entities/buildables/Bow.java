package dungeonmania.entities.buildables;

import dungeonmania.battles.BattleStatistics;

public class Bow extends Buildable {
    private double health = 0;
    private double attack = 0;
    private double defence = 0;
    private double attackMagnifier = 2;
    private double damageReducer = 2;

    public Bow(int durability) {
        super(null, durability);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(health, attack, defence, attackMagnifier, damageReducer));
    }

}
