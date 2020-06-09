package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RedSoldier extends EnemySoldier {
    public RedSoldier(double x, double y) {
        super(x, y, 1000, 500, 1,
                new Rectangle(5,5, Color.RED));
    }
}
