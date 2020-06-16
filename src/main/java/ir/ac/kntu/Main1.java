package ir.ac.kntu;

import ir.ac.kntu.scene.Window;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main1 extends Application {
//    Circle circle = new Circle(5);
//    Circle circle1 = new Circle(500,200,15, Color.YELLOW);
//    Circle circle2 = new Circle(500,300,15, Color.GREEN);
//    ProgressBar bar1 = new ProgressBar(1);
//    Circle circle3 = new Circle();
//    double x, y;

    public static void main(String[] args) {
        launch();
    }

    private GridPane menuPane;
    private Scene menuScene;
    private Group gamePane;
    private Scene gameScene;

    @Override
    public void start(Stage stage) {
//        Group root = new Group();
//        Scene scene = new Scene(root, 800,600);
//        stage.setScene(scene);
        initialSetting(stage);
        stage.show();



    }

    private void initialSetting(Stage stage) {
        menu(stage);
        game(stage);
        stage.setScene(menuScene);
    }

    private void game(Stage stage) {
        gamePane = new Group();
        gameScene = new Scene(gamePane, 800, 600);
        Circle circle = new Circle(15, Color.BLACK);
        gameScene.addEventHandler(MouseEvent.ANY, e -> {
            circle.setCenterX(e.getX());
            circle.setCenterY(e.getY());
        });
        gamePane.getChildren().add(circle);
    }

    private void menu(Stage stage) {
        menuPane = new GridPane();
        menuScene = new Scene(menuPane, 800, 600);
        menuPane.setPadding(new Insets(200, 10, 10, 300));
//        menuPane.setMinSize(30, 30);
        menuPane.setVgap(5);
        menuPane.setHgap(5);

        Button game = new Button("game");
        menuPane.add(game,0,0);

        game.setOnAction(e -> stage.setScene(gameScene));

        Button player = new Button("player");
        menuPane.add(player,0,1);

        menuPane.setStyle("-fx-background-color: #D8BFD8;");

    }

//    public static void move(Circle circle, double x, double y) {
//        double xSpeed = (x - circle.getCenterX())/Math.hypot(circle.getCenterX() - x, circle.getCenterY() - y);
//        double ySpeed = (y - circle.getCenterY())/Math.hypot(circle.getCenterX() - x, circle.getCenterY() - y);
//        circle.setCenterX(circle.getCenterX() + xSpeed);
//        circle.setCenterY(circle.getCenterY() + ySpeed);
//    }
}

//        scene.addEventHandler(MouseEvent.ANY, e -> {
//        circle.setCenterX(e.getX());
//        circle.setCenterY(e.getY());
//        });
//        AtomicBoolean b = new AtomicBoolean(false);
//        circle1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//        circle3 = circle1;
//        b.set(true);
//        });
//        circle2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//        circle3 = circle2;
//        b.set(true);
//        });
//        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
//        if (b.get()) {
////                circle3.setCenterX(e.getX());
////                circle3.setCenterY(e.getY());
////                x = e.getX();
////                y = e.getY();
//        Path path = new Path();
//        path.getElements().add(new MoveTo(circle3.getCenterX(),circle3.getCenterY()));
//        CubicCurveTo cct = new CubicCurveTo();
//        cct.setControlX1(circle3.getCenterX());
//        cct.setControlY1(circle3.getCenterY());
//        cct.setControlX2(circle3.getCenterX());
//        cct.setControlY2(circle3.getCenterY());
//        cct.setX(e.getX());
//        cct.setY(e.getY());
//        path.getElements().add (cct);
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(1000));
//        pathTransition.setPath(path);
//        pathTransition.setNode(circle3);
//        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.setCycleCount((int) 1f);
//        pathTransition.setAutoReverse(true);
//        pathTransition.play();
//        circle3.setCenterX(e.getX());
//        circle3.setCenterY(e.getY());
//        b.set(false);
//        e.consume();
//        }
//        });
//        bar1.setScaleX(0.5);
////        bar1.setTranslateX(20);
//        root.getChildren().addAll(circle, circle1, circle2, bar1);


//        System.out.println(b);
//        if (!b.get()) {
//            while (circle3.getCenterY() != y && circle3.getCenterX() != x) {
//                move(circle3, x, y);
//            }
//        }

//new AnimationTimer() {
//@Override
//public void handle(long l) {
//        bar1.setProgress(bar1.getProgress() - 0.01);
//        }
//        }.start();