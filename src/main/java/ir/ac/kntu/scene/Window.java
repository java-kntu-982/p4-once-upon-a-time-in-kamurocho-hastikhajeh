package ir.ac.kntu.scene;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.material.item.Container;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.soldier.EnemySoldier;
import ir.ac.kntu.soldier.InternalSoldier;
import ir.ac.kntu.soldier.enemy.RedSoldier;
import ir.ac.kntu.soldier.internal.DaigoDojima;
import ir.ac.kntu.soldier.internal.FutoshiShimano;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Window extends Application {
    private static final int HEIGHT = 600, WIDTH = 900;
    private Game game;
    private Group root;
    private Scene scene;

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    @Override
    public void init() throws Exception {
        game = new Game(new ArrayList<InternalSoldier>(), new ArrayList<EnemySoldier>(), new ArrayList<Material>(), 100);

        game.getInternalSoldiers().add(new DaigoDojima(650,200));
        game.getInternalSoldiers().add(new FutoshiShimano(650,400));
        game.getEnemySoldiers().add(new RedSoldier(100,200));
        game.getEnemySoldiers().add(new RedSoldier(100,300));
        game.getItems().add(new Container(700, 300));
    }


    InternalSoldier internalSoldier;
    boolean b = false;
    @Override
    public void start(Stage stage) throws Exception {
        root = new Group();
        scene = new Scene(root,800,600);
        stage.setScene(scene);

        for (InternalSoldier in: game.getInternalSoldiers()) {
            in.getShape().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                internalSoldier = in;
                b = true;
            });
            in.getText().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                internalSoldier = in;
                b = true;
            });
        }
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (b) {
                internalSoldier.setXYSpeed(e.getX(),e.getY(), true);
                b = false;
                e.consume();
            }
        });
        game.getInternalSoldiers().forEach(in -> root.getChildren().add(in.getShape()));
        game.getInternalSoldiers().forEach(in -> root.getChildren().add(in.getText()));
        game.getInternalSoldiers().forEach(in -> root.getChildren().add(in.getBar()));
        game.getItems().forEach(it -> root.getChildren().add(it.getShape()));
        game.getItems().forEach(it -> root.getChildren().add(it.getText()));
        game.getEnemySoldiers().forEach(en -> root.getChildren().add(en.getShape()));
        game.getEnemySoldiers().forEach(en -> root.getChildren().add(en.getBar()));
        stage.show();

    new AnimationTimer() {
        @Override
        public void handle(long l) {
            game.getInternalSoldiers().forEach(InternalSoldier::setXAndY);
            game.getEnemySoldiers().forEach(EnemySoldier::setXAndY);
            game.getEnemySoldiers().forEach(en -> en.setXYSpeed(game.getItems()));
            game.getEnemySoldiers().forEach(EnemySoldier::move);
            game.getInternalSoldiers().forEach(InternalSoldier::goForEnemy);
            game.getInternalSoldiers().forEach(in -> in.makeEnemyInFieldOfViewList(game.getEnemySoldiers()));
            game.getInternalSoldiers().forEach(InternalSoldier::makeEnemyInAttackRange);
            game.getInternalSoldiers().forEach(InternalSoldier::move);
            game.getInternalSoldiers().forEach(InternalSoldier::attack);
            game.getEnemySoldiers().forEach(en -> en.makeInternalInAttackRange(game.getInternalSoldiers()));
            game.getEnemySoldiers().forEach(EnemySoldier::attack);
            game.getEnemySoldiers().forEach(en -> en.fadeIfDead(game.getEnemySoldiers()));
        }
    }.start();

    }

    public static void main(String[] args) {
        launch();
    }

}
