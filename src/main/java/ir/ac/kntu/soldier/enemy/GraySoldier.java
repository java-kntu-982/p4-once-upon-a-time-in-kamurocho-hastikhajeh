package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GraySoldier extends EnemySoldier {
    public GraySoldier(double x, double y) {
        super(x, y, 1000, 800, 1,
                new Rectangle(9,13, Color.GRAY));
    }
}
