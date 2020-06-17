package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class SotaroKomaki extends InternalSoldier {
    public SotaroKomaki(double x, double y) {
        super(x, y, 2800, 5000, 1, 3,
                new Circle(15, Color.BLACK), new Text("SK"));
    }
}
