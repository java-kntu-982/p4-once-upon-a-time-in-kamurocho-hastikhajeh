package ir.ac.kntu.level;

import ir.ac.kntu.gameLogic.Game;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.soldier.EnemySoldier;
import ir.ac.kntu.soldier.enemy.*;

import java.util.ArrayList;
import java.util.List;

public class Level2 extends Level {
    public Level2(Game game) {
        super(8, 281, new ArrayList<>());
        getEnemyWaves().add(makeWave123());
        getEnemyWaves().add(makeWave123());
        getEnemyWaves().add(makeWave123());
        getEnemyWaves().add(makeWave45());
        getEnemyWaves().add(makeWave45());
        getEnemyWaves().add(makeWave67());
        getEnemyWaves().add(makeWave67());
        getEnemyWaves().add(makeWave8());
    }

    public void makeItems(Game game) {
        game.getItems().add(0, game.getAllItems().get(0));
        game.getItems().add(1, game.getAllItems().get(1));
    }

    private List<EnemySoldier> makeWave123() {
        List<EnemySoldier> wave = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                wave.add(new RedSoldier(30 + 20*i, 150 + 15*(2*j+1)));
            }
        }
        return wave;
    }

    private List<EnemySoldier> makeWave45() {
        List<EnemySoldier> wave = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                wave.add(new GraySoldier(30 + 20*i, 150 + 15*(2*j+1)));
            }
        }
        return wave;
    }

    private List<EnemySoldier> makeWave67() {
        List<EnemySoldier> wave = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                wave.add(new YellowSoldier(30 + 20*i, 150 + 15*(2*j+1)));
            }
        }
        return wave;
    }

    private List<EnemySoldier> makeWave8() {
        List<EnemySoldier> wave = new ArrayList<>();
        wave.add(new BossLevel2(40, 300));
        return wave;
    }
}
