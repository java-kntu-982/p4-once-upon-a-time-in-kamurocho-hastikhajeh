package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GoldSoldier extends EnemySoldier {
    public GoldSoldier(double x, double y) {
        super(x, y, 800, 2000, 5, new Rectangle(17, 23, Color.GOLD));
    }
}
