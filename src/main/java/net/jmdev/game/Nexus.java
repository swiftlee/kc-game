package net.jmdev.game;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.util.DynamicLocation;

import net.jmdev.Main;
import net.jmdev.util.TextUtils;
import net.jmdev.util.Title;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/23/2017 | 19:57
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
public class Nexus {

    public static UUID blueUUID;
    public static UUID redUUID;
    public static BossBar bossBar;
    private Main plugin;
    private EffectManager effectManager;

    public Nexus(Main plugin, EffectManager effectManager) {
        this.plugin = plugin;
        this.effectManager = effectManager;
    }

    public void spawnNexusSlimes(World world) {

        Slime villager = world.spawn(new Location(world, 683.5, 8, 37.5, -90, 0), Slime.class); //blue
        ArmorStand armorStand = world.spawn(new Location(world, 683.5, 6.5, 37.5, -90, 0), ArmorStand.class);
        Slime slime = world.spawn(new Location(world, 683.5, 8, 37.5, -90, 0), Slime.class);
        ArmorStand hologram = world.spawn(new Location(world, 683.5, 8, 37.5, -90, 0), ArmorStand.class);
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
        slime.setAI(false);
        slime.setInvulnerable(true);
        slime.setCustomNameVisible(false);
        slime.setSize(-1);
        slime.addPotionEffect(potionEffect);
        slime.setRemoveWhenFarAway(false);
        hologram.setGravity(false);
        hologram.setVisible(false);
        hologram.setCustomNameVisible(true);
        hologram.setInvulnerable(true);
        hologram.setSmall(true);
        hologram.setCanPickupItems(false);
        slime.setPassenger(hologram);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);
        armorStand.setCanPickupItems(false);
        villager.setCustomNameVisible(false);
        villager.setAI(false);
        villager.setSize(8);
        villager.setInvulnerable(false);
        villager.setCollidable(false);
        villager.addPotionEffect(potionEffect);
        villager.setRemoveWhenFarAway(false);
        villager.setPassenger(slime);
        armorStand.setPassenger(villager);
        villager.setMaxHealth(2048);
        villager.setHealth(2048);
        blueUUID = villager.getUniqueId();

        Slime villager1 = world.spawn(new Location(world, 877.5, 8, 37.5, 90, 0), Slime.class); //red
        ArmorStand armorStand1 = world.spawn(new Location(world, 877.5, 6.5, 37.5, 90, 0), ArmorStand.class);
        Slime slime1 = world.spawn(new Location(world, 877.5, 8, 37.5, 90, 0), Slime.class); //red
        ArmorStand hologram1 = world.spawn(new Location(world, 877.5, 8, 37.5, 90, 0), ArmorStand.class);
        slime1.setAI(false);
        slime1.setInvulnerable(true);
        slime1.setCustomNameVisible(false);
        slime1.setSize(-1);
        slime1.addPotionEffect(potionEffect);
        slime1.setRemoveWhenFarAway(false);
        hologram1.setGravity(false);
        hologram1.setVisible(false);
        hologram1.setCustomNameVisible(true);
        hologram1.setInvulnerable(true);
        hologram1.setSmall(true);
        hologram1.setCanPickupItems(false);
        slime1.setPassenger(hologram1);
        armorStand1.setGravity(false);
        armorStand1.setVisible(false);
        armorStand1.setInvulnerable(true);
        armorStand1.setSmall(true);
        armorStand1.setCanPickupItems(false);
        villager1.setCustomNameVisible(false);
        villager1.setAI(false);
        villager1.setSize(8);
        villager1.addPotionEffect(potionEffect);
        villager1.setInvulnerable(false);
        villager1.setCollidable(false);
        villager1.setRemoveWhenFarAway(false);
        villager1.setPassenger(slime1);
        armorStand1.setPassenger(villager1);
        villager1.setMaxHealth(2048);
        villager1.setHealth(2048);
        redUUID = villager1.getUniqueId();

        bossBar = Bukkit.createBossBar(TextUtils.formatText("&9Blue Nexus: &7" + (int) villager.getHealth() + "/&a" + (int) villager.getMaxHealth() + " &7| &cRed Nexus: &7" + (int) villager1.getHealth() + "/&a" + (int) villager1.getMaxHealth()), BarColor.RED, BarStyle.SEGMENTED_6);

        for (GamePlayer gamePlayer : Main.gamePlayers) {

            bossBar.addPlayer(Bukkit.getPlayer(gamePlayer.getUuid()));

        }

