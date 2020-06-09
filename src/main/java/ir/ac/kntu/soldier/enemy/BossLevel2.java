package ir.ac.kntu.soldier.enemy;

import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BossLevel2 extends EnemySoldier {
    public BossLevel2(double x, double y) {
        super(x, y, 20000, 12000, 1,
                new Rectangle(15,20, Color.DARKGREEN));
    }
}
