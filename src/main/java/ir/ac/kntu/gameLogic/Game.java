package ir.ac.kntu.gameLogic;

import ir.ac.kntu.block.Block;
import ir.ac.kntu.level.Level;
import ir.ac.kntu.material.Van;
import ir.ac.kntu.material.item.Container;
import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.material.item.Truck;
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
    private Integer money;
    private List<InternalSoldier> allInternalSoldiers;
    private List<Material> allItems;
    private Level level;
    private boolean start;

    public Game(ArrayList<InternalSoldier> internalSoldiers, ArrayList<EnemySoldier> enemySoldiers, ArrayList<Material> items, ArrayList<Block> blocks, Integer money) {
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
        allInternalSoldiers.add(new TaigaSaejima(450,450));
        allItems = new ArrayList<>();
        allItems.add(new Van(850, 200));
        allItems.add(new Container(850, 350));
        allItems.add(new Truck(850, 450));
        start = false;
    }

    public void play(){
//        internalSoldiers.add(new DaigoDojima(500,200));
//        internalSoldiers.add(new DaigoDojima(500,300));
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        if (money < 0) {
            this.money = 0;
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
