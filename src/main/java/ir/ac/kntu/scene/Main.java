package ir.ac.kntu.scene;

import ir.ac.kntu.gameLogic.Game;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

    private Scene startMenuScene;
    private Scene missionsScene;
    private Scene organizationScene;
    private Scene trainScene;
    private Scene fortifyHQScene;
    private Scene gameScene;
    private Scene resultScene;

    private Text moneyTrain = new Text();
    private Text moneyFortify = new Text();
    private Media music = new Media(new File("src/main/resources/522110__setuniman__cheeky-1t41b.wav").toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(music);

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
        stage.setTitle("once upon a time in kamurocho:)");
        stage.show();
    }

    private void initialSetting(Stage stage) {
        game = new Game(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1000d);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        try {
            startMenu(stage);
            missions(stage);
            organization(stage);
            train(stage);
            fortifyHQ(stage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stage.setScene(startMenuScene);
    }

    public void gameControl(Scene scene) {
        final InternalSoldier[] internalSoldier = new InternalSoldier[1];
        final boolean[] b = {false};
        for (InternalSoldier in: game.getInternalSoldiers()) {
            in.getShape().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                internalSoldier[0] = in;
                b[0] = true;
            });
            in.getText().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                internalSoldier[0] = in;
                b[0] = true;
            });
        }
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (b[0]) {
                internalSoldier[0].setXYSpeed(e.getX(),e.getY(), true);
                b[0] = false;
                e.consume();
            }
        });
    }

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
                    game.getEnemySoldiers().forEach(en -> en.setXYSpeed(game.getItems()));
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

    private void startMenu(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/Shinjuku Omoide-Yokocho.jpg"));
        startMenuPane = new GridPane();
        startMenuPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        startMenuScene = new Scene(startMenuPane, 900, 600);
        startMenuPane.setPadding(new Insets(40, 10, 10, 50));
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

    }

    private void missions(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/Black Rain Ikebukuro 池袋.jpg"));
        missionsPane = new BorderPane();
        missionsPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        missionsScene = new Scene(missionsPane, 900, 600);
        GridPane levels = new GridPane();
        GridPane levelInfo = new GridPane();
        GridPane back = new GridPane();
        missionsPane.setBottom(back);
        missionsPane.setRight(levelInfo);
        missionsPane.setCenter(levels);
        levels.setPadding(new Insets(125,10,10,50));
        levels.setVgap(20);
        levels.setHgap(40);

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        levelInfo.setPadding(new Insets(125,100,50,50));
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
            default:
                break;
        }
        info.setFill(Color.WHITE);
        info.setFont(Font.font("Vardana",27));
    }

    private void organization(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/Travel Japan Radiation  #JapanTravelCities.jpg"));
        organizationPane = new BorderPane();
        organizationPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        organizationScene = new Scene(organizationPane, 900, 600);
        GridPane chooseInternal = new GridPane();
        GridPane internalStack = new GridPane();
        GridPane soldierInfo = new GridPane();
        GridPane back = new GridPane();
        organizationPane.setTop(internalStack);
        organizationPane.setCenter(chooseInternal);
        organizationPane.setRight(soldierInfo);
        organizationPane.setBottom(back);
        chooseInternal.setPadding(new Insets(100, 10, 10, 20));
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

        Button daigoDojima = organizationButton("DaigoDojima", team, info, 0);
        chooseInternal.add(daigoDojima,0,0);

        Button futoshiShimano = organizationButton("FutoshiShimano", team, info, 1);
        chooseInternal.add(futoshiShimano,1,0);

        Button goroMajima = organizationButton("GoroMajima", team, info, 2);
        chooseInternal.add(goroMajima,2,0);

        Button jiroKawara = organizationButton("JiroKawara", team, info, 3);
        chooseInternal.add(jiroKawara,3,0);

        Button kaoruSayama = organizationButton("KaoruSayama", team, info, 4);
        chooseInternal.add(kaoruSayama,0,1);

        Button makotoDate = organizationButton("MakotoDate", team, info, 5);
        chooseInternal.add(makotoDate,1,1);

        Button osamuKashiwagi = organizationButton("OsamuKashiwagi", team, info, 6);
        chooseInternal.add(osamuKashiwagi,2,1);

        Button ryoTakashima = organizationButton("RyoTakashima", team, info, 7);
        chooseInternal.add(ryoTakashima,3,1);

        Button ryujiGoda = organizationButton("RyujiGoda", team, info, 8);
        chooseInternal.add(ryujiGoda,0,2);

        Button shintaroKazama = organizationButton("ShintaroKazama", team, info, 9);
        chooseInternal.add(shintaroKazama,1,2);

        Button soheiDojima = organizationButton("SoheiDojima", team, info, 10);
        chooseInternal.add(soheiDojima,2,2);

        Button taigaSaejima = organizationButton("TaigaSaejima", team, info, 11);
        chooseInternal.add(taigaSaejima,3,2);

        Button kojiShindo = organizationButton("KojiShindo", team, info, 15);
        chooseInternal.add(kojiShindo,0,3);

        Button sotaroKomaki = organizationButton("SotaroKomaki", team, info, 14);
        chooseInternal.add(sotaroKomaki,1,3);

        Button yukioTerada = organizationButton("YukioTerada", team, info, 12);
        chooseInternal.add(yukioTerada,2,3);

        Button tetsuTachibana = organizationButton("TetsuTachibana", team, info, 13);
        chooseInternal.add(tetsuTachibana,3,3);
    }

    public Button organizationButton(String name, ArrayList<Button> team, Text info, int index) {
        Button button = new Button(name);
        button.setStyle("-fx-pref-width: 120px;");
        button.setOnAction(e -> putInOrGetOutOfStack(team,button,game.getAllInternalSoldiers().get(index), info));
        return button;
    }

    public void putInOrGetOutOfStack(ArrayList<Button> team, Button internalSoldier, InternalSoldier newSoldier, Text info) {
        info.setText(internalSoldier.getText()+"\nHealth: "+newSoldier.getHealth().toString()+"\nattack: "+newSoldier.getAttack().toString()+"\nlevel: "+newSoldier.getLvl());
        info.setFont(Font.font("Vardana",30));
        info.setFill(Color.WHITE);

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

    public void trainPane(Stage stage, GridPane back, AnchorPane moneyPane, GridPane soldierInfo, GridPane soldierList, Text info, Text error) {
        trainPane.setBottom(back);
        trainPane.setTop(moneyPane);
        trainPane.setRight(soldierInfo);
        trainPane.setCenter(soldierList);
        soldierList.setPadding(new Insets(10, 10, 10, 20));
        soldierList.setVgap(3);
        soldierList.setHgap(50);

        moneyPane.setPadding(new Insets(10,50,0,30));
        moneyTrain.setText(String.format("%.2f",game.getMoney()));
        moneyTrain.setFill(Color.WHITE);
        moneyPane.getChildren().add(moneyTrain);
        AnchorPane.setTopAnchor(moneyTrain, 10.0);
        AnchorPane.setLeftAnchor(moneyTrain, 790.0);
        moneyTrain.setFont(Font.font("Vardana",18));
        moneyPane.getChildren().add(error);
        AnchorPane.setTopAnchor(error, 10.0);
        AnchorPane.setLeftAnchor(error, 510.0);
        error.setFont(Font.font("Vardana",18));

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        soldierInfo.setPadding(new Insets(150,100,50,0));
        soldierInfo.setMaxSize(350,350);
        soldierInfo.setMinSize(350,350);
        soldierInfo.getChildren().add(info);
    }

    private void train(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/Travel Japan Radiation  #JapanTravelCities.jpg"));
        trainPane = new BorderPane();
        trainPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        trainScene = new Scene(trainPane, 900, 600);
        GridPane soldierList = new GridPane();
        GridPane soldierInfo = new GridPane();
        AnchorPane moneyPane = new AnchorPane();
        GridPane back = new GridPane();
        Text error = new Text();
        Text info = new Text();
        trainPane(stage, back, moneyPane, soldierInfo, soldierList, info, error);

        Button daigoDojima = trainButton("daigoDojima", info, 0);
        soldierList.add(daigoDojima,0,0);
        Text ddMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(0).getLvl()*10));
        soldierList.add(ddMoney,2,0);
        Button lvlUpDD = trainLvlUpButton(info, ddMoney, error, 0);
        soldierList.add(lvlUpDD,1,0);

        Button futoshiShimano = trainButton("futoshiShimano", info, 1);
        soldierList.add(futoshiShimano,0,1);
        Text fsMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(1).getLvl()*10));
        soldierList.add(fsMoney,2,1);
        Button lvlUpFS = trainLvlUpButton(info, fsMoney, error, 1);
        soldierList.add(lvlUpFS,1,1);

        Button goroMajima = trainButton("goroMajima", info, 2);
        soldierList.add(goroMajima,0,2);
        Text gmMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(2).getLvl()*10));
        soldierList.add(gmMoney,2,2);
        Button lvlUpGM = trainLvlUpButton(info, gmMoney, error, 2);
        soldierList.add(lvlUpGM,1,2);

        Button jiroKawara = trainButton("jiroKawara", info, 3);
        soldierList.add(jiroKawara,0,3);
        Text jkMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(3).getLvl()*10));
        soldierList.add(jkMoney,2,3);
        Button lvlUpJK = trainLvlUpButton(info, jkMoney, error, 3);
        soldierList.add(lvlUpJK,1,3);

        Button kaoruSayama = trainButton("kaoruSayama", info, 4);
        soldierList.add(kaoruSayama,0,4);
        Text ksMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(4).getLvl()*10));
        soldierList.add(ksMoney,2,4);
        Button lvlUpKS = trainLvlUpButton(info, ksMoney, error, 4);
        soldierList.add(lvlUpKS,1,4);

        Button makotoDate = trainButton("makotoDate", info, 5);
        soldierList.add(makotoDate,0,5);
        Text mdMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(5).getLvl()*10));
        soldierList.add(mdMoney,2,5);
        Button lvlUpMD = trainLvlUpButton(info, mdMoney, error, 5);
        soldierList.add(lvlUpMD,1,5);

        Button osamuKashiwagi = trainButton("osamuKashiwagi", info, 6);
        soldierList.add(osamuKashiwagi,0,6);
        Text okMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(6).getLvl()*10));
        soldierList.add(okMoney,2,6);
        Button lvlUpOK = trainLvlUpButton(info, okMoney, error, 6);
        soldierList.add(lvlUpOK,1,6);

        Button ryoTakashima = trainButton("ryoTakashima", info, 7);
        soldierList.add(ryoTakashima,0,7);
        Text rtMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(7).getLvl()*10));
        soldierList.add(rtMoney,2,7);
        Button lvlUpRT = trainLvlUpButton(info, rtMoney, error, 7);
        soldierList.add(lvlUpRT,1,7);

        Button ryujiGoda = trainButton("ryujiGoda", info, 8);
        soldierList.add(ryujiGoda,0,8);
        Text rgMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(8).getLvl()*10));
        soldierList.add(rgMoney,2,8);
        Button lvlUpRG = trainLvlUpButton(info, rgMoney, error, 8);
        soldierList.add(lvlUpRG,1,8);

        Button shintaroKazama = trainButton("shintaroKazama", info, 9);
        soldierList.add(shintaroKazama,0,9);
        Text skMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(9).getLvl()*10));
        soldierList.add(skMoney,2,9);
        Button lvlUpSK = trainLvlUpButton(info, skMoney, error, 9);
        soldierList.add(lvlUpSK,1,9);

        Button soheiDojima = trainButton("soheiDojima", info, 10);
        soldierList.add(soheiDojima,0,10);
        Text sdMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(10).getLvl()*10));
        soldierList.add(sdMoney,2,10);
        Button lvlUpSD = trainLvlUpButton(info, sdMoney, error, 10);
        soldierList.add(lvlUpSD,1,10);

        Button taigaSaejima = trainButton("taigaSaejima", info, 11);
        soldierList.add(taigaSaejima,0,11);
        Text tsMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(11).getLvl()*10));
        soldierList.add(tsMoney,2,11);
        Button lvlUpTS = trainLvlUpButton(info, tsMoney, error, 11);
        soldierList.add(lvlUpTS,1,11);

        Button kojiShindo = trainButton("KojiShindo", info, 15);
        soldierList.add(kojiShindo,0,12);
        Text kshMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(15).getLvl()*10));
        soldierList.add(kshMoney,2,12);
        Button lvlUpKSH = trainLvlUpButton(info, kshMoney, error, 15);
        soldierList.add(lvlUpKSH,1,12);

        Button sotaroKomaki = trainButton("SotaroKomaki", info, 14);
        soldierList.add(sotaroKomaki,0,13);
        Text stkMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(14).getLvl()*10));
        soldierList.add(stkMoney,2,13);
        Button lvlUpSTK = trainLvlUpButton(info, stkMoney, error, 14);
        soldierList.add(lvlUpSTK,1,13);

        Button yukioTerada = trainButton("YukioTerada", info, 12);
        soldierList.add(yukioTerada,0,14);
        Text ytMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(12).getLvl()*10));
        soldierList.add(ytMoney,2,14);
        Button lvlUpYT = trainLvlUpButton(info, ytMoney, error, 12);
        soldierList.add(lvlUpYT,1,14);

        Button tetsuTachibana = trainButton("TetsuTachibana", info, 13);
        soldierList.add(tetsuTachibana,0,15);
        Text ttMoney = trainLvlUpText(Integer.toString(-game.getAllInternalSoldiers().get(13).getLvl()*10));
        soldierList.add(ttMoney,2,15);
        Button lvlUpTT = trainLvlUpButton(info, ttMoney, error, 13);
        soldierList.add(lvlUpTT,1,15);
    }

    public Button trainButton(String name, Text info, int index) {
        Button button = new Button(name);
        button.setStyle("-fx-pref-width: 120px;");
        button.setOnAction(e -> putSoldierInfo(game.getAllInternalSoldiers().get(index), info));
        return button;
    }

    public Text trainLvlUpText(String amount) {
        Text text = new Text(amount);
        text.setFill(Color.WHITE);
        return text;
    }

    public Button trainLvlUpButton(Text info, Text money, Text error, int index) {
        Button button = new Button("lvl up");
        button.setOnAction(e -> internalLvlUp(info, moneyTrain, money, error, index));
        return button;
    }

    public void internalLvlUp(Text info, Text money, Text lvlUp, Text error, int index) {
        if (game.getMoney() < game.getAllInternalSoldiers().get(index).getLvl()*10) {
            error.setText("you don't have enough money -> ");
            error.setFill(Color.WHITE);
            FadeTransition fade = new FadeTransition(Duration.millis(3000), error);
            fade.setOnFinished(e -> error.setText(""));
            fade.play();
        } else {
            money.setText(String.format("%.2f",game.getMoney() - game.getAllInternalSoldiers().get(index).getLvl()*10));
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
        info.setFill(Color.WHITE);
    }

    private void fortifyHQ(Stage stage) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/main/resources/Trendy photography night light rain 70+ ideas.jpg"));
        fortifyHQPane = new BorderPane();
        fortifyHQPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        fortifyHQScene = new Scene(fortifyHQPane, 900, 600);
        GridPane itemList = new GridPane();
        GridPane itemInfo = new GridPane();
        AnchorPane moneyPane = new AnchorPane();
        GridPane back = new GridPane();
        fortifyHQPane.setTop(moneyPane);
        fortifyHQPane.setCenter(itemList);
        fortifyHQPane.setRight(itemInfo);
        fortifyHQPane.setBottom(back);
        itemList.setPadding(new Insets(50, 10, 10, 20));
        itemList.setVgap(10);
        itemList.setHgap(50);

        moneyPane.setPadding(new Insets(30,30,30,30));
        moneyFortify.setText(game.getMoney().toString());
        moneyPane.getChildren().add(moneyFortify);
        AnchorPane.setTopAnchor(moneyFortify, 10.0);
        AnchorPane.setLeftAnchor(moneyFortify, 790.0);
        moneyFortify.setFill(Color.WHITE);
        moneyFortify.setFont(Font.font("Vardana",18));
        Text error = new Text();
        moneyPane.getChildren().add(error);
        AnchorPane.setTopAnchor(error, 10.0);
        AnchorPane.setLeftAnchor(error, 510.0);
        error.setFill(Color.WHITE);
        error.setFont(Font.font("Vardana",18));

        back.setPadding(new Insets(30,30,30,30));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(startMenuScene));
        back.getChildren().add(backButton);

        itemInfo.setPadding(new Insets(120,100,50,0));
        itemInfo.setMinSize(350,350);
        itemInfo.setMaxSize(350,350);
        Text info = new Text();
        info.setFill(Color.WHITE);
        itemInfo.getChildren().add(info);

        Button container = new Button("container");
        container.setStyle("-fx-pref-width: 120px;");
        container.setOnAction(e -> putItemInfo(game.getAllItems().get(1), info));
        itemList.add(container, 0, 0);
        Text cMoney = new Text(Integer.toString(-game.getAllItems().get(1).getLvl()*100));
        cMoney.setFill(Color.WHITE);
        itemList.add(cMoney, 2, 0);
        Button lvlUpC = new Button("lvl up");
        lvlUpC.setOnAction(e -> itemLvlUp(info, moneyFortify, cMoney, error, 1));
        itemList.add(lvlUpC, 1, 0);

        Button van = new Button("van");
        van.setStyle("-fx-pref-width: 120px;");
        van.setOnAction(e -> putItemInfo(game.getAllItems().get(0), info));
        itemList.add(van, 0, 1);
        Text vMoney = new Text(Integer.toString(-game.getAllItems().get(0).getLvl()*100));
        vMoney.setFill(Color.WHITE);
        itemList.add(vMoney, 2, 1);
        Button lvlUpV = new Button("lvl up");
        lvlUpV.setOnAction(e -> itemLvlUp(info, moneyFortify, vMoney, error, 0));
        itemList.add(lvlUpV, 1, 1);

        Button truck = new Button("truck");
        truck.setStyle("-fx-pref-width: 120px;");
        truck.setOnAction(e -> putItemInfo(game.getAllItems().get(2), info));
        itemList.add(truck, 0, 2);
        Text tMoney = new Text(Integer.toString(-game.getAllItems().get(2).getLvl()*100));
        tMoney.setFill(Color.WHITE);
        itemList.add(tMoney, 2, 2);
        Button lvlUpT = new Button("lvl up");
        lvlUpT.setOnAction(e -> itemLvlUp(info, moneyFortify, tMoney, error, 2));
        itemList.add(lvlUpT, 1, 2);

        Button powerShovel = new Button("power shovel");
        powerShovel.setStyle("-fx-pref-width: 120px;");
        powerShovel.setOnAction(e -> putItemInfo(game.getAllItems().get(3), info));
        itemList.add(powerShovel, 0, 3);
        Text pMoney = new Text(Integer.toString(-game.getAllItems().get(3).getLvl()*100));
        pMoney.setFill(Color.WHITE);
        itemList.add(pMoney, 2, 3);
        Button lvlUpP = new Button("lvl up");
        lvlUpP.setOnAction(e -> itemLvlUp(info, moneyFortify, pMoney, error, 3));
        itemList.add(lvlUpP, 1, 3);

        Button steelFramework = new Button("steel framework");
        steelFramework.setStyle("-fx-pref-width: 120px;");
        steelFramework.setOnAction(e -> putItemInfo(game.getAllItems().get(4), info));
        itemList.add(steelFramework, 0, 4);
        Text sMoney = new Text(Integer.toString(-game.getAllItems().get(4).getLvl()*100));
        sMoney.setFill(Color.WHITE);
        itemList.add(sMoney, 2, 4);
        Button lvlUpS = new Button("lvl up");
        lvlUpS.setOnAction(e -> itemLvlUp(info, moneyFortify, sMoney, error, 4));
        itemList.add(lvlUpS, 1, 4);

    }

    public void itemLvlUp(Text info, Text money, Text lvlUp, Text error, int index) {
        if (game.getMoney() < game.getAllItems().get(index).getLvl()*100) {
            error.setText("you don't have enough money -> ");
            FadeTransition fade = new FadeTransition(Duration.millis(3000), error);
            fade.setOnFinished(e -> error.setText(""));
            fade.play();
        } else {
            money.setText(String.format("%.2f",game.getMoney() - game.getAllItems().get(index).getLvl()*100));
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
