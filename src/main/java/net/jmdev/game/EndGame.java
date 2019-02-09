package net.jmdev.game;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.jmdev.Connection.Database;
import net.jmdev.Connection.GlobalBooster;
import net.jmdev.Connection.PlayerData;
import net.jmdev.Main;
import net.jmdev.util.Scoreboard;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/23/2017 | 20:31
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
public class EndGame {

    private Main plugin;
    private Team winner;

    public EndGame(Main plugin, Team team) {
        this.plugin = plugin;
        winner = team;
    }


    public void endGame() {
        GameState.setGameState(GameState.ENDED);
        List<GamePlayer> gamePlayers = new ArrayList<>();
        gamePlayers.addAll(Main.gamePlayers);
        gamePlayers.sort((o1, o2) -> Integer.compare(o2.getKills(), o1.getKills()));
        if (gamePlayers.size() >= 3) {
            String topPlayers = "&8&l------------------------\n" +
                    "&6&l" + GameMode.getMode().toString() + " Unranked\n" +
                    "\n" +
                    "&e&l 1st Champion &7- " + Bukkit.getPlayer(gamePlayers.get(0).getUuid()).getName() + "\n" +
                    "&e&l 2nd Champion &7- " + Bukkit.getPlayer(gamePlayers.get(1).getUuid()).getName() + "\n" +
                    "&e&l 3rd Champion &7- " + Bukkit.getPlayer(gamePlayers.get(2).getUuid()).getName() + "\n" +
                    "\n" +
                    "&8&l------------------------";
            Bukkit.broadcastMessage(TextUtils.formatText(topPlayers));
        }


        for (GamePlayer gp : Main.gamePlayers) {
            PlayerData pd = Database.getPlayerByUUID(gp.getUuid());

            pd.addTotalDeaths(gp.getDeaths());
            pd.addTotalKill(gp.getKills());
            if (gp.getTeam().equals(winner)) {
                gp.winner = true;

                pd.addTotalWins(1);
                if (GameMode.getMode() == GameMode._1V1) {
                    pd.addTotal1v1Wins(1);
                } else if (GameMode.getMode() == GameMode._3V3) {
                    pd.addTotal3v3Wins(1);
                } else if (GameMode.getMode() == GameMode._5V5) {

                }
            } else
                pd.setTotalLosses(pd.getTotalLosses() + 1);

            int gold = gp.calculateGold();
            for (GlobalBooster gb : Database.getGlobalBoosters()) {
                if (gb.checkIsDone())
                    continue;
                if (gb.getField().equalsIgnoreCase("coins") || gb.getField().equalsIgnoreCase("gold"))
                    gold *= gb.getMultiplier();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            try {
                Date d = dateFormat.parse("8/25/2017");
                Date endDate = dateFormat.parse("8/28/2017");
                if (date.getTime()<=endDate.getTime() && date.getTime()>=d.getTime()){
                    gold *= 2;
                    Bukkit.getPlayer(gp.getUuid()).sendMessage(ChatColor.GOLD + "Gold x2 (Double Gold Weekend)");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Bukkit.getPlayer(gp.getUuid()).sendMessage(gp.getRecap("" + gold, "" + gp.calculateEXP()));
            pd.setCoins(pd.getCoins() + gold);
            pd.addTotalAssists(gp.getAssists());
            long time = new Date().getTime() - Scoreboard.startDate;
            time /= 1000;
            pd.addTotalTime(time);
            Database.updatePlayer(pd);
        }


        plugin.getServer().getScheduler().cancelTasks(plugin);
        Bukkit.broadcastMessage(TextUtils.formatText("&a&lReturning to lobby in 15 seconds."));

        new BukkitRunnable() {

            @Override
            public void run() {

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("lobby");
                Bukkit.getOnlinePlayers().forEach(player -> player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray()));

            }

        }.runTaskLater(plugin, 15 * 20);

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getServer().shutdown();

            }

        }.runTaskLater(plugin, 25 * 20);

    }

}
