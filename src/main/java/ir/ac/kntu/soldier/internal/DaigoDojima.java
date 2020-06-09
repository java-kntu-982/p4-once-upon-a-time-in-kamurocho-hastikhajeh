package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DaigoDojima extends InternalSoldier {
    public DaigoDojima(double x, double y) {
        super(x, y, 4000, 4000, 2, 1,
                new Circle(15, Color.BLACK), new Text("DD"));
    }
}
