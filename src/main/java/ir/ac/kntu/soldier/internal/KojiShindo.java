package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class KojiShindo extends InternalSoldier {
    public KojiShindo(double x, double y) {
        super(x, y, 3800, 3600, 1, 1,
                new Circle(15, Color.BLACK), new Text("KS"));
    }
}
