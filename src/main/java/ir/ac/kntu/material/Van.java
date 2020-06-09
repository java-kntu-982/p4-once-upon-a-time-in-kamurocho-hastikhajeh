package ir.ac.kntu.material;

import ir.ac.kntu.material.item.Material;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Van extends Material {
    public Van(double x, double y) {
        super(x, y, 6000, new Rectangle(80, 100, Color.ORANGE), new Text("V"));
        getShape().setArcWidth(15);
    }
}
