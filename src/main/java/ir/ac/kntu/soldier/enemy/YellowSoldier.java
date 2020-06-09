package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class YellowSoldier extends EnemySoldier {
    public YellowSoldier(double x, double y) {
        super(x, y, 800, 1800, 2, new Rectangle(7,9, Color.YELLOW));
    }
}
