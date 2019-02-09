package net.jmdev.listener;

import de.slikey.effectlib.EffectManager;

import net.jmdev.Connection.Database;
import net.jmdev.Connection.PlayerData;
import net.jmdev.Main;
import net.jmdev.game.ChampionSelection;
import net.jmdev.game.GameMode;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.GameState;
import net.jmdev.game.Nexus;
import net.jmdev.game.Team;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.TextUtils;
import net.jmdev.util.Title;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/08/2017 | 15:37
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
public class PlayerJoinListener implements Listener {

    public static List<GamePlayer> left = new ArrayList<>();
    private Main plugin;
    private ChampionSelection championSelection;
    private EffectManager effectManager;

    public PlayerJoinListener(Main plugin, EffectManager effectManager) {

        this.plugin = plugin;
        this.effectManager = effectManager;
        championSelection = new ChampionSelection(plugin, effectManager);

    }

    private Team choosePlayerTeam() {

        if (GameMode.getMode() == GameMode._1V1) {

            boolean teamHasPlayer = Main.teams.get(0).size() > 0;

            if (teamHasPlayer) //team does not have space
                return Main.teams.get(1);
            else
                return Main.teams.get(0);

        } else if (GameMode.getMode() == GameMode._3V3) {

            int teamOneSize = Main.teams.get(0).size();
            int teamTwoSize = Main.teams.get(1).size();
            if (teamOneSize < 3)
                return Main.teams.get(0);
            else if (teamTwoSize < 3)
                return Main.teams.get(1);
            else
                return null;

        } else if (GameMode.getMode() == GameMode._5V5) {

            byte teamOneSize = (byte) Main.teams.get(0).size();
            byte teamTwoSize = (byte) Main.teams.get(1).size();

            float randomFloat = new Random().nextFloat();

            if (randomFloat > 0.49F) {

                if (teamOneSize < 5) //team has space
                    return Main.teams.get(0);
                else
                    return Main.teams.get(1);

            } else {

                if (teamTwoSize < 5) //team has space
                    return Main.teams.get(1);
                else
                    return Main.teams.get(0);


            }

        } else {

            return null;

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(null);
        e.getPlayer().setGameMode(org.bukkit.GameMode.SURVIVAL);

        if (Main.gamePlayers.size() == GameMode.getMode().getMaxPlayers()) {
            e.getPlayer().kickPlayer(TextUtils.formatText("&cThis server is full."));
            return;
        }

        if (GameState.getGameState() == GameState.WAITING) {
            e.getPlayer().getInventory().clear();
            e.getPlayer().setHealth(20);
            e.getPlayer().setFoodLevel(20);
            e.getPlayer().setLevel(0);
            e.getPlayer().setExp(0);

            Team team = choosePlayerTeam();

            if (team != null) {
                team.add(e.getPlayer().getUniqueId());
            }
            GamePlayer gp = new GamePlayer(e.getPlayer().getUniqueId(), null, team);
            Main.gamePlayers.add(gp);

           //Database.savePlayer(new PlayerData(e.getPlayer().getUniqueId(), e.getPlayer().getName()));
            e.getPlayer().sendMessage(TextUtils.formatText("&6[&4Disclaimer&6] &7This is public &c&lBETA&7, so please expect &ebugs &7and report them properly on the forums."));
            Bukkit.broadcastMessage(TextUtils.formatText("&f" + e.getPlayer().getName() + " &ahas joined! &7(" + Main.gamePlayers.size() + "/" + GameMode.getMode().getMaxPlayers() + ")"));

            new Title(TextUtils.formatText(plugin.getConfig().getString("onJoin.title")), TextUtils.formatText(plugin.getConfig().getString("onJoin.subtitle")), 1, 3, 1).send(e.getPlayer());

            if (GameMode.getMode() == GameMode._1V1) { // TODO: 7/20/2017 Change to 1V1
                /*
                if (team.getName().equalsIgnoreCase("red")) {
                    e.getPlayer().teleport(new Location(Bukkit.getWorld("3v3"), 928, 17, 38)); // TODO: 7/20/2017 Change the world name.
                } else {
                    e.getPlayer().teleport(new Location(Bukkit.getWorld("3v3"), 638, 18, 37)); // TODO: 7/20/2017 Change the world name.
                } */

                if (team.getName().equalsIgnoreCase("red")) {
                    e.getPlayer().teleport(new Location(Bukkit.getWorld("3v3"), 943.5, 21, 37.5, 90, 0)); // temporary
                } else {
                    e.getPlayer().teleport(new Location(Bukkit.getWorld("3v3"), 617.5, 21, 37.5, -90, 0)); // temporary
                }

            } else if (GameMode.getMode() == GameMode._3V3) {
                if (team.getName().equalsIgnoreCase("red")) {
                    e.getPlayer().teleport(new Location(Bukkit.getWorld("3v3"), 943.5, 21, 37.5, 90, 0)); // TODO: 7/20/2017 Change the world name.
                } else {
                    e.getPlayer().teleport(new Location(Bukkit.getWorld("3v3"), 617.5, 21, 37.5, -90, 0)); // TODO: 7/20/2017 Change the world name.
                }
            }

            if (GameMode.getMode().getMaxPlayers() <= Main.gamePlayers.size() && GameState.getGameState() == GameState.WAITING) {
                for (int i = 0; i <= plugin.getConfig().getInt("onEnoughPlayers.countdownFrom"); i++) {
                    final int j = i;
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            if (GameMode.getMode().getMaxPlayers() > Main.gamePlayers.size()) {
                                this.cancel();
                            }

                            new Title(plugin.getConfig().getString("onEnoughPlayers.title").replace("{number}", String.valueOf(plugin.getConfig().getInt("onEnoughPlayers.countdownFrom") - j)), "", 0, 1, 0).broadcast();

                            if (j == plugin.getConfig().getInt("onEnoughPlayers.countdownFrom")) {
                                // TODO: 6/26/2017 SEND GAME STATE TO LOBBY SERVER
                                championSelection.start(false);
                            }
                        }
                    }.runTaskLater(plugin, 20 * i);
                }
            }
        } else {
            /*
             * ```19.08 15:42:04 [Server] INFO ... 14 more
             19.08 15:42:04 [Server] INFO at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:306) ~[spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_131]
             19.08 15:42:04 [Server] INFO at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_131]
             19.08 15:42:04 [Server] INFO at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_131]
             19.08 15:42:04 [Server] INFO at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_131]
             19.08 15:42:04 [Server] INFO at net.jmdev.listener.PlayerJoinListener.onPlayerJoin(PlayerJoinListener.java:189) ~[?:?]
             19.08 15:42:04 [Server] INFO Caused by: java.lang.NullPointerException
             19.08 15:42:04 [Server] INFO at java.lang.Thread.run(Thread.java:748) [?:1.8.0_131]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.MinecraftServer.run(MinecraftServer.java:564) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.MinecraftServer.C(MinecraftServer.java:665) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.DedicatedServer.D(DedicatedServer.java:399) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.MinecraftServer.D(MinecraftServer.java:825) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.ServerConnection.c(ServerConnection.java:140) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.NetworkManager.a(NetworkManager.java:233) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.LoginListener.c(LoginListener.java:54) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.LoginListener.b(LoginListener.java:144) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.PlayerList.a(PlayerList.java:159) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at net.minecraft.server.v1_9_R2.PlayerList.onPlayerJoin(PlayerList.java:333) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:487) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at org.bukkit.plugin.SimplePluginManager.fireEvent(SimplePluginManager.java:502) [spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:62) ~[spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO at org.bukkit.plugin.java.JavaPluginLoader$1.execute(JavaPluginLoader.java:310) ~[spigot-1.9.4.jar:git-Spigot-c6871e2-0cd0397]
             19.08 15:42:04 [Server] INFO org.bukkit.event.EventException
             19.08 15:42:04 [Server] ERROR Could not pass event PlayerJoinEvent to KCGame v1.0```
             */
            boolean canJoin = false;
            Iterator<GamePlayer> iterator = left.iterator();

            while (iterator.hasNext()) {
                GamePlayer gamePlayer = iterator.next();
                if (gamePlayer.getUuid().equals(e.getPlayer().getUniqueId())) {

                    gamePlayer.getTeam().add(e.getPlayer().getUniqueId());
                    Main.gamePlayers.add(gamePlayer);
                    Nexus.bossBar.addPlayer(e.getPlayer());

                    canJoin = true;
                    iterator.remove();
                    break;
                }
            }

            if (!canJoin)
                e.getPlayer().kickPlayer(TextUtils.formatText("&cYou tried to join a game in progress!"));

        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if (e.getEntity().hasMetadata("Mount")){
            e.getEntity().eject();
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {

        e.setQuitMessage(null);

        if (GameState.getGameState() == GameState.WAITING) {
            GamePlayer player = GeneralUtils.getGamePlayer(e.getPlayer().getUniqueId());
            for (Team team : Main.teams) {
                team.removeIf(uuid -> uuid.equals(e.getPlayer().getUniqueId()));

            }
            Main.gamePlayers.remove(player);
            Bukkit.broadcastMessage(TextUtils.formatText("&f" + e.getPlayer().getName() + " &ahas left! &7(" + Main.gamePlayers.size() + "/" + GameMode.getMode().getMaxPlayers() + ")"));
        } else if (GameState.getGameState() == GameState.CHAMPION_SELECT) {
            GameState.setGameState(GameState.WAITING);
            championSelection.getTask().cancel();
            Bukkit.getOnlinePlayers().forEach(p -> {
                p.closeInventory();
                p.getInventory().clear();
            });
            GamePlayer player = GeneralUtils.getGamePlayer(e.getPlayer().getUniqueId());
            for (Team team : Main.teams) {
                team.removeIf(uuid -> uuid.equals(e.getPlayer().getUniqueId()));
            }
            Main.gamePlayers.remove(player);
            int playersNeeded = (GameMode.getMode().getMaxPlayers() - Main.gamePlayers.size());
            Bukkit.broadcastMessage(TextUtils.formatText("&cA champion has left, waiting for &7" + playersNeeded + " &cplayer" + (playersNeeded > 1 ? "s" : "") + "!"));
            Bukkit.broadcastMessage(TextUtils.formatText("&f" + e.getPlayer().getName() + " &ahas left! &7(" + Main.gamePlayers.size() + "/" + GameMode.getMode().getMaxPlayers() + ")"));

        } else if (GameState.getGameState() == GameState.STARTED) {

            // TODO: 6/27/2017 STORE DATA TO HOLD UUID & SERVER SO PLAYER CAN REJOIN
            for (GamePlayer gamePlayer : Main.gamePlayers) {

                if (gamePlayer.getUuid().equals(e.getPlayer().getUniqueId())) {
                    left.add(gamePlayer);
                    Main.gamePlayers.remove(gamePlayer);
                    for (Team team : Main.teams) {
                        team.removeIf(uuid -> uuid.equals(e.getPlayer().getUniqueId()));

                    }
                    break;
                }

            }

        }

    }

}
