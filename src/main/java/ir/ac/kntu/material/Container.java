package ir.ac.kntu.material.item;

import ir.ac.kntu.material.item.Material;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Container extends Material {
    public Container(double x, double y) {
        super(x, y, 3000, new Rectangle(50, 60, Color.ORANGE), new Text("C"));
        getShape().setArcWidth(10);
    }
}
