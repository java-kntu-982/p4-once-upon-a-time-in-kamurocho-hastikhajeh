package ir.ac.kntu.level;

import ir.ac.kntu.gameLogic.Game;
import ir.ac. kntu.soldier.EnemySoldier;
import ir.ac.kntu.soldier.enemy.BossLevel1;
import ir.ac.kntu.soldier.enemy.GraySoldier;
import ir.ac.kntu.soldier.enemy.RedSoldier;
import ir.ac.kntu.soldier.enemy.YellowSoldier;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends Level {

    public Level1(Game game) {
        super(6, 181, new ArrayList<>(), new ArrayList<>());
        getEnemyWaves().add(makeWave123());
        getEnemyWaves().add(makeWave123());
        getEnemyWaves().add(makeWave123());
        getEnemyWaves().add(makeWave45());
        getEnemyWaves().add(makeWave45());
        getEnemyWaves().add(makeWave6());
        game.getItems().add(0, game.getAllItems().get(2));
        game.getItems().add(1, game.getAllItems().get(1));
    }

    private List<EnemySoldier> makeWave123() {
        List<EnemySoldier> wave = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                wave.add(new RedSoldier(30 + 20*i, 150 + 15*(2*j+1)));
            }
        }
        return wave;
    }

    private List<EnemySoldier> makeWave45() {
        List<EnemySoldier> wave = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                wave.add(new GraySoldier(30 + 20*i, 150 + 15*(2*j+1)));
            }
        }
        return wave;
    }

    private List<EnemySoldier> makeWave6() {
        List<EnemySoldier> wave = new ArrayList<>();
        wave.add(new BossLevel1(40, 300));
        return wave;
    }

}
