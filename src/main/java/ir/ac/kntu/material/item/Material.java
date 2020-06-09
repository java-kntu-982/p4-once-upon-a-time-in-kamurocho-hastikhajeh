package ir.ac.kntu.material.item;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class Material {
    private final double x;
    private final double y;
    private int durability;
    private Rectangle shape;
    private Text text;

    public Material(double x, double y, int durability, Rectangle shape, Text text) {
        this.x = x;
        this.y = y;
        this.durability = durability;
        this.shape = shape;
        shape.setX(x);
        shape.setY(y);
        this.text = text;
        text.setX(x + shape.getWidth()/3);
        text.setY(y + (shape.getHeight()/2));
        text.setFill(Color.BLACK);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
//        return "Material{" +
//                "x=" + x +
//                ", y=" + y +
//                ", durability=" + durability +
//                ", shape=" + shape +
//                ", text=" + text +
//                '}';
    }

    public double getX() {
        return x;
    }

//    public void setX(double x) {
//        this.x = x;
//    }

    public double getY() {
        return y;
    }

//    public void setY(double y) {
//        this.y = y;
//    }

    public int getDurability() {
        return durability;
    }

    public Rectangle getShape() {
        return shape;
    }

    public Text getText() {
        return text;
    }
}
