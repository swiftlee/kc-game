package net.jmdev.util;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.listener.PlayerJoinListener;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/23/2017 | 17:35
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
public class Scoreboard {

    public static long startDate;

    public static void start3v3Board(Main plugin) {

        final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        final Date date = new Date();
        final long oldTime = System.currentTimeMillis() / 1000;
        startDate = new Date().getTime();

        new BukkitRunnable() {

            @Override
            public void run() {

                for (GamePlayer gamePlayer : Main.gamePlayers) {

                    if (Bukkit.getPlayer(gamePlayer.getUuid()).isOnline() && Bukkit.getPlayer(gamePlayer.getUuid()) != null) {

                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
                        Objective objective = board.registerNewObjective("test", "dummy");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName(TextUtils.formatText("&e&lKingdom Crashers"));

                        Score dateLine;
                        Score timeElapsed;
                        Score teamScore;
                        Score user1;
                        Score user2 = null;
                        Score user3 = null;
                        Score statsMana;
                        Score level;
                        Score exp;
                        Score gold;

                        dateLine = objective.getScore(TextUtils.formatText("&7Date: " + dateFormat.format(date)));
                        dateLine.setScore(15);
                        objective.getScore("§1").setScore(14);
                        timeElapsed = objective.getScore(TextUtils.formatText("&7Time Elapsed: " + TextUtils.formatTime((int) ((int) (System.currentTimeMillis() / 1000) - oldTime))));
                        timeElapsed.setScore(13);
                        objective.getScore("§2").setScore(12);
                        teamScore = objective.getScore(TextUtils.formatText("&7Your team: "));
                        teamScore.setScore(11);

                        boolean isSet = false;

                        if (gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                            for (Object uuid : Main.teams.get(0)) {

                                for (GamePlayer otherPlayer : Main.gamePlayers) {

                                    if (gamePlayer.getUuid() != otherPlayer.getUuid() && otherPlayer.getUuid().equals(uuid)) {

                                        if (!isSet) {
                                            user2 = objective.getScore(TextUtils.formatText("&7" + Bukkit.getPlayer((UUID) uuid).getName() + " &8| &a" + (int) otherPlayer.getChampion().getHealth() + "&fHP"));
                                            isSet = true;
                                        } else {
                                            user3 = objective.getScore(TextUtils.formatText("&7" + Bukkit.getPlayer((UUID) uuid).getName() + " &8| &a" + (int) otherPlayer.getChampion().getHealth() + "&fHP"));
                                            break;
                                        }

                                    }

                                }

                            }

                        } else {

                            for (Object uuid : Main.teams.get(1)) {

                                for (GamePlayer otherPlayer : Main.gamePlayers) {

                                    if (gamePlayer.getUuid() != otherPlayer.getUuid() && otherPlayer.getUuid().equals(uuid)) {

                                        if (!isSet) {
                                            user2 = objective.getScore(TextUtils.formatText("&7" + Bukkit.getPlayer((UUID) uuid).getName() + " &8| &a" + (int) otherPlayer.getChampion().getHealth() + "&fHP"));
                                            isSet = true;
                                        } else
                                            user3 = objective.getScore(TextUtils.formatText("&7" + Bukkit.getPlayer((UUID) uuid).getName() + " &8| &a" + (int) otherPlayer.getChampion().getHealth() + "&fHP"));

                                    }

                                }

                            }

                        }

                        List<UUID> alreadyRemoved = new ArrayList<>();
                        user1 = objective.getScore(TextUtils.formatText("&7" + Bukkit.getPlayer(gamePlayer.getUuid()).getName() + " &8| &a" + (int) gamePlayer.getChampion().getHealth() + "&fHP"));

                        user1.setScore(10);
                        if (user2 != null) {
                            user2.setScore(8);
                        } else {

                            boolean setScore = false;
                            for (GamePlayer offlinePlayer : PlayerJoinListener.left) {

                                if (offlinePlayer.getTeam().getName().equalsIgnoreCase(gamePlayer.getTeam().getName())) {

                                    objective.getScore(TextUtils.formatText("&7" + Bukkit.getOfflinePlayer(offlinePlayer.getUuid()).getName() + " &8| &aN/A")).setScore(8);
                                    setScore = true;
                                    alreadyRemoved.add(offlinePlayer.getUuid());
                                    break;

                                }

                            }

                            if (!setScore)
                                objective.getScore("§8").setScore(8);

                        }
                        if (user3 != null) {
                            user3.setScore(9);
                        } else {

                            boolean setScore = false;
                            for (GamePlayer offlinePlayer : PlayerJoinListener.left) {

                                if (offlinePlayer.getTeam().getName().equalsIgnoreCase(gamePlayer.getTeam().getName()) && !alreadyRemoved.contains(offlinePlayer.getUuid())) {

                                    objective.getScore(TextUtils.formatText("&7" + Bukkit.getOfflinePlayer(offlinePlayer.getUuid()).getName() + " &8| &aN/A")).setScore(9);
                                    setScore = true;
                                    break;

                                }

                            }

                            if (!setScore)
                                objective.getScore("§9").setScore(9);

                        }
                        objective.getScore("§3").setScore(7);
                        statsMana = objective.getScore(TextUtils.formatText("&7Stats: Mana: &3" + gamePlayer.getChampion().getMana() + "&8/&3" + gamePlayer.getChampion().getMaxMana()));
                        statsMana.setScore(6);
                        level = objective.getScore(TextUtils.formatText("&fLevel: &6" + gamePlayer.getLevel()));
                        level.setScore(5);
                        String expRequired;
                        if (gamePlayer.getLevel() == 1)
                            expRequired = 150 + "";
                        else if (gamePlayer.getLevel() == 2)
                            expRequired = 182 + "";
                        else if (gamePlayer.getLevel() == 3)
                            expRequired = 210 + "";
                        else if (gamePlayer.getLevel() == 4)
                            expRequired = 243 + "";
                        else if (gamePlayer.getLevel() == 5)
                            expRequired = 271 + "";
                        else if (gamePlayer.getLevel() == 6)
                            expRequired = 300 + "";
                        else
                            expRequired = "Max Lvl";

                        exp = objective.getScore(TextUtils.formatText("&f" + gamePlayer.getChampion().getExp() + "&8/&7" + expRequired));
                        exp.setScore(4);
                        objective.getScore("§4").setScore(3);

                        gold = objective.getScore(TextUtils.formatText("&6Gold&7: &6" + gamePlayer.getGold()));
                        gold.setScore(2);
                        objective.getScore("§ewww.kingdomcrashers.net").setScore(1);

                        Bukkit.getPlayer(gamePlayer.getUuid()).setScoreboard(board);

                    }

                }

            }

        }.runTaskTimer(plugin, 0, 20);

    }

}
