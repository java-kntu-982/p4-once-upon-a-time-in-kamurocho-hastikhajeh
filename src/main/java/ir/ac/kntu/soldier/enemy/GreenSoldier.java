package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GreenSoldier extends EnemySoldier {
    public GreenSoldier(double x, double y) {
        super(x, y, 1500, 700, 1, new Rectangle(11, 15, Color.GREEN));
    }
}
