package ir.ac.kntu.level;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.Group;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Level {
    private int waves;
    private int currantWave;
    private int enemyNum;
    private List<List<EnemySoldier>> enemyWaves;
//    private List<Material> items;
    private List<String> items;

    public Level(int waves, int enemyNum, List<List<EnemySoldier>> enemyWaves, List<String> items) {
        this.waves = waves;
        currantWave = 0;
        this.enemyNum = enemyNum;
        this.enemyWaves = enemyWaves;
        this.items = items;
    }

    public void setWaves(Game game, Group root) {
        if (currantWave < waves ) {
            AtomicBoolean allDead = new AtomicBoolean(true);
            game.getEnemySoldiers().forEach(en -> {
                if (en.getBar().getProgress() > 0.1) {
                    allDead.set(false);
                }
            });
            if (allDead.get()) {
                currantWave++;
                if (currantWave < waves) {
                    game.setEnemySoldiers(enemyWaves.get(currantWave));
                    game.getEnemySoldiers().forEach(en -> root.getChildren().add(en.getShape()));
                    game.getEnemySoldiers().forEach(en -> root.getChildren().add(en.getBar()));
                }
            }
        } else {
            System.out.println("it's done");
        }
    }

    public boolean itsDone(Game game) {
        return currantWave >= waves || game.getItems().isEmpty();
    }

    public boolean won() {
        return currantWave >= waves;
    }

    public int getWaves() {
        return waves;
    }

    public int getEnemyNum() {
        return enemyNum;
    }

    public List<List<EnemySoldier>> getEnemyWaves() {
        return enemyWaves;
    }

    public List<String> getItems() {
        return items;
    }

}
