package ir.ac.kntu.soldier.internal;

import ir.ac.kntu.soldier.InternalSoldier;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class TaigaSaejima extends InternalSoldier {
    public TaigaSaejima(double x, double y) {
        super(x, y, 7000, 5000, 1, 1,
                new Circle(15, Color.BLACK), new Text("TS"));
    }
}
