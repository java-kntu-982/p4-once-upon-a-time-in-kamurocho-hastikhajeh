package ir.ac.kntu.material.item;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PowerShovel extends Material {
    public PowerShovel(double x, double y) {
        super(x, y, 11000,
                new Rectangle(60, 110, Color.ORANGE),
                new Text("PS"));
        getShape().setArcWidth(10);
    }
}
