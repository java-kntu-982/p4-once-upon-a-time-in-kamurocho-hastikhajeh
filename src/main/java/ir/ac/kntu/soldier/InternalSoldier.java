package ir.ac.kntu.soldier;

public abstract class InternalSoldier extends Soldier {
    private double fieldOfView;

    public InternalSoldier(double x, double y, double health, double attack, double attackRange, double fieldOfView) {
        super(x, y);
        setHealth(health);
        setAttack(attack);
        setAttackRange(attackRange);
        this.fieldOfView = fieldOfView;
    }
}
