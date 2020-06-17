package ir.ac.kntu.soldier;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.material.item.Material;
import javafx.animation.FadeTransition;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class EnemySoldier extends Soldier {
    private Rectangle shape;
    private List<InternalSoldier> internalInAttackRange;
    private InternalSoldier attackedBy;
    public EnemySoldier(double x, double y, double health, double attack, double attackRange, Rectangle shape) {
        super(x, y);
        setHealth(health);
        setAttack(attack);
        setAttackRange(attackRange);
        this.shape = shape;
        shape.setX(x);
        shape.setY(y);
        shape.setArcHeight(5);
        shape.setArcWidth(5);
        internalInAttackRange = new ArrayList<>();
        attackedBy = null;
        setBar(new ProgressBar(1));
        getBar().setLayoutX(x - shape.getWidth() - 45);
        getBar().setLayoutY(y - shape.getHeight() - 30);
        getBar().setScaleX(0.3);
        getBar().setScaleY(0.4);
    }

    public void move() {
        if (!isDead()) {
            if (internalInAttackRange.isEmpty()) {
                shape.setX(getX() + getxSpeed());
                shape.setY(getY() + getySpeed());
                getBar().setLayoutX(getX() + getxSpeed() - shape.getWidth() - 45);
                getBar().setLayoutY(getY() + getySpeed() - shape.getHeight() - 30);
            }
        }
    }

    public void setXYSpeed(List<Material> items) {
        if (!isDead()) {
            Material item = nearest(items);
            if (attackedBy != null) {
                setxSpeed((attackedBy.getX() - getX()) / Math.hypot(attackedBy.getX() - getX(), attackedBy.getY() - getY()));
                setySpeed((attackedBy.getY() - getY()) / Math.hypot(attackedBy.getX() - getX(), attackedBy.getY() - getY()));
            } else if (stop(items)) {
                setxSpeed(0);
                setySpeed(0);
            } else {
                setxSpeed((item.getX() - getX()) / Math.hypot(item.getX() - getX(), item.getY() + 30 - getY()));
                setySpeed((item.getY() + 30 - getY()) / Math.hypot(item.getX() - getX(), item.getY() + 30 - getY()));
            }
        }
    }

    public boolean stop(List<Material> materials) {
        if (!isDead()) {
            for (Material material : materials) {
                if (Math.hypot(getX() - material.getX(), getY() - material.getY()) < getAttackRangeConst()) {
                    if (material.getShape().getOpacity() > 0.0001) {
                        material.getShape().setOpacity(material.getShape().getOpacity() - 0.0000001 * getAttack() + 0.000000001 * material.getDurability());
                        material.getText().setOpacity(material.getText().getOpacity() - 0.0000001 * getAttack() + +0.000000001 * material.getDurability());
                        return true;
                    } else {
                        materials.remove(material);
                        break;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public void makeInternalInAttackRange(List<InternalSoldier> internalSoldiers) {
        if (!isDead()) {
            for (InternalSoldier internal : internalSoldiers) {
                if (!internal.isDead()) {
                    if (getAttackRangeConst() >= Math.hypot(getX() - internal.getX(), getY() - internal.getY())) {
                        if (!internalInAttackRange.contains(internal)) {
                            internalInAttackRange.add(internal);
                        }
                    } else {
                        internalInAttackRange.remove(internal);
                    }
                }
            }
            internalInAttackRange.removeIf(internal -> {
                attackedBy = null;
                return internal.isDead();
            });
        }
    }

    public void attack() {
        if (!isDead()) {
            for (InternalSoldier internal : internalInAttackRange) {
                internal.getBar().setProgress(internal.getBar().getProgress() - getAttack() * 0.0000005 + internal.getHealth() * 0.00000005);
            }
        }
    }

    public void fadeIfDead(Game game) {
        if (getBar().getProgress() < 0.1) {
            getShape().setOpacity(0);
            getBar().setOpacity(0);
            setDead(true);
            game.setMoney(game.getMoney()+0.01);
        }
    }

    public Material nearest(List<Material> items) {
        if (!items.isEmpty()) {
            double min = Math.hypot(getX() - items.get(0).getX(), getY() - items.get(0).getY());
            Material material = items.get(0);
            for (Material mat : items) {
                if (min > Math.hypot(getX() - mat.getX(), getY() - mat.getY())) {
                    min = Math.hypot(getX() - mat.getX(), getY() - mat.getY());
                    material = mat;
                }
            }
            return material;
        }
        return null;
    }

    public void setXAndY() {
        setX(shape.getX());
        setY(shape.getY());
    }

    public Rectangle getShape() {
        return shape;
    }

    public InternalSoldier getAttackedBy() {
        return attackedBy;
    }

    public void setAttackedBy(InternalSoldier attackedBy) {
        this.attackedBy = attackedBy;
    }
}
