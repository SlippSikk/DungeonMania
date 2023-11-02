package dungeonmania.entities.buildables;

import dungeonmania.battles.BattleStatistics;

public class Shield extends Buildable {
    private double defence;
    private double health = 0;
    private double attack = 0;
    private double attackMagnifier = 1;
    private double damageReducer = 1;

    public Shield(int durability, double defence) {
        super(null, durability);
        this.defence = defence;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(health, attack, defence, attackMagnifier, damageReducer));
    }

}
