package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class RyujiGoda extends InternalSoldier {
    public RyujiGoda(double x, double y) {
        super(x, y, 5000, 5000, 2, 1,
                new Circle(15, Color.BLACK), new Text("RG"));
    }
}
