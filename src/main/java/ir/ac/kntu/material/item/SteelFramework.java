package ir.ac.kntu.material.item;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SteelFramework extends Material {
    public SteelFramework(double x, double y, int durability, Rectangle shape, Text text) {
        super(x, y, 8500,
                new Rectangle(60, 80, Color.ORANGE),
                new Text("SF"));
    }
}
