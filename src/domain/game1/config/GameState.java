package domain.game1.config;

import domain.game1.config.InformationExpertPattern.MonsterInfo;
import domain.game1.config.InformationExpertPattern.PlayerInfo;
import domain.game1.game.Hall;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private List<Hall> hallList;
    private int hallNum;
    private List<MonsterInfo> monsterInfo;
    private PlayerInfo playerInfo;
    private int time;

    public GameState(List<Hall> hallList, int hallNum, List<MonsterInfo> monsterInfo, PlayerInfo playerInfo, int time) {
        this.hallList = hallList;
        this.hallNum = hallNum;
        this.monsterInfo = monsterInfo;
        this.playerInfo = playerInfo;
        this.time = time;

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

    public PlayerInfo getPlayerInfo(){
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }


}

