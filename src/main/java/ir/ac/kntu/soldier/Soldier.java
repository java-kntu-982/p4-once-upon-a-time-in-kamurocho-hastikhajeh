package ir.ac.kntu.soldier;

import ir.ac.kntu.scene.Window;
import javafx.scene.control.ProgressBar;

public abstract class Soldier {
    private Double health;
    private Double attack;
    private double attackRange;
    private final double attackRangeConst = Math.hypot(Window.getHEIGHT(),Window.getWIDTH())/20;
    private double x;
    private double y;
    private double speed;
    private double xSpeed;
    private double ySpeed;
    private boolean dead;
    private ProgressBar bar;

    public Soldier(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(double attackRange) {
        this.attackRange = attackRange;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getAttackRangeConst() {
        return attackRangeConst;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public ProgressBar getBar() {
        return bar;
    }

    public void setBar(ProgressBar bar) {
        this.bar = bar;
    }
}
