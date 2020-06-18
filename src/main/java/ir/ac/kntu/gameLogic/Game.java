package ir.ac.kntu.gameLogic;

import ir.ac.kntu.block.Block;
import ir.ac.kntu.level.Level;
import ir.ac.kntu.material.Van;
import ir.ac.kntu.material.Container;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.material.Truck;
import ir.ac.kntu.material.item.PowerShovel;
import ir.ac.kntu.material.item.SteelFramework;
import ir.ac.kntu.soldier.EnemySoldier;
import ir.ac.kntu.soldier.InternalSoldier;
import ir.ac.kntu.soldier.internal.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<InternalSoldier> internalSoldiers;
    private List<EnemySoldier> enemySoldiers;
    private List<Material> items;
    private List<Block> blocks;
    private Double money;
    private List<InternalSoldier> allInternalSoldiers;
    private List<Material> allItems;
    private Level level;
    private boolean start;

    public Game(ArrayList<InternalSoldier> internalSoldiers, ArrayList<EnemySoldier> enemySoldiers, ArrayList<Material> items, ArrayList<Block> blocks, Double money) {
        this.internalSoldiers = internalSoldiers;
        this.enemySoldiers = enemySoldiers;
        this.items = items;
        this.blocks = blocks;
        this.money = money;
        allInternalSoldiers = new ArrayList<>();
        allInternalSoldiers.add(new DaigoDojima(600,300));
        allInternalSoldiers.add(new FutoshiShimano(600, 200));
        allInternalSoldiers.add(new GoroMajima(600, 400));
        allInternalSoldiers.add(new JiroKawara(550, 150));
        allInternalSoldiers.add(new KaoruSayama(550, 250));
        allInternalSoldiers.add(new MakotoDate(550,350));
        allInternalSoldiers.add(new OsamuKashiwagi(550,450));
        allInternalSoldiers.add(new RyoTakashima(500,200));
        allInternalSoldiers.add(new RyujiGoda(500, 300));
        allInternalSoldiers.add(new ShintaroKazama(500,400));
        allInternalSoldiers.add(new SoheiDojima(450, 150));
        allInternalSoldiers.add(new TaigaSaejima(450,250));
        allInternalSoldiers.add(new YukioTerada(450,350));
        allInternalSoldiers.add(new TetsuTachibana(450,450));
        allInternalSoldiers.add(new SotaroKomaki(650,150));
        allInternalSoldiers.add(new KojiShindo(650,350));
        allItems = new ArrayList<>();
        allItems.add(new Van(800, 250));
        allItems.add(new Container(820, 150));
        allItems.add(new Truck(820, 250));
        allItems.add(new PowerShovel(820, 250));
        allItems.add(new SteelFramework(820, 250));
        start = false;
    }

    public void reset() {
        allInternalSoldiers.get(0).setXY(600, 300);
        allInternalSoldiers.get(1).setXY(600, 200);
        allInternalSoldiers.get(2).setXY(600, 400);
        allInternalSoldiers.get(3).setXY(550, 150);
        allInternalSoldiers.get(4).setXY(550, 250);
        allInternalSoldiers.get(5).setXY(550, 350);
        allInternalSoldiers.get(6).setXY(550, 450);
        allInternalSoldiers.get(7).setXY(500, 200);
        allInternalSoldiers.get(8).setXY(500, 300);
        allInternalSoldiers.get(9).setXY(500, 400);
        allInternalSoldiers.get(10).setXY(450, 150);
        allInternalSoldiers.get(11).setXY(450, 250);
        allInternalSoldiers.get(12).setXY(450, 350);
        allInternalSoldiers.get(13).setXY(450, 450);
        allInternalSoldiers.get(14).setXY(650, 150);
        allInternalSoldiers.get(15).setXY(650, 350);
        allInternalSoldiers.forEach(in -> {
            in.setDead(false);
            in.getShape().setOpacity(1);
            in.getText().setOpacity(1);
            in.getBar().setOpacity(1);
            in.getBar().setProgress(1);
            in.setxSpeed(0);
            in.setySpeed(0);
        });
        allItems.forEach(it -> {
            it.getShape().setOpacity(1);
            it.getText().setOpacity(1);
        });
        items = new ArrayList<>();
        start = false;
    }

    public List<InternalSoldier> getInternalSoldiers() {
        return internalSoldiers;
    }

    public List<EnemySoldier> getEnemySoldiers() {
        return enemySoldiers;
    }

    public void setEnemySoldiers(List<EnemySoldier> enemySoldiers) {
        this.enemySoldiers = enemySoldiers;
    }

    public List<Material> getItems() {
        return items;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<InternalSoldier> getAllInternalSoldiers() {
        return allInternalSoldiers;
    }

    public List<Material> getAllItems() {
        return allItems;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if (money < 0) {
            this.money = 0d;
        } else {
            this.money = money;
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isStarted() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
