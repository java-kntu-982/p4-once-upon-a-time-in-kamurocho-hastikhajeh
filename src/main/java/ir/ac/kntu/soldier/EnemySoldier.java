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
        if (internalInAttackRange.isEmpty()) {
            shape.setX(getX() + getxSpeed());
            shape.setY(getY() + getySpeed());
            getBar().setLayoutX(getX() + getxSpeed() - shape.getWidth() - 45);
            getBar().setLayoutY(getY() + getySpeed() - shape.getHeight() - 30);
        }
    }

    public void setXYSpeed(List<Material> items) {
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

    public boolean stop(List<Material> materials) {
        if (!materials.isEmpty()) {
//            materials.forEach(System.out::println);
            for (Material material : materials) {
                if (Math.hypot(getX() - material.getX(), getY() - material.getY()) < getAttackRangeConst()) {
                    if (material.getShape().getOpacity() > 0.0001) {
                        material.getShape().setOpacity(material.getShape().getOpacity() - 0.000001 * getAttack() + 0.00000001 * material.getDurability());
                        material.getText().setOpacity(material.getText().getOpacity() - 0.000001 * getAttack() + +0.00000001 * material.getDurability());
                        return true;
                    } else {
                        materials.remove(material);
                    }
                }
            }
            return false;
        }
        System.out.println("nn");
        return true;
    }

    public void makeInternalInAttackRange(List<InternalSoldier> internalSoldiers) {
        for (InternalSoldier internal: internalSoldiers) {
            if (getAttackRangeConst() >= Math.hypot(getX() - internal.getX(), getY() - internal.getY())) {
                if (!internalInAttackRange.contains(internal)) {
                    internalInAttackRange.add(internal);
                }
            } else {
                internalInAttackRange.remove(internal);
            }
        }
        internalInAttackRange.removeIf(internal -> {
            attackedBy = null;
            return !internalSoldiers.contains(internal);
        });
    }

    public void attack() {
        for (InternalSoldier internal: internalInAttackRange) {
            internal.getBar().setProgress(internal.getBar().getProgress() - getAttack()*0.0000005);
        }
    }

    public void fadeIfDead(List<EnemySoldier> enemySoldiers, Game game) {
        if (getBar().getProgress() < 0.1) {
            FadeTransition fade1 = new FadeTransition(Duration.millis(500), getShape());
            fade1.setFromValue(1);
            fade1.setToValue(0);
            FadeTransition fade2 = new FadeTransition(Duration.millis(500), getBar());
            fade2.setFromValue(1);
            fade2.setToValue(0);
            fade1.setOnFinished(event -> {
                enemySoldiers.remove(this);
            });
            fade1.play();
            fade2.play();
            game.setMoney(game.getMoney()+1);
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
