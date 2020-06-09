package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BossLevel1 extends EnemySoldier {
    public BossLevel1(double x, double y) {
        super(x, y, 8000, 5000, 1,
                new Rectangle(15,20, Color.DARKBLUE));
    }
}
