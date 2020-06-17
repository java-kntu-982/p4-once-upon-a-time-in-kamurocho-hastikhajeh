package ir.ac.kntu.material.item;

import ir.ac.kntu.material.item.Material;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Truck extends Material {
    public Truck(double x, double y) {
        super(x, y, 5000, new Rectangle(60, 100, Color.ORANGE), new Text("T"));
        getShape().setArcWidth(10);
    }
}