        new BukkitRunnable() {

            @Override
            public void run() {

                if (!villager.isDead()) {

                    final double barPercent = (villager.getHealth() / villager.getMaxHealth());
                    String levelBar = "::::::::::::::::::::".replace(":", "\u258C");
                    int index = (int) (barPercent * 20);

                    if (index > 0)
                        levelBar = TextUtils.formatText("&a" + levelBar.substring(0, index) + "§c" + levelBar.substring(index, levelBar.length()));
                    else
                        levelBar = TextUtils.formatText("&c" + levelBar);
                    hologram.setCustomName(levelBar);

                } else {

                    new Title(TextUtils.formatText("&c&lRed &6&lteam has won!"), "", 0, 2, 1).broadcast();
                    Bukkit.broadcastMessage(TextUtils.formatText("&c&lRed &6&lteam has won!"));
                    for (Team team : Main.teams) {

                        if (team.getName().equalsIgnoreCase("Red")) {
                            new EndGame(plugin, team).endGame();
                            this.cancel();
                            break;
                        }
                    }

                    this.cancel();

                }

                if (!villager1.isDead()) {

                    final double barPercent = (villager1.getHealth() / villager1.getMaxHealth());
                    String levelBar = "::::::::::::::::::::".replace(":", "\u258C");
                    int index = (int) (barPercent * 20);

                    if (index > 0)
                        levelBar = TextUtils.formatText("&a" + levelBar.substring(0, index) + "§c" + levelBar.substring(index, levelBar.length()));
                    else
                        levelBar = TextUtils.formatText("&c" + levelBar);
                    hologram1.setCustomName(levelBar);

                } else {

                    new Title(TextUtils.formatText("&9&lBlue &6&lteam has won!"), "", 0, 2, 1).broadcast();
                    Bukkit.broadcastMessage(TextUtils.formatText("&9&lBlue &6&lteam has won!"));

                    for (Team team : Main.teams) {
                        if (team.getName().equalsIgnoreCase("Blue")) {
                            new EndGame(plugin, team).endGame();
                            this.cancel();
                            break;
                        }
                    }

                    this.cancel();

                }

                bossBar.setTitle(TextUtils.formatText("&9Blue Nexus: &7" + (int) villager.getHealth() + "/&a" + (int) villager.getMaxHealth() + " &7| &cRed Nexus: &7" + (int) villager1.getHealth() + "/&a" + (int) villager1.getMaxHealth()));

                if (TurretPriorityHandler.leftLaneExists("blue") && TurretPriorityHandler.rightLaneExists("blue") && TurretPriorityHandler.middleLaneExists("blue")) { //in blue territory

                    for (Entity e : villager.getNearbyEntities(25, 14, 25)) {


                        GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getUniqueId());

<<<<<<< HEAD
                        if (gamePlayer != null && gamePlayer.getTeam().getName().equalsIgnoreCase("red") && Bukkit.getPlayer(gamePlayer.getUuid()).getGameMode() != org.bukkit.GameMode.SPECTATOR) {
=======
                                ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                                explodeEffect.setDynamicOrigin(new DynamicLocation(e));
                                explodeEffect.particleCount = 1;
                                explodeEffect.start();
                                gamePlayer.getChampionType().subtractHealth(150, plugin, null, gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                            ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                            explodeEffect.setDynamicOrigin(new DynamicLocation(e));
                            explodeEffect.particleCount = 1;
                            explodeEffect.start();
                            gamePlayer.getChampion().subtractHealth(150, plugin, null, gamePlayer, false);

                        }

                    }

                }

                if (TurretPriorityHandler.leftLaneExists("red") && TurretPriorityHandler.rightLaneExists("red") && TurretPriorityHandler.middleLaneExists("red")) { //in red territory

                    for (Entity e : villager1.getNearbyEntities(25, 14, 25)) {

                        GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getUniqueId());

                        if (gamePlayer != null && gamePlayer.getTeam().getName().equalsIgnoreCase("blue") && Bukkit.getPlayer(gamePlayer.getUuid()).getGameMode() != org.bukkit.GameMode.SPECTATOR) {

<<<<<<< HEAD
                            //Bukkit.broadcastMessage("Nexus player:" + gamePlayer.getTeam().getName());
                            ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                            explodeEffect.setDynamicOrigin(new DynamicLocation(e));
                            explodeEffect.particleCount = 1;
                            explodeEffect.start();
                            gamePlayer.getChampion().subtractHealth(150, plugin, null, gamePlayer, false);
=======
                                //Bukkit.broadcastMessage("Nexus player:" + gamePlayer.getTeam().getName());
                                ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                                explodeEffect.setDynamicOrigin(new DynamicLocation(e));
                                explodeEffect.particleCount = 1;
                                explodeEffect.start();
                                gamePlayer.getChampionType().subtractHealth(150, plugin, null, gamePlayer);

                            }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                        }

                    }

                }

            }

        }.runTaskTimer(plugin, 0, 20);

    }

}
