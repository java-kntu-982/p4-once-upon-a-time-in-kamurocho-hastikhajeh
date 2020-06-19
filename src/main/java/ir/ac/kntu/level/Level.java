package ir.ac.kntu.level;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.soldier.EnemySoldier;
import javafx.scene.Group;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Level {
    private int waves;
    private int currantWave;
    private int enemyNum;
    private List<List<EnemySoldier>> enemyWaves;

    public Level(int waves, int enemyNum, List<List<EnemySoldier>> enemyWaves) {
        this.waves = waves;
        currantWave = 0;
        this.enemyNum = enemyNum;
        this.enemyWaves = enemyWaves;
    }

    abstract public void makeItems(Game game);

    public void setWaves(Game game, Group root) {
        if (currantWave < waves ) {
            AtomicBoolean allDead = new AtomicBoolean(true);
            game.getEnemySoldiers().forEach(en -> {
                if (!en.isDead()) {
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
        }
    }

    public boolean itsDone(Game game) {
        final boolean[] allDead = {true};
        game.getInternalSoldiers().forEach(in -> {
            if (!in.isDead()) {
                allDead[0] = false;
            }
        });
        return currantWave >= waves || game.getItems().isEmpty() || allDead[0];
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

}
