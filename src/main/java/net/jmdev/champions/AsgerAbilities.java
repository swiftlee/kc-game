package net.jmdev.champions;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.effect.HeartEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.DynamicLocation;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.listener.PlayerInteractListener;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
<<<<<<< HEAD
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.meta.LeatherArmorMeta;
=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/19/2017 | 22:23
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
public class AsgerAbilities {

    private EffectManager effectManager;
    private Main plugin;

    public AsgerAbilities(EffectManager effectManager, Main plugin) {

        this.effectManager = effectManager;
        this.plugin = plugin;

    }

    public void rage(Player player, GamePlayer gamePlayer) {

        int maxHealth = gamePlayer.getChampion().getMaxHealth();

        if (!gamePlayer.asgerPassive && gamePlayer.getChampion().getHealth() <= (maxHealth / 10)) {
            HeartEffect heartEffect = new HeartEffect(effectManager);
            heartEffect.setEntity(player);
            heartEffect.setDynamicOrigin(new DynamicLocation(player));
            heartEffect.delay = 20;
            heartEffect.iterations = 60;
            heartEffect.start();
            gamePlayer.asgerPassive = true;

            for (int i = 0; i < 35; i++) {
                int j = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        gamePlayer.getChampion().addHealth((maxHealth * .4) / 35);
                        if (j == 49) {
                            gamePlayer.asgerPassive = false;
                        }
                    }
                }.runTaskLater(plugin, i * 20);
            }
        }
    }

    public void bloodspikes(Player player, GamePlayer gamePlayer) {

        BleedEffect bleedEffect = new BleedEffect(effectManager);

        for (Entity entity : player.getNearbyEntities(5, 3, 5)) {

            if (entity instanceof Player) { // TODO: 7/20/2017 Add checks for minions 
                for (GamePlayer otherPlayer : Main.gamePlayers) {
                    if (otherPlayer.getUuid().equals(entity.getUniqueId())) {

                        if (!(gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName()))) {

                            for (int i = 0; i < 4; i++) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!(gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName()))) {
                                            bleedEffect.setEntity(entity);
                                            bleedEffect.hurt = false;
                                            bleedEffect.setDynamicOrigin(new DynamicLocation(entity));
                                            bleedEffect.start();
                                            Bukkit.getPlayer(otherPlayer.getUuid()).playEffect(EntityEffect.HURT);

<<<<<<< HEAD
                                            otherPlayer.getChampion().subtractHealth(5 + (otherPlayer.getAsgerUlt() * gamePlayer.getChampion().getAttackDamage() * 0.5), plugin, gamePlayer, otherPlayer, false);
=======
                                            otherPlayer.getChampionType().subtractHealth(20 + (otherPlayer.getAsgerUlt() * gamePlayer.getChampionType().getAttackDamage() * .8), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                                            TextUtils.damageMsg(gamePlayer, otherPlayer);
                                        }

                                    }
                                }.runTaskLater(plugin, i * 20);
                            }
                        }
                    }
                }
            } else if (entity.getType() == EntityType.ZOMBIE) {

                Zombie zombie = (Zombie) entity;
                if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                    for (int i = 0; i < 4; i++) {

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                bleedEffect.setEntity(entity);
                                bleedEffect.hurt = false;
                                bleedEffect.setDynamicOrigin(new DynamicLocation(entity));
                                bleedEffect.start();

<<<<<<< HEAD
                                zombie.damage(5 + (gamePlayer.getChampion().getAttackDamage() * 0.5), player);

                                /*if (zombie.isDead()) {
                                    gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                                    player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                                    GeneralUtils.levelUp(gamePlayer);
                                }*/

                            }

                        }.runTaskLater(plugin, i * 20);

                    }


                } else if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.RED.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("blue")) {

                    for (int i = 0; i < 4; i++) {

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                bleedEffect.setEntity(entity);
                                bleedEffect.hurt = false;
                                bleedEffect.setDynamicOrigin(new DynamicLocation(entity));
                                bleedEffect.start();

                                zombie.damage(20 + (gamePlayer.getChampion().getAttackDamage() * 0.8), player);

                                if (zombie.isDead()) {
                                    gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                                    player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                                    GeneralUtils.levelUp(gamePlayer);
                                }

                            }

                        }.runTaskLater(plugin, i * 20);
