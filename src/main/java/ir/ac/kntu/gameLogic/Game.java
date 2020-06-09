package ir.ac.kntu.gameLogic;

import ir.ac.kntu.material.item.Material;
import ir.ac.kntu.soldier.EnemySoldier;
import ir.ac.kntu.soldier.InternalSoldier;
import ir.ac.kntu.soldier.internal.DaigoDojima;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<InternalSoldier> internalSoldiers;
    private List<EnemySoldier> enemySoldiers;
    private List<Material> items;
    private Integer money;

    public Game(ArrayList<InternalSoldier> internalSoldiers, ArrayList<EnemySoldier> enemySoldiers, ArrayList<Material> items, Integer money) {
        this.internalSoldiers = internalSoldiers;
        this.enemySoldiers = enemySoldiers;
        this.items = items;
        this.money = money;
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

    public List<Material> getItems() {
        return items;
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

}
