package ir.ac.kntu.level;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.soldier.EnemySoldier;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Level {
    private int waves;
    private int currantWave;
    private int enemyNum;
    private List<List<EnemySoldier>> enemyWaves;
    private List<Material> items;

    public Level(int waves, int enemyNum, List<List<EnemySoldier>> enemyWaves, List<Material> items) {
        this.waves = waves;
        currantWave = 0;
        this.enemyNum = enemyNum;
        this.enemyWaves = enemyWaves;
        this.items = items;
    }

    public void setWaves(Game game) {
        if (currantWave < waves) {
            AtomicBoolean allDead = new AtomicBoolean(true);
            game.getEnemySoldiers().forEach(en -> {
                if (en.getBar().getProgress() > 0.1) {
                    allDead.set(false);
                }
            });
            if (allDead.get()) {
                currantWave++;
                game.setEnemySoldiers(enemyWaves.get(currantWave));
            }
        } else {
            System.out.println("it's done");
        }
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

    public List<Material> getItems() {
        return items;
    }

}
