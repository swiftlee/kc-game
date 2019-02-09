package net.jmdev.game;

import net.jmdev.Connection.Mount;
import net.jmdev.util.Scoreboard;
import net.jmdev.util.TextUtils;

import org.bukkit.scheduler.BukkitTask;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/10/2017 | 00:53
 * __________________
 *
 *  [2016] J&M Plugin Development 
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of J&M Plugin Development and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to J&M Plugin Development
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from J&M Plugin Development.
 */
public class GamePlayer implements Cloneable {

    //handles all player data, NOT champion data (e.g. out-of-game currency)

    public HashMap<UUID, Long> recentAttacked = new HashMap<>();
    public long lastAttack = 0;
    public long basicAttack = 0;
    public boolean asgerPassive = false;
    public long hearthstone = 0;
    public boolean xerolEternalHunger = false;
    public boolean resurrection = false;
    public String[] shopItems = {"", "", "", "", "", ""};
    public int materialSpirit = 0;
    public int ironWillDamage = 0;
    public int ironWillHealth = 0;
    boolean winner = false;
    public boolean firstPlayerKill = false;
    public boolean firstTurretKill = false;
    private UUID uuid;
    private Champion champion;
    private Team team;
    private byte level;
    private byte slaughterfestStacks = 0;
    private byte asgerUlt = 1;
    private byte runicDaggersStacks = 0;
    private int assists = 0;
    private int gold = 0;
    private int deaths = 0;
    private int kills = 0;
    private int xiauTsunStacks = 0;
    public long illusionGem = 0;
    public long pandoraStormbow = 0;
    public long reflectingShield = 0;
    public long vowOfProtector = 0;
    public boolean resistancePotion = false;
    public int goldPerSecond = 0;
    boolean healthPotion = false;
    public boolean powerPotion = false;
    private Mount mount;
    public BukkitTask hearthstoneTask = null;

    public GamePlayer(UUID uuid, Champion championType, Team team) {

        this.uuid = uuid;
        this.champion = championType;
        this.team = team;
        level = 1;

    }

    public int calculateEXP() {
        int exp = 0;
        if (winner) {
            exp += 30;
        } else {
            exp += 20;
        }
        if (firstPlayerKill) {
            exp += 50;
        }
        if (firstTurretKill) {
            exp += 50;
        }
        exp += kills * 30;
        return exp;
    }

    public int calculateGold() {
        int gold = 0;
        if (winner) {
            gold += 50;
        } else {
            gold += 20;
        }
        if (firstTurretKill) {
            gold += 50;
        }
        if (firstPlayerKill) {
            gold += 50;
        }
        gold+=kills*15;
        long time = (new Date().getTime()-Scoreboard.startDate);
        time/=1000;
        gold+=(time/60)*3;
        return gold;
    }

    public boolean hasItem(String name) {

        for (String s : shopItems) {
            if (s.equalsIgnoreCase(name))
                return true;

        }

        return false;

    }

    public int itemCount(String name) {

        int count = 0;

        for (String s : shopItems)
            if (s.equalsIgnoreCase(name))
                count++;

        return count;

    }

    public String getRecap(String coins, String exp) {
        return TextUtils.formatText("&8&l------------------------\n" +
                "&f&lReward Summary\n" +
                "&7Total earned gold: &6" + coins + "\n" +
                "&8&l------------------------");
    }


    public UUID getUuid() {

        return uuid;

    }

    public Champion getChampion() {

        return champion;

    }

    public void setChampion(Champion champion) {

        if (champion == null) {
            this.champion = null;
            return;
        }

        try {
            this.champion = (Champion) Class.forName("net.jmdev.champions." + champion.getName().replace(" ", "")).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Team getTeam() {

        return team;

    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public byte getSlaughterfestStacks() {
        return slaughterfestStacks;
    }

    public void setSlaughterfestStacks(byte slaughterfestStacks) {
        this.slaughterfestStacks = slaughterfestStacks;
    }

    public byte getAsgerUlt() {
        return asgerUlt;
    }

    public void setAsgerUlt(byte asgerUlt) {
        this.asgerUlt = asgerUlt;
    }

    public byte getRunicDaggersStacks() {
        return runicDaggersStacks;
    }

    public void setRunicDaggersStacks(byte runicDaggersStacks) {
        this.runicDaggersStacks = runicDaggersStacks;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getXiauTsunStacks() {
        return xiauTsunStacks;
    }

    public void setXiauTsunStacks(int xiauTsunStacks) {
        this.xiauTsunStacks = xiauTsunStacks;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }
}
