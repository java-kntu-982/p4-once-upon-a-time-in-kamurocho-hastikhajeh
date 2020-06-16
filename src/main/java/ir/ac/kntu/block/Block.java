package ir.ac.kntu.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {
    private double x;
    private double y;
    private double width;
    private double height;
    private Rectangle shape;

    public Block(double x, double y) {
        this.x = x;
        this.y = y;
        width = 20;
        height = 90;
        shape = new Rectangle(width, height, Color.BROWN);
        shape.setX(x);
        shape.setY(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle getShape() {
        return shape;
    }
}
