package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class KaoruSayama extends InternalSoldier {
    public KaoruSayama(double x, double y) {
        super(x, y, 4500, 4500, 4, 2,
                new Circle(15, Color.BLACK), new Text("KS"));
    }
}
