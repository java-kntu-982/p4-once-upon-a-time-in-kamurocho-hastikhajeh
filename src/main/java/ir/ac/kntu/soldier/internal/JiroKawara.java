package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class JiroKawara extends InternalSoldier {
    public JiroKawara(double x, double y) {
        super(x, y, 5500, 3200, 3, 3,
                new Circle(15, Color.BLACK), new Text("JK"));
    }
}
