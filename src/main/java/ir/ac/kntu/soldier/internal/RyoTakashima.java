package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class RyoTakashima extends InternalSoldier {
    public RyoTakashima(double x, double y) {
        super(x, y, 3600, 3800, 1, 1,
                new Circle(15, Color.BLACK), new Text("RT"));
    }
}