=======
                            GeneralUtils.cooldown(plugin, gamePlayer, 4, 9);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    }

                }

            }
        }

        GeneralUtils.cooldown(plugin, gamePlayer, 4, 7);

    }

    public void insanityAura(Player player, GamePlayer gamePlayer) {
        boolean cooldown = false;
        for (Entity e : player.getNearbyEntities(5, 1, 5)) {

            if (e instanceof Player) {

                for (GamePlayer otherPlayer : Main.gamePlayers) {

                    if (otherPlayer.getUuid().equals(e.getUniqueId()) && !otherPlayer.getTeam().getName().equalsIgnoreCase(gamePlayer.getTeam().getName())) {

                        e.playEffect(EntityEffect.HURT);
<<<<<<< HEAD

                        otherPlayer.getChampion().subtractHealth(30+(gamePlayer.getChampion().getAttackDamage()*0.3) * otherPlayer.getAsgerUlt(), plugin, gamePlayer, otherPlayer, false);
=======
                        GeneralUtils.cooldown(plugin, gamePlayer, 2, 12);

                        otherPlayer.getChampionType().subtractHealth(35 * otherPlayer.getAsgerUlt(), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                        ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                        explodeEffect.setEntity(player);
                        explodeEffect.color = Color.fromRGB(255, 165, 0);
                        explodeEffect.type = EffectType.INSTANT;
                        explodeEffect.speed = 0.4F;
                        explodeEffect.particleCount = 500;

                        explodeEffect.start();
                        if (!cooldown) {
                            GeneralUtils.cooldown(plugin, gamePlayer, 2, 12);
                            cooldown = true;
                        }
                        TextUtils.damageMsg(gamePlayer, otherPlayer);
                        break;

                    }

                }

            } else if (e.getType() == EntityType.ZOMBIE) {

                Zombie zombie = (Zombie) e;
                if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                    zombie.damage(30+(gamePlayer.getChampion().getAttackDamage()*0.3), player);

                    if (zombie.isDead()) {
                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                        player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                        GeneralUtils.levelUp(gamePlayer);
                    }
                    if (!cooldown) {
                        GeneralUtils.cooldown(plugin, gamePlayer, 2, 9);
                        cooldown = true;
                    }


                } else if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.RED.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("blue")) {

                    zombie.damage(30+(gamePlayer.getChampion().getAttackDamage()*0.3), player);

                    if (zombie.isDead()) {
                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                        player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                        GeneralUtils.levelUp(gamePlayer);
                    }

                    if (!cooldown) {
                        GeneralUtils.cooldown(plugin, gamePlayer, 2, 9);
                        cooldown = true;
                    }

                }

            }

        }

    }

    public void slaughterfest(Player player, GamePlayer gamePlayer) {

        Entity entity = PlayerInteractListener.getNearestEntityInSight(gamePlayer, player, 10);

        if (entity instanceof Player && ((Player) entity).getGameMode() == GameMode.SPECTATOR)
            return;

        if (entity == null)
            return;

        if (entity instanceof Player) {

            Player player1 = (Player) entity;

            GamePlayer otherPlayer = null;

            for (GamePlayer gamePlayer1 : Main.gamePlayers) {

                if (gamePlayer1.getUuid().equals(player1.getUniqueId())) {

                    otherPlayer = gamePlayer1;
                    break;

                }

            }

            if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName())) {

                if (otherPlayer.getSlaughterfestStacks() == 3) {

                    otherPlayer.setSlaughterfestStacks((byte) 0);

<<<<<<< HEAD
                    GeneralUtils.cooldown(plugin, gamePlayer, 3, 13);
=======
                    GeneralUtils.cooldown(plugin, gamePlayer, 3, 12);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    SmokeEffect smokeEffect = new SmokeEffect(effectManager);
                    smokeEffect.setEntity(player);
                    smokeEffect.color = Color.fromRGB(255, 165, 0);
                    smokeEffect.type = EffectType.INSTANT;
                    smokeEffect.speed = 0.4F;
                    smokeEffect.particleCount = 500;

                    smokeEffect.start();

                    player.teleport(player1);

                    ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                    explodeEffect.setEntity(player);
                    explodeEffect.color = Color.fromRGB(255, 165, 0);
                    explodeEffect.type = EffectType.INSTANT;
                    explodeEffect.speed = 0.4F;
                    explodeEffect.particleCount = 500;

                    player1.playEffect(EntityEffect.HURT);
                    player1.setVelocity(new Vector(0, 1, 0));

<<<<<<< HEAD
                    otherPlayer.getChampion().subtractHealth(30 + (otherPlayer.getAsgerUlt() * gamePlayer.getChampion().getAttackDamage() * 0.5D), plugin, gamePlayer, otherPlayer, false);
=======
                    otherPlayer.getChampionType().subtractHealth(30 + (otherPlayer.getAsgerUlt() * gamePlayer.getChampionType().getAttackDamage() * 0.8D), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                    TextUtils.damageMsg(gamePlayer, otherPlayer);

                }
            }

        }

    }

    public void limitlessAnger(Player player, GamePlayer gamePlayer) {

        Entity entity = PlayerInteractListener.getNearestEntityInSight(gamePlayer, player, 10);

        if (entity instanceof Player) {

            Player player1 = (Player) entity;
            final GamePlayer otherPlayer = GeneralUtils.getGamePlayer(entity.getUniqueId());

            if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName())) { //if greater than 0.99 is true

                GeneralUtils.cooldown(plugin, gamePlayer, 5, 90);
<<<<<<< HEAD
                gamePlayer.getChampion().setUltActive(true);
=======
                gamePlayer.getChampionType().setUltActive(true);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                otherPlayer.setAsgerUlt((byte) 5); //sets the multiplier for asger damage against this player

                WarpEffect warpEffect = new WarpEffect(effectManager);
                warpEffect.rings = 3;
                warpEffect.duration = 5 * 20;
                warpEffect.setDynamicOrigin(new DynamicLocation(player));
                warpEffect.iterations = 20 * 5;

                WarpEffect warpEffect2 = new WarpEffect(effectManager);
                warpEffect2.rings = 3;
                warpEffect2.duration = 5 * 20;
                warpEffect2.setDynamicOrigin(new DynamicLocation(player1));
                warpEffect2.iterations = 20 * 5;

                warpEffect.start();
                warpEffect2.start();

<<<<<<< HEAD
                GeneralUtils.cooldown(plugin, gamePlayer, 5, 80);
=======
                GeneralUtils.cooldown(plugin, gamePlayer, 5, 60);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        otherPlayer.setAsgerUlt((byte) 1);
<<<<<<< HEAD
                        gamePlayer.getChampion().setUltActive(false);

                    }

                }.runTaskLater(plugin, 20 * 6);
=======
                        gamePlayer.getChampionType().setUltActive(false);

                    }

                }.runTaskLater(plugin, 20 * 5);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

            }

        }

    }

}
