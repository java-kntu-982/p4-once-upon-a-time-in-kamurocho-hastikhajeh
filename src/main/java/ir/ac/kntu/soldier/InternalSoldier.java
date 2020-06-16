package ir.ac.kntu.soldier;

import ir.ac.kntu.scene.Window;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class InternalSoldier extends Soldier {
    private final double fieldOfView;
    private final double fieldOfViewConst = Math.hypot(Window.getHEIGHT(),Window.getWIDTH())/10;
    private Circle shape;
    private Text text;
    private List<EnemySoldier> enemyInFieldOfView;
    private List<EnemySoldier> enemyInAttackRange;
    private Double finalX;
    private Double finalY;
    private Integer lvl;

    public InternalSoldier(double x, double y, double health, double attack, double attackRange, double fieldOfView, Circle shape, Text text) {
        super(x, y);
        setHealth(health);
        setAttack(attack);
        setAttackRange(attackRange);
        lvl = 1;
        this.fieldOfView = fieldOfView;
        this.shape = shape;
        shape.setCenterX(x);
        shape.setCenterY(y);
        this.text = text;
        text.setX(x - 7);
        text.setY(y + 5);
        text.setFill(Color.WHITE);
        enemyInFieldOfView = new ArrayList<>();
        enemyInAttackRange = new ArrayList<>();
        finalX = null;
        finalY = null;
        setBar(new ProgressBar(1));
        getBar().setLayoutX(x - shape.getRadius());
        getBar().setLayoutY(y - shape.getRadius());
        getBar().setScaleX(0.4);
        getBar().setScaleY(0.5);
    }


    public void setXYSpeed(double x, double y, boolean playerOrder) {
        if (playerOrder) {
            finalX = x;
            finalY = y;
        } else {
            finalX = null;
            finalY = null;
        }
        setxSpeed((x - getX()) / Math.hypot(x - getX(), y - getY()));
        setySpeed((y - getY()) / Math.hypot(x - getX(), y - getY()));
    }

    public boolean gotToFinalPoint() {
        if (finalX != null && finalY != null) {
            return Math.hypot(finalX - getX(), finalY - getY()) < 0.5;
        }
        return false;
    }

    public void move() {
//        if (!enemyInAttackRange.isEmpty()) {
//            setxSpeed(0);
//            setySpeed(0);
//        }
        if (enemyInAttackRange.isEmpty()) {
            if (!gotToFinalPoint()) {
                shape.setCenterX(shape.getCenterX() + getxSpeed());
                shape.setCenterY(shape.getCenterY() + getySpeed());
                text.setX(shape.getCenterX() - 5);
                text.setY(shape.getCenterY() + 7);
                getBar().setLayoutX(shape.getCenterX() - shape.getRadius());
                getBar().setLayoutY(shape.getCenterY() - shape.getRadius());
            }
        }
    }

    public void makeEnemyInFieldOfViewList(List<EnemySoldier> enemySoldiers) {
        for (EnemySoldier enemy: enemySoldiers) {
            if (fieldOfViewConst >= Math.hypot(getX() - enemy.getX(), getY() - enemy.getY())) {
                if (!enemyInFieldOfView.contains(enemy)) {
                    enemyInFieldOfView.add(enemy);
                }
            } else {
                enemyInFieldOfView.remove(enemy);
            }
        }
        enemyInFieldOfView.removeIf(enemy -> !enemySoldiers.contains(enemy));
    }

    public void makeEnemyInAttackRange() {
        for (EnemySoldier enemy: enemyInFieldOfView) {
            if (getAttackRangeConst() >= Math.hypot(getX() - enemy.getX(), getY() - enemy.getY())) {
                if (!enemyInAttackRange.contains(enemy)) {
                    enemyInAttackRange.add(enemy);
                }
            } else {
                enemyInAttackRange.remove(enemy);
            }
        }
        enemyInAttackRange.removeIf(enemy -> {
            setxSpeed(0);
            setySpeed(0);
            return !enemyInFieldOfView.contains(enemy);
        });
    }

    public void goForEnemy() {
        if (!enemyInFieldOfView.isEmpty()) {
            EnemySoldier enemy = nearestEnemy(enemyInFieldOfView);
            setXYSpeed(enemy.getX(), enemy.getY(), false);
        }
    }

    public void attack() {
        for (EnemySoldier enemy: enemyInAttackRange) {
            enemy.getBar().setProgress(enemy.getBar().getProgress()-getAttack()*0.000003);
            enemy.setAttackedBy(this);
        }
    }

    public EnemySoldier nearestEnemy(List<EnemySoldier> enemySoldiers) {
        double min = Math.hypot(getX() - enemySoldiers.get(0).getX(), getY() - enemySoldiers.get(0).getY());
        EnemySoldier enemySoldier = enemySoldiers.get(0);
        for (EnemySoldier enemy: enemySoldiers) {
            if (min > Math.hypot(getX() - enemy.getX(), getY() - enemy.getY())) {
                min = Math.hypot(getX() - enemy.getX(), getY() - enemy.getY());
                enemySoldier = enemy;
            }
        }
        return enemySoldier;
    }

    public void setXAndY() {
        setX(shape.getCenterX());
        setY(shape.getCenterY());
    }

    public void lvlUp() {
        lvl++;
        double num = getHealth()*1.04;
        setHealth((int) num);
        num = getAttack()*1.04;
        setAttack((int) num);
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public double getFieldOfViewConst() {
        return fieldOfViewConst;
    }

    public List<EnemySoldier> getEnemyInFieldOfView() {
        return enemyInFieldOfView;
    }

    public List<EnemySoldier> getEnemyInAttackRange() {
        return enemyInAttackRange;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }
}
//    public void move(double x, double y, boolean playerOrder) {
//        if (isEnemyInFieldOfAttack()) {
//            makeEnemyInAttackRange(enemyInFieldOfView);
//        }
//        if ((playerOrder || !isEnemyInFieldOfAttack()) && !isMoving) {
//            Path path = new Path();
//            path.getElements().add(new MoveTo(shape.getCenterX(), shape.getCenterY()));
//            path.getElements().add(new CubicCurveTo(shape.getCenterX(),shape.getCenterY(),
//                                                    shape.getCenterX(),shape.getCenterY(),x,y));
//            PathTransition pathTransition = new PathTransition();
//            pathTransition.setDuration(Duration.millis(1000));
//            pathTransition.setPath(path);
//            pathTransition.setNode(shape);
//            pathTransition.setCycleCount(1);
//            pathTransition.setAutoReverse(true);
//            isMoving = true;
//            pathTransition.setOnFinished(event -> {
//                shape.setCenterX(shape.getCenterX() + shape.getTranslateX());
//                shape.setCenterY(shape.getCenterY() + shape.getTranslateY());
//                text.setX(text.getX() + shape.getTranslateX());
//                text.setY(text.getY() + shape.getTranslateY());
//                shape.setTranslateX(0);
//                shape.setTranslateY(0);
//                isMoving = false;
//            });
//            pathTransition.play();
//        }
//    }

//    go for enemy >>

//            double x, y;
//            double d = (getAttackRangeConst() * (enemy.getY() - getY())) /
//                    (Math.hypot(getX() - enemy.getX(),getY() - enemy.getY()) - getAttackRangeConst());
//            y = enemy.getY() - Math.abs(d);
//            x = enemy.getX() + Math.sqrt(Math.pow(getAttackRangeConst(),2) - Math.pow(d,2));
//            setXYSpeed(x,y);
//            move(x, y, false);
//            move(enemy.getX(), enemy.getY(), false);

