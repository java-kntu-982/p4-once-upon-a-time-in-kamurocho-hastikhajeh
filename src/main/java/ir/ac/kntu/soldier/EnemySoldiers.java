package ir.ac.kntu.soldier;

public abstract class EnemySoldiers extends Soldier {
    public EnemySoldiers(double x, double y, double health, double attack, double attackRange) {
        super(x, y);
        setHealth(health);
        setAttack(attack);
        setAttackRange(attackRange);
    }
}
