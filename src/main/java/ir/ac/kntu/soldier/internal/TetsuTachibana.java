package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class TetsuTachibana extends InternalSoldier {
    public TetsuTachibana(double x, double y) {
        super(x, y, 5600, 4000, 1, 2,
                new Circle(15, Color.BLACK), new Text("TT"));
    }
}
