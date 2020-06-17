package ir.ac.kntu.scene;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.level.Level;
import ir.ac.kntu.level.Level1;
import ir.ac.kntu.level.Level2;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.soldier.EnemySoldier;
import ir.ac.kntu.soldier.InternalSoldier;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    private static final int HEIGHT = 600, WIDTH = 900;
    private Game game;

    private GridPane startMenuPane;
    private BorderPane missionsPane;
    private BorderPane organizationPane;
    private BorderPane trainPane;
    private BorderPane fortifyHQPane;
    private Group gamePane;
    private Group resultPane;

    private Text moneyTrain = new Text();
    private Text moneyFortify = new Text();

    private Scene startMenuScene;
    private Scene missionsScene;
    private Scene organizationScene;
    private Scene trainScene;
    private Scene fortifyHQScene;
    private Scene gameScene;
    private Scene resultScene;

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        initialSetting(stage);
        stage.show();
    }

    private void initialSetting(Stage stage) {
        game = new Game(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1000d);
        startMenu(stage);
        missions(stage);
        organization(stage);
        train(stage);
        fortifyHQ(stage);
        stage.setScene(startMenuScene);
    }

    public void gameControl(Scene scene) {
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
    }

    InternalSoldier internalSoldier;
    boolean b = false;
    public void game(Stage stage) {
        gamePane = new Group();
        gameScene = new Scene(gamePane, 900, 600);
        if (game.isStarted()) {
            game.getInternalSoldiers().forEach(in -> gamePane.getChildren().add(in.getShape()));
            game.getInternalSoldiers().forEach(in -> gamePane.getChildren().add(in.getText()));
            game.getInternalSoldiers().forEach(in -> gamePane.getChildren().add(in.getBar()));
            game.getItems().forEach(it -> gamePane.getChildren().add(it.getShape()));
            game.getItems().forEach(it -> gamePane.getChildren().add(it.getText()));
            game.getEnemySoldiers().forEach(en -> gamePane.getChildren().add(en.getShape()));
            game.getEnemySoldiers().forEach(en -> gamePane.getChildren().add(en.getBar()));
            game.getBlocks().forEach(bl -> gamePane.getChildren().add(bl.getShape()));
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    game.getInternalSoldiers().forEach(InternalSoldier::setXAndY);
                    game.getEnemySoldiers().forEach(EnemySoldier::setXAndY);
//                    try {
                        game.getEnemySoldiers().forEach(en -> en.setXYSpeed(game.getItems()));
//                    } catch (NullPointerException ignored) {
//                    }
                    game.getEnemySoldiers().forEach(EnemySoldier::move);
                    game.getInternalSoldiers().forEach(InternalSoldier::goForEnemy);
                    game.getInternalSoldiers().forEach(in -> in.makeEnemyInFieldOfViewList(game.getEnemySoldiers()));
                    game.getInternalSoldiers().forEach(InternalSoldier::makeEnemyInAttackRange);
                    game.getInternalSoldiers().forEach(InternalSoldier::move);
                    game.getInternalSoldiers().forEach(InternalSoldier::attack);
                    game.getEnemySoldiers().forEach(en -> en.makeInternalInAttackRange(game.getInternalSoldiers()));
                    game.getEnemySoldiers().forEach(EnemySoldier::attack);
                    game.getEnemySoldiers().forEach(en -> en.fadeIfDead(game));
                    game.getInternalSoldiers().forEach(InternalSoldier::fadeIfDead);
                    game.getLevel().setWaves(game, gamePane);
                    if (game.getLevel().itsDone(game)) {
                        result(stage);
                        stage.setScene(resultScene);
                        stop();
                    }
                }
            };
            if (!game.getLevel().itsDone(game)) {
                timer.start();
            }
        }
    }

    public void result(Stage stage) {
        resultPane = new Group();
        resultScene = new Scene(resultPane, 900, 600);
        Text result = new Text();
        result.setX(300);
        result.setY(200);
        result.setFont(Font.font("Vardana", 40));
        if (game.getLevel().won()) {
            result.setText("you won :)");
        } else {
            result.setText("you lost :(");
        }

        Text earned = new Text("you have "+String.format("%.2f",game.getMoney())+" money now");
        earned.setX(300);
        earned.setY(350);
        earned.setFont(Font.font("Vardana", 27));

        Button back = new Button("back to menu");
        back.setOnAction(e -> {
            game.reset();
            stage.setScene(startMenuScene);
        });
        back.setLayoutX(400);
        back.setLayoutY(400);
        moneyTrain.setText(String.format("%.2f",game.getMoney()));
        moneyFortify.setText(String.format("%.2f",game.getMoney()));
        resultPane.getChildren().addAll(result, earned, back);
    }

    private void startMenu(Stage stage) {
        startMenuPane = new GridPane();
        startMenuScene = new Scene(startMenuPane, 900, 600);
        startMenuPane.setPadding(new Insets(20, 10, 10, 20));
        startMenuPane.setVgap(5);
        startMenuPane.setHgap(5);

        Button missions = new Button("Missions");
        missions.setStyle("-fx-pref-width: 200px;");
        missions.setOnAction(e -> stage.setScene(missionsScene));
        startMenuPane.add(missions,0,0);

        Button organization = new Button("Organization");
        organization.setStyle("-fx-pref-width: 200px;");
        organization.setOnAction(e -> stage.setScene(organizationScene));
        startMenuPane.add(organization,0,1);

        Button train = new Button("Train");
        train.setStyle("-fx-pref-width: 200px;");
        train.setOnAction(e -> stage.setScene(trainScene));
        startMenuPane.add(train,0,2);

        Button fortifyHQ = new Button("Fortify HQ");
        fortifyHQ.setStyle("-fx-pref-width: 200px;");
        fortifyHQ.setOnAction(e -> stage.setScene(fortifyHQScene));
        startMenuPane.add(fortifyHQ,0,3);

        startMenuPane.setStyle("-fx-background-color: #14227d;");
    }

    private void missions(Stage stage) {
        missionsPane = new BorderPane();
        missionsScene = new Scene(missionsPane, 900, 600);
        GridPane levels = new GridPane();
        GridPane levelInfo = new GridPane();
        GridPane back = new GridPane();
        missionsPane.setBottom(back);
        missionsPane.setRight(levelInfo);
        missionsPane.setCenter(levels);
        levels.setPadding(new Insets(50,10,10,20));
        levels.setVgap(20);
        levels.setHgap(40);

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        levelInfo.setPadding(new Insets(120,100,50,50));
        levelInfo.setMaxSize(400,400);
        levelInfo.setMinSize(400,400);
        Text info = new Text();
        levelInfo.getChildren().add(info);

        Button level1 = new Button("level One");
        level1.setStyle("-fx-pref-width: 120px");
        level1.setOnAction(e -> putLvLInfo(info, 1));
        levels.add(level1, 0, 0);
        Button start1 = new Button("start");
        start1.setStyle("-fx-pref-width: 120px");
        start1.setOnAction(e -> {
            game.setLevel(new Level1(game));
            game.setEnemySoldiers(game.getLevel().getEnemyWaves().get(0));
            game.setStart(true);
            game.getLevel().makeItems(game);
            game(stage);
            gameControl(gameScene);
            stage.setScene(gameScene);
        });
        levels.add(start1, 1, 0);

        Button level2 = new Button("level Two");
        level2.setStyle("-fx-pref-width: 120px");
        level2.setOnAction(e -> putLvLInfo(info, 2));
        levels.add(level2, 0, 1);
        Button start2 = new Button("start");
        start2.setStyle("-fx-pref-width: 120px");
        start2.setOnAction(e -> {
            game.setLevel(new Level2(game));
            game.setEnemySoldiers(game.getLevel().getEnemyWaves().get(0));
            game.setStart(true);
            game.getLevel().makeItems(game);
            game(stage);
            gameControl(gameScene);
            stage.setScene(gameScene);
        });
        levels.add(start2, 1, 1);

    }

    public void putLvLInfo(Text info, int num) {
        switch (num) {
            case 1:
                info.setText("level one\n180 soldiers in 6 waves\nContainer + Truck");
                break;
            case 2:
                info.setText("level two\n280 soldiers in 8 waves\nContainer + Van");
                break;
        }
        info.setFont(Font.font("Vardana",27));
    }

    private void organization(Stage stage) {
        organizationPane = new BorderPane();
        organizationPane.setStyle("-fx-background-color: #88cef0;");
        organizationScene = new Scene(organizationPane, 900, 600);
        GridPane chooseInternal = new GridPane();
        GridPane internalStack = new GridPane();
        GridPane soldierInfo = new GridPane();
        GridPane back = new GridPane();
        organizationPane.setTop(internalStack);
        organizationPane.setCenter(chooseInternal);
        organizationPane.setRight(soldierInfo);
        organizationPane.setBottom(back);
        chooseInternal.setPadding(new Insets(20, 10, 10, 20));
        chooseInternal.setVgap(20);
        chooseInternal.setHgap(20);

        internalStack.setPadding(new Insets(20, 20,20,20));
        internalStack.setMinSize(150, 150);
        internalStack.setVgap(5);
        internalStack.setHgap(5);

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        soldierInfo.setPadding(new Insets(90,100,50,50));
        soldierInfo.setMaxSize(300,300);
        soldierInfo.setMinSize(300,300);
        Text info = new Text();
        soldierInfo.getChildren().add(info);

        ArrayList<Button> team = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String name = "empty";
            if (game.getInternalSoldiers().size() > i && game.getInternalSoldiers().get(i) != null) {
                name = game.getInternalSoldiers().get(i).getClass().getSimpleName();
            }
            Button button = new Button(name);
            button.setStyle("-fx-pref-width: 100px;-fx-background-color: #69ad9c");
            team.add(button);
            internalStack.add(team.get(i), i, 0);
        }

        Button daigoDojima = new Button("DaigoDojima");
        daigoDojima.setStyle("-fx-pref-width: 120px;");
        daigoDojima.setOnAction(e -> putInOrGetOutOfStack(team,daigoDojima, game.getAllInternalSoldiers().get(0), info));
        chooseInternal.add(daigoDojima,0,3);

        Button futoshiShimano = new Button("FutoshiShimano");
        futoshiShimano.setStyle("-fx-pref-width: 120px;");
        futoshiShimano.setOnAction(e -> putInOrGetOutOfStack(team,futoshiShimano, game.getAllInternalSoldiers().get(1), info));
        chooseInternal.add(futoshiShimano,1,3);

        Button goroMajima = new Button("GoroMajima");
        goroMajima.setStyle("-fx-pref-width: 120px;");
        goroMajima.setOnAction(e -> putInOrGetOutOfStack(team,goroMajima, game.getAllInternalSoldiers().get(2), info));
        chooseInternal.add(goroMajima,2,3);

        Button jiroKawara = new Button("JiroKawara");
        jiroKawara.setStyle("-fx-pref-width: 120px;");
        jiroKawara.setOnAction(e -> putInOrGetOutOfStack(team,jiroKawara, game.getAllInternalSoldiers().get(3), info));
        chooseInternal.add(jiroKawara,0,4);

        Button kaoruSayama = new Button("KaoruSayama");
        kaoruSayama.setStyle("-fx-pref-width: 120px;");
        kaoruSayama.setOnAction(e -> putInOrGetOutOfStack(team,kaoruSayama, game.getAllInternalSoldiers().get(4), info));
        chooseInternal.add(kaoruSayama,1,4);

        Button makotoDate = new Button("MakotoDate");
        makotoDate.setStyle("-fx-pref-width: 120px;");
        makotoDate.setOnAction(e -> putInOrGetOutOfStack(team,makotoDate, game.getAllInternalSoldiers().get(5), info));
        chooseInternal.add(makotoDate,2,4);

        Button osamuKashiwagi = new Button("OsamuKashiwagi");
        osamuKashiwagi.setStyle("-fx-pref-width: 120px;");
        osamuKashiwagi.setOnAction(e -> putInOrGetOutOfStack(team,osamuKashiwagi, game.getAllInternalSoldiers().get(6), info));
        chooseInternal.add(osamuKashiwagi,0,5);

        Button ryoTakashima = new Button("RyoTakashima");
        ryoTakashima.setStyle("-fx-pref-width: 120px;");
        ryoTakashima.setOnAction(e -> putInOrGetOutOfStack(team,ryoTakashima, game.getAllInternalSoldiers().get(7), info));
        chooseInternal.add(ryoTakashima,1,5);

        Button ryujiGoda = new Button("RyujiGoda");
        ryujiGoda.setStyle("-fx-pref-width: 120px;");
        ryujiGoda.setOnAction(e -> putInOrGetOutOfStack(team,ryujiGoda, game.getAllInternalSoldiers().get(8), info));
        chooseInternal.add(ryujiGoda,2,5);

        Button shintaroKazama = new Button("ShintaroKazama");
        shintaroKazama.setStyle("-fx-pref-width: 120px;");
        shintaroKazama.setOnAction(e -> putInOrGetOutOfStack(team,shintaroKazama, game.getAllInternalSoldiers().get(9), info));
        chooseInternal.add(shintaroKazama,0,6);

        Button soheiDojima = new Button("SoheiDojima");
        soheiDojima.setStyle("-fx-pref-width: 120px;");
        soheiDojima.setOnAction(e -> putInOrGetOutOfStack(team,soheiDojima, game.getAllInternalSoldiers().get(10), info));
        chooseInternal.add(soheiDojima,1,6);

        Button taigaSaejima = new Button("TaigaSaejima");
        taigaSaejima.setStyle("-fx-pref-width: 120px;");
        taigaSaejima.setOnAction(e -> putInOrGetOutOfStack(team,taigaSaejima,game.getAllInternalSoldiers().get(11), info));
        chooseInternal.add(taigaSaejima,2,6);

    }

    public void putInOrGetOutOfStack(ArrayList<Button> team, Button internalSoldier, InternalSoldier newSoldier, Text info) {
        info.setText(internalSoldier.getText()+"\nHealth: "+newSoldier.getHealth().toString()+"\nattack: "+newSoldier.getAttack().toString()+"\nlevel: "+newSoldier.getLvl());
        info.setFont(Font.font("Vardana",30));

        boolean flag = true;
        for (InternalSoldier internal: game.getInternalSoldiers()) {
            if (internal.getClass().getSimpleName().equals(internalSoldier.getText())) {
                flag = false;
            }
        }
        if (flag) {
            for (Button button: team) {
                if (button.getText().equals("empty")) {
                    game.getInternalSoldiers().add(newSoldier);
                    button.setText(internalSoldier.getText());
                    break;
                }
            }
        } else {
            game.getInternalSoldiers().removeIf(in -> in.getClass().equals(newSoldier.getClass()));
            for (int i = 0; i < team.size(); i++) {
                if (!flag && team.get(i).getText().equals(internalSoldier.getText())) {
                    team.get(i).setText("empty");
                    flag = true;
                    continue;
                }
                if (flag) {
                    team.get(i-1).setText(team.get(i).getText());
                    if (i == team.size() - 1) {
                        team.get(i).setText("empty");
                    }
                }
            }
        }
    }

    private void train(Stage stage) {
        trainPane = new BorderPane();
        trainScene = new Scene(trainPane, 900, 600);
        GridPane soldierList = new GridPane();
        GridPane soldierInfo = new GridPane();
        AnchorPane moneyPane = new AnchorPane();
        GridPane back = new GridPane();
        trainPane.setBottom(back);
        trainPane.setTop(moneyPane);
        trainPane.setRight(soldierInfo);
        trainPane.setCenter(soldierList);
        soldierList.setPadding(new Insets(20, 10, 10, 20));
        soldierList.setVgap(10);
        soldierList.setHgap(50);

        moneyPane.setPadding(new Insets(30,30,30,30));
        moneyTrain.setText(game.getMoney().toString());
        moneyPane.getChildren().add(moneyTrain);
        AnchorPane.setTopAnchor(moneyTrain, 10.0);
        AnchorPane.setLeftAnchor(moneyTrain, 790.0);
        moneyTrain.setFont(Font.font("Vardana",18));
        Text error = new Text();
        moneyPane.getChildren().add(error);
        AnchorPane.setTopAnchor(error, 10.0);
        AnchorPane.setLeftAnchor(error, 510.0);
        error.setFont(Font.font("Vardana",18));

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        soldierInfo.setPadding(new Insets(100,100,50,0));
        soldierInfo.setMaxSize(300,300);
        soldierInfo.setMinSize(300,300);
        Text info = new Text();
        soldierInfo.getChildren().add(info);

        Button daigoDojima = new Button("daigoDojima");
        daigoDojima.setStyle("-fx-pref-width: 120px;");
        daigoDojima.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(0), info));
        soldierList.add(daigoDojima,0,0);
        Text ddMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(0).getLvl()*10));
        soldierList.add(ddMoney,2,0);
        Button lvlUpDD = new Button("lvl up");
        lvlUpDD.setOnAction(e -> internalLvlUp(info, moneyTrain, ddMoney, error, 0));
        soldierList.add(lvlUpDD,1,0);

        Button futoshiShimano = new Button("futoshiShimano");
        futoshiShimano.setStyle("-fx-pref-width: 120px;");
        futoshiShimano.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(1), info));
        soldierList.add(futoshiShimano,0,1);
        Text fsMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(1).getLvl()*10));
        soldierList.add(fsMoney,2,1);
        Button lvlUpFS = new Button("lvl up");
        lvlUpFS.setOnAction(e -> internalLvlUp(info, moneyTrain, fsMoney, error, 1));
        soldierList.add(lvlUpFS,1,1);

        Button goroMajima = new Button("goroMajima");
        goroMajima.setStyle("-fx-pref-width: 120px;");
        goroMajima.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(2), info));
        soldierList.add(goroMajima,0,2);
        Text gmMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(2).getLvl()*10));
        soldierList.add(gmMoney,2,2);
        Button lvlUpGM = new Button("lvl up");
        lvlUpGM.setOnAction(e -> internalLvlUp(info, moneyTrain, gmMoney, error, 2));
        soldierList.add(lvlUpGM,1,2);

        Button jiroKawara = new Button("jiroKawara");
        jiroKawara.setStyle("-fx-pref-width: 120px;");
        jiroKawara.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(3), info));
        soldierList.add(jiroKawara,0,3);
        Text jkMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(3).getLvl()*10));
        soldierList.add(jkMoney,2,3);
        Button lvlUpJK = new Button("lvl up");
        lvlUpJK.setOnAction(e -> internalLvlUp(info, moneyTrain, jkMoney, error, 3));
        soldierList.add(lvlUpJK,1,3);

        Button kaoruSayama = new Button("kaoruSayama");
        kaoruSayama.setStyle("-fx-pref-width: 120px;");
        kaoruSayama.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(4), info));
        soldierList.add(kaoruSayama,0,4);
        Text ksMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(4).getLvl()*10));
        soldierList.add(ksMoney,2,4);
        Button lvlUpKS = new Button("lvl up");
        lvlUpKS.setOnAction(e -> internalLvlUp(info, moneyTrain, ksMoney, error, 4));
        soldierList.add(lvlUpKS,1,4);

        Button makotoDate = new Button("makotoDate");
        makotoDate.setStyle("-fx-pref-width: 120px;");
        makotoDate.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(5), info));
        soldierList.add(makotoDate,0,5);
        Text mdMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(5).getLvl()*10));
        soldierList.add(mdMoney,2,5);
        Button lvlUpMD = new Button("lvl up");
        lvlUpMD.setOnAction(e -> internalLvlUp(info, moneyTrain, mdMoney, error, 5));
        soldierList.add(lvlUpMD,1,5);

        Button osamuKashiwagi = new Button("osamuKashiwagi");
        osamuKashiwagi.setStyle("-fx-pref-width: 120px;");
        osamuKashiwagi.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(6), info));
        soldierList.add(osamuKashiwagi,0,6);
        Text okMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(6).getLvl()*10));
        soldierList.add(okMoney,2,6);
        Button lvlUpOK = new Button("lvl up");
        lvlUpOK.setOnAction(e -> internalLvlUp(info, moneyTrain, okMoney, error, 6));
        soldierList.add(lvlUpOK,1,6);

        Button ryoTakashima = new Button("ryoTakashima");
        ryoTakashima.setStyle("-fx-pref-width: 120px;");
        ryoTakashima.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(7), info));
        soldierList.add(ryoTakashima,0,7);
        Text rtMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(7).getLvl()*10));
        soldierList.add(rtMoney,2,7);
        Button lvlUpRT = new Button("lvl up");
        lvlUpRT.setOnAction(e -> internalLvlUp(info, moneyTrain, rtMoney, error, 7));
        soldierList.add(lvlUpRT,1,7);

        Button ryujiGoda = new Button("ryujiGoda");
        ryujiGoda.setStyle("-fx-pref-width: 120px;");
        ryujiGoda.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(8), info));
        soldierList.add(ryujiGoda,0,8);
        Text rgMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(8).getLvl()*10));
        soldierList.add(rgMoney,2,8);
        Button lvlUpRG = new Button("lvl up");
        lvlUpRG.setOnAction(e -> internalLvlUp(info, moneyTrain, rgMoney, error, 8));
        soldierList.add(lvlUpRG,1,8);

        Button shintaroKazama = new Button("shintaroKazama");
        shintaroKazama.setStyle("-fx-pref-width: 120px;");
        shintaroKazama.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(9), info));
        soldierList.add(shintaroKazama,0,9);
        Text skMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(9).getLvl()*10));
        soldierList.add(skMoney,2,9);
        Button lvlUpSK = new Button("lvl up");
        lvlUpSK.setOnAction(e -> internalLvlUp(info, moneyTrain, skMoney, error, 9));
        soldierList.add(lvlUpSK,1,9);

        Button soheiDojima = new Button("soheiDojima");
        soheiDojima.setStyle("-fx-pref-width: 120px;");
        soheiDojima.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(10), info));
        soldierList.add(soheiDojima,0,10);
        Text sdMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(10).getLvl()*10));
        soldierList.add(sdMoney,2,10);
        Button lvlUpSD = new Button("lvl up");
        lvlUpSD.setOnAction(e -> internalLvlUp(info, moneyTrain, sdMoney, error, 10));
        soldierList.add(lvlUpSD,1,10);

        Button taigaSaejima = new Button("taigaSaejima");
        taigaSaejima.setStyle("-fx-pref-width: 120px;");
        taigaSaejima.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(11), info));
        soldierList.add(taigaSaejima,0,11);
        Text tsMoney = new Text(Integer.toString(-game.getAllInternalSoldiers().get(11).getLvl()*10));
        soldierList.add(tsMoney,2,11);
        Button lvlUpTS = new Button("lvl up");
        lvlUpTS.setOnAction(e -> internalLvlUp(info, moneyTrain, tsMoney, error, 11));
        soldierList.add(lvlUpTS,1,11);

    }

    public void internalLvlUp(Text info, Text money, Text lvlUp, Text error, int index) {
        if (game.getMoney() < game.getAllInternalSoldiers().get(index).getLvl()*10) {
            error.setText("you don't have enough money -> ");
            FadeTransition fade = new FadeTransition(Duration.millis(3000), error);
            fade.setOnFinished(e -> error.setText(""));
            fade.play();
        } else {
            money.setText(Double.toString(game.getMoney() - game.getAllInternalSoldiers().get(index).getLvl()*10));
            moneyFortify.setText(Double.toString(game.getMoney() - game.getAllInternalSoldiers().get(index).getLvl()*10));
            game.setMoney(game.getMoney() - game.getAllInternalSoldiers().get(index).getLvl()*10);
            game.getAllInternalSoldiers().get(index).lvlUp();
            lvlUp.setText(Integer.toString(-game.getAllInternalSoldiers().get(index).getLvl()*10));
            putSoldierInfo(game.getAllInternalSoldiers().get(index), info);
        }
    }

    public void putSoldierInfo(InternalSoldier internal, Text info) {
        double health = internal.getHealth()*1.04, attack = internal.getAttack()*1.04;
        info.setText(internal.getClass().getSimpleName()+
                "\nHealth: "+internal.getHealth().toString()+" -> "+(int) health+
                "\nattack: "+internal.getAttack().toString()+" -> "+(int) attack+
                "\nlevel: "+internal.getLvl() + " -> " + (internal.getLvl()+1));
        info.setFont(Font.font("Vardana",27));
    }

    private void fortifyHQ(Stage stage) {
        fortifyHQPane = new BorderPane();
        fortifyHQScene = new Scene(fortifyHQPane, 900, 600);
        GridPane itemList = new GridPane();
        GridPane itemInfo = new GridPane();
        AnchorPane moneyPane = new AnchorPane();
        GridPane back = new GridPane();
        fortifyHQPane.setTop(moneyPane);
        fortifyHQPane.setCenter(itemList);
        fortifyHQPane.setRight(itemInfo);
        fortifyHQPane.setBottom(back);
        itemList.setPadding(new Insets(20, 10, 10, 20));
        itemList.setVgap(10);
        itemList.setHgap(50);

        moneyPane.setPadding(new Insets(30,30,30,30));
        moneyFortify.setText(game.getMoney().toString());
        moneyPane.getChildren().add(moneyFortify);
        AnchorPane.setTopAnchor(moneyFortify, 10.0);
        AnchorPane.setLeftAnchor(moneyFortify, 790.0);
        moneyFortify.setFont(Font.font("Vardana",18));
        Text error = new Text();
        moneyPane.getChildren().add(error);
        AnchorPane.setTopAnchor(error, 10.0);
        AnchorPane.setLeftAnchor(error, 510.0);
        error.setFont(Font.font("Vardana",18));

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        itemInfo.setPadding(new Insets(100,100,50,0));
        itemInfo.setMinSize(350,350);
        itemInfo.setMaxSize(350,350);
        Text info = new Text();
        itemInfo.getChildren().add(info);

        Button container = new Button("container");
        container.setStyle("-fx-pref-width: 120px;");
        container.setOnAction(e -> putItemInfo(game.getAllItems().get(1), info));
        itemList.add(container, 0, 0);
        Text cMoney = new Text(Integer.toString(-game.getAllItems().get(1).getLvl()*100));
        itemList.add(cMoney, 2, 0);
        Button lvlUpC = new Button("lvl up");
        lvlUpC.setOnAction(e -> itemLvlUp(info, moneyFortify, cMoney, error, 1));
        itemList.add(lvlUpC, 1, 0);

        Button van = new Button("van");
        van.setStyle("-fx-pref-width: 120px;");
        van.setOnAction(e -> putItemInfo(game.getAllItems().get(0), info));
        itemList.add(van, 0, 1);
        Text vMoney = new Text(Integer.toString(-game.getAllItems().get(0).getLvl()*100));
        itemList.add(vMoney, 2, 1);
        Button lvlUpV = new Button("lvl up");
        lvlUpV.setOnAction(e -> itemLvlUp(info, moneyFortify, vMoney, error, 0));
        itemList.add(lvlUpV, 1, 1);

        Button truck = new Button("truck");
        truck.setStyle("-fx-pref-width: 120px;");
        truck.setOnAction(e -> putItemInfo(game.getAllItems().get(2), info));
        itemList.add(truck, 0, 2);
        Text tMoney = new Text(Integer.toString(-game.getAllItems().get(2).getLvl()*100));
        itemList.add(tMoney, 2, 2);
        Button lvlUpT = new Button("lvl up");
        lvlUpT.setOnAction(e -> itemLvlUp(info, moneyFortify, tMoney, error, 2));
        itemList.add(lvlUpT, 1, 2);



    }

    public void itemLvlUp(Text info, Text money, Text lvlUp, Text error, int index) {
        if (game.getMoney() < game.getAllItems().get(index).getLvl()*100) {
            error.setText("you don't have enough money -> ");
            FadeTransition fade = new FadeTransition(Duration.millis(3000), error);
            fade.setOnFinished(e -> error.setText(""));
            fade.play();
        } else {
            money.setText(Double.toString(game.getMoney() - game.getAllItems().get(index).getLvl()*100));
            moneyTrain.setText(Double.toString(game.getMoney() - game.getAllItems().get(index).getLvl()*100));
            game.setMoney(game.getMoney() - game.getAllItems().get(index).getLvl()*100);
            game.getAllItems().get(index).lvlUp();
            lvlUp.setText(Integer.toString(-game.getAllItems().get(index).getLvl()*100));
            putItemInfo(game.getAllItems().get(index), info);
        }
    }

    public void putItemInfo(Material item, Text info) {
        int durability = (int) (item.getDurability()*1.1);
        info.setText(item.getClass().getSimpleName()+
                "\nDurability: "+item.getDurability()+" -> "+ durability+
                "\nlevel: "+item.getLvl()+" -> "+(item.getLvl()+1));
        info.setFont(Font.font("Vardana", 27));
    }



}
