package domain.config;

import domain.entity.monsters.MonsterInfo;
import domain.game.Hall;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private List<Hall> hallList;
    private int hallNum;
    private List<MonsterInfo> monsterInfo;

    public GameState(List<Hall> hallList, int hallNum, List<MonsterInfo> monsterInfo) {
        this.hallList = hallList;
        this.hallNum = hallNum;
        this.monsterInfo = monsterInfo;
    }

    public List<Hall> getHallsList(){
        return hallList;
    }

    public void setHallsList(List<Hall> hallList){
        this.hallList = hallList;
    }

    public int getHallNum(){
        return hallNum;
    }

    public void setHallNum(int hallNum){
        this.hallNum = hallNum;
    }

    public List<MonsterInfo> getMonsterInfo(){
        return monsterInfo;
    }

    public void setMonsterInfo(List<MonsterInfo> monsterInfo){
        this.monsterInfo = monsterInfo;
    }
}

