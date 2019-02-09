package net.jmdev.game;

import net.jmdev.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/20/2017 | 23:11
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
class Regeneration {

    private Main plugin;
    private Location locRed;
    private Location locBlue;

    Regeneration(Main plugin, World world) {

        this.plugin = plugin;

        if (GameMode.getMode() == GameMode._1V1) {

            locRed = new Location(world, 936.5, 21, 44.5, -135, 0);//change
            locBlue = new Location(world, 624.5, 21, 44.5, 135, 0);//change

        } else if (GameMode.getMode() == GameMode._3V3) {

            locRed = new Location(world, 936.5, 21, 44.5, -135, 0);
            locBlue = new Location(world, 624.5, 21, 44.5, 135, 0);

        } else {

            locRed = new Location(world, 936.5, 21, 44.5, -135, 0);//change
            locBlue = new Location(world, 624.5, 21, 44.5, 135, 0);//change

        }

    }

    void startManaRegen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                for (GamePlayer player : Main.gamePlayers) {

                    if (player.getChampion().getManaRegen() > 0) {

                        if (player.hasItem("Material Spirit")) {

                            if (player.materialSpirit < 5) {

                                //get 10% of maxHealth, get current health and
                                float ratio = (float) (player.getChampion().getHealth() / player.getChampion().getMaxHealth());
                                ratio = ratio < 0.5F ? 0.5F : ratio;

                                for (float i = 0; i < ratio; i += 0.1F) {


                                    player.materialSpirit += 1;
                                    player.getChampion().addManaRegen(1);
                                }

                            }


                        }

                        float modifier;

                        if (player.getTeam().getName().equals("red"))
                            modifier = Bukkit.getPlayer(player.getUuid()).getLocation().distanceSquared(locRed) <= 100 ? (30 / player.getChampion().getManaRegen()) : 1.0F;
                        else
                            modifier = Bukkit.getPlayer(player.getUuid()).getLocation().distanceSquared(locBlue) <= 100 ? (30 / player.getChampion().getManaRegen()) : 1.0F;

                        player.getChampion().addMana((int) (player.getChampion().getManaRegen() * modifier));

                    }

                }

            }

        }.runTaskTimer(plugin, 0, 20);

    }

    void startHealthRegen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                for (GamePlayer player : Main.gamePlayers) {

                    float modifier;

                    if (player.getTeam().getName().equals("red"))
                        modifier = Bukkit.getPlayer(player.getUuid()).getLocation().distanceSquared(locRed) <= 100 ? (30 / player.getChampion().getHealthRegen()) : 1.0F;
                    else
                        modifier = Bukkit.getPlayer(player.getUuid()).getLocation().distanceSquared(locBlue) <= 100 ? (30 / player.getChampion().getHealthRegen()) : 1.0F;

                    player.getChampion().addHealth(player.getChampion().getHealthRegen() * modifier);


                }

            }

        }.runTaskTimer(plugin, 0, 20);

    }

    void startGoldGeneration() {

        new BukkitRunnable() {

            @Override
            public void run() {

                for (GamePlayer player : Main.gamePlayers) {

                    player.setGold(player.getGold() + (6 / 5) + player.goldPerSecond);

                }

            }

        }.runTaskTimer(plugin, 0, 20);

    }

}
