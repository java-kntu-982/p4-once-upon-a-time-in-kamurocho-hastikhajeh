package ir.ac.kntu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Main1 extends Application {
    // this class is just a sample
    // clear the start menu and start writing your project!
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, false, SceneAntialiasing.BALANCED);
        stage.setScene(scene);
        Camera camera = new ParallelCamera();
        scene.setCamera(camera);
        stage.setTitle("Once Upon a Time in Kamurocho!");
        Circle circle = new Circle(100, Color.rgb(128, 50, 110, 0.9));
        Rectangle rectangle = new Rectangle(40, 40, Color.web("0x01abff"));
        circle.setCenterX(200);
        circle.setCenterY(200);
        rectangle.setX(400);
        rectangle.setY(400);
        circle.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            double dx = e.getX() - circle.getCenterX();
            double dy = e.getY() - circle.getCenterY();
            circle.setCenterX(circle.getCenterX() + dx);
            circle.setCenterY(circle.getCenterY() + dy);
        });
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            rectangle.setX(e.getX() - 20);
            rectangle.setY(e.getY() - 20);
        });
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
            if (e.isAltDown()) {
                e.consume();
            }
        });
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                rectangle.setRotate(rectangle.getRotate() + 5);
            }
        }.start();
        Timer timer = new Timer();
        ProgressBar progressBar = new ProgressBar();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> progressBar.setProgress(progressBar.getProgress() + 0.1));
            }
        };
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> e.consume());
        timer.schedule(task, 1000, 500);
        root.getChildren().addAll(circle, rectangle, progressBar);
        stage.setResizable(false);
        scene.setFill(Color.DARKGREEN);
        stage.show();
    }
}