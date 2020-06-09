package ir.ac.kntu;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {
    Circle circle = new Circle(5);
    Circle circle1 = new Circle(500,200,15, Color.YELLOW);
    Circle circle2 = new Circle(500,300,15, Color.GREEN);
    ProgressBar bar1 = new ProgressBar(1);
    Circle circle3 = new Circle();
    double x, y;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800,600);
        stage.setScene(scene);
        scene.addEventHandler(MouseEvent.ANY, e -> {
            circle.setCenterX(e.getX());
            circle.setCenterY(e.getY());
        });
        AtomicBoolean b = new AtomicBoolean(false);
        circle1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            circle3 = circle1;
            b.set(true);
        });
        circle2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            circle3 = circle2;
            b.set(true);
        });
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (b.get()) {
//                circle3.setCenterX(e.getX());
//                circle3.setCenterY(e.getY());
//                x = e.getX();
//                y = e.getY();
                Path path = new Path();
                path.getElements().add(new MoveTo(circle3.getCenterX(),circle3.getCenterY()));
                CubicCurveTo cct = new CubicCurveTo();
                cct.setControlX1(circle3.getCenterX());
                cct.setControlY1(circle3.getCenterY());
                cct.setControlX2(circle3.getCenterX());
                cct.setControlY2(circle3.getCenterY());
                cct.setX(e.getX());
                cct.setY(e.getY());
                path.getElements().add (cct);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(1000));
                pathTransition.setPath(path);
                pathTransition.setNode(circle3);
                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition.setCycleCount((int) 1f);
                pathTransition.setAutoReverse(true);
                pathTransition.play();
                circle3.setCenterX(e.getX());
                circle3.setCenterY(e.getY());
                b.set(false);
                e.consume();
            }
        });
        bar1.setScaleX(0.5);
//        bar1.setTranslateX(20);
        root.getChildren().addAll(circle, circle1, circle2, bar1);
        stage.show();

//        System.out.println(b);
//        if (!b.get()) {
//            while (circle3.getCenterY() != y && circle3.getCenterX() != x) {
//                move(circle3, x, y);
//            }
//        }

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                bar1.setProgress(bar1.getProgress() - 0.01);
            }
        }.start();
    }

    public static void move(Circle circle, double x, double y) {
        double xSpeed = (x - circle.getCenterX())/Math.hypot(circle.getCenterX() - x, circle.getCenterY() - y);
        double ySpeed = (y - circle.getCenterY())/Math.hypot(circle.getCenterX() - x, circle.getCenterY() - y);
        circle.setCenterX(circle.getCenterX() + xSpeed);
        circle.setCenterY(circle.getCenterY() + ySpeed);
    }
}
