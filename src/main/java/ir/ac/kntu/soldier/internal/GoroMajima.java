package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GoroMajima extends InternalSoldier {
    public GoroMajima(double x, double y) {
        super(x, y, 4000,4500,4,3,
                new Circle(15, Color.BLACK), new Text("GM"));
    }
}
