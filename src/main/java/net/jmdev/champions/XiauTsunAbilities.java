package net.jmdev.champions;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.DynamicLocation;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.ParticlesUtil;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
<<<<<<< HEAD
=======
import org.bukkit.Sound;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/20/2017 | 23:03
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
public class XiauTsunAbilities {

    private EffectManager effectManager;
    private Main plugin;

    public XiauTsunAbilities(EffectManager effectManager, Main plugin) {

        this.effectManager = effectManager;
        this.plugin = plugin;

    }

    Player getNearestPlayer(GamePlayer gamePlayer, Player player) {

        List<Entity> players = new ArrayList<>();

        for (Entity e : player.getNearbyEntities(10, 2, 10)) {

            for (GamePlayer otherPlayer : Main.gamePlayers) {

                if (e.getUniqueId().equals(otherPlayer.getUuid()) && !otherPlayer.getTeam().getName().equalsIgnoreCase(gamePlayer.getTeam().getName()))
                    players.add(e);


            }

        }

        if (players.isEmpty())
            return null;

        java.util.Map<UUID, Double> uuidDistanceMap = new HashMap<>();

        for (Entity entity : players) {

            uuidDistanceMap.put(entity.getUniqueId(), player.getLocation().distanceSquared(entity.getLocation()));

        }

        Double closestDistance = Collections.min(uuidDistanceMap.values());

        java.util.Map.Entry<UUID, Double> min = null;

        for (java.util.Map.Entry<UUID, Double> entry : uuidDistanceMap.entrySet()) {

            if (closestDistance == (double) entry.getValue()) {

                min = entry;
                break;

            }

        }

        if (min != null)
            return Bukkit.getPlayer(min.getKey());
        else
            return null;

    }

    public void assassination(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 80) { //mana cost

            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;

        }

        Entity nearestPlayer = getNearestPlayer(gamePlayer, player);

        if (nearestPlayer == null)
            return;

        double newX;
        double newZ;
        float nang = nearestPlayer.getLocation().getYaw() + 90; //add 90 to horizontal rotation

        if (nang < 0) nang += 360; //reset angle to 360 if 0, essentially doesn't rotate player

        newX = Math.cos(Math.toRadians(nang)); //x amount to put you behind the player
        newZ = Math.sin(Math.toRadians(nang)); //z amount to put you behind the player

        Location newDamagerLoc = new Location(nearestPlayer.getWorld(), nearestPlayer.getLocation().getX() - newX,
                nearestPlayer.getLocation().getY(), nearestPlayer.getLocation().getZ() - newZ, nearestPlayer.getLocation().getYaw(), nearestPlayer.getLocation().getPitch());

        player.teleport(newDamagerLoc);

        WarpEffect warpEffect = new WarpEffect(effectManager);
        warpEffect.rings = 3;
        warpEffect.color = Color.fromRGB(0, 0, 0);
        warpEffect.setDynamicOrigin(new DynamicLocation(nearestPlayer));
        warpEffect.duration = 20 * 2;
        warpEffect.disappearWithOriginEntity = true;
        warpEffect.start();

        GamePlayer otherPlayer = null;

        for (GamePlayer gamePlayer1 : Main.gamePlayers) {

            if (gamePlayer1.getUuid().equals(nearestPlayer.getUniqueId())) {

                otherPlayer = gamePlayer1;
                break;

            }

        }

<<<<<<< HEAD
        GeneralUtils.cooldown(plugin, gamePlayer, 2, 12);
        gamePlayer.getChampion().subtractMana(80);

        if (!otherPlayer.getChampion().getName().equalsIgnoreCase("asger")) { //COPY AND PASTE FOR ALL ABILITIES

            otherPlayer.getChampion().subtractHealth(40 + (gamePlayer.getChampion().getAttackDamage() * 0.2), plugin, gamePlayer, otherPlayer, false);
=======
        GeneralUtils.cooldown(plugin, gamePlayer, 2, 16);
        gamePlayer.getChampionType().subtractMana(80);

        if (!otherPlayer.getChampionType().getName().equalsIgnoreCase("asger")) { //COPY AND PASTE FOR ALL ABILITIES

            otherPlayer.getChampionType().subtractHealth(40 + (gamePlayer.getChampionType().getAttackDamage() * 0.2), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
            TextUtils.damageMsg(gamePlayer, otherPlayer);
            //Bukkit.broadcastMessage(otherPlayer.getChampion().getHealth() + "");
            nearestPlayer.playEffect(EntityEffect.HURT);

        } else {

            if (otherPlayer.getChampion().isUltActive()) {

                if (otherPlayer.getAsgerUlt() > 1) {

<<<<<<< HEAD
                    otherPlayer.getChampion().subtractHealth(40 + (gamePlayer.getChampion().getAttackDamage() * 0.2), plugin, gamePlayer, otherPlayer, false);
=======
                    otherPlayer.getChampionType().subtractHealth(40 + (gamePlayer.getChampionType().getAttackDamage() * 0.2), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                    TextUtils.damageMsg(gamePlayer, otherPlayer);
                    //Bukkit.broadcastMessage(otherPlayer.getChampion().getHealth() + "");
                    nearestPlayer.playEffect(EntityEffect.HURT);

                }

            } else {

<<<<<<< HEAD
                otherPlayer.getChampion().subtractHealth(40 + (gamePlayer.getChampion().getAttackDamage() * 0.2), plugin, gamePlayer, otherPlayer, false);
=======
                otherPlayer.getChampionType().subtractHealth(40 + (gamePlayer.getChampionType().getAttackDamage() * 0.2), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                TextUtils.damageMsg(gamePlayer, otherPlayer);
                //Bukkit.broadcastMessage(otherPlayer.getChampion().getHealth() + "");
                nearestPlayer.playEffect(EntityEffect.HURT);

            }

        } //END COPY AND PASTE

    }

    public void clearArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void ambush(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 60) {

            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;

        }

        Location location = player.getLocation();
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 3, 1, false, false);
        player.addPotionEffect(potionEffect);
        ItemStack[] armorContents = player.getEquipment().getArmorContents();
        clearArmor(player);

<<<<<<< HEAD
        gamePlayer.getChampion().subtractMana(60);
        GeneralUtils.cooldown(plugin, gamePlayer, 4, 18);
=======
        gamePlayer.getChampionType().subtractMana(60);
        GeneralUtils.cooldown(plugin, gamePlayer, 4, 22);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

        new BukkitRunnable() {

            @Override
            public void run() {

                SmokeEffect smokeEffect = new SmokeEffect(effectManager);
                smokeEffect.duration = 10;
                smokeEffect.particleCount = 150;
                smokeEffect.setDynamicOrigin(new DynamicLocation(location));
                smokeEffect.start();
                player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(2.0));
                player.getEquipment().setArmorContents(armorContents);

            }

        }.runTaskLater(plugin, 20 * 3);

    }

    public void finalCalling(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 100) {

            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;

        }

<<<<<<< HEAD
        Player nearestPlayer = getNearestPlayer(gamePlayer, player);
=======
        player.setVelocity(player.getEyeLocation().getDirection().multiply(2.0));
        ArrayList<GamePlayer> inSight = new ArrayList<>();

        TraceEffect traceEffect = new TraceEffect(effectManager);
        traceEffect.color = Color.fromRGB(0, 0, 0);
        traceEffect.duration = 2;
        traceEffect.setDynamicOrigin(new DynamicLocation(player));
        traceEffect.disappearWithOriginEntity = true;
        traceEffect.start();

        for (Entity e : player.getNearbyEntities(5, 5, 5)) {

            if (e instanceof Player) {

                for (GamePlayer otherPlayer : Main.gamePlayers) {

                    if (otherPlayer.getUuid().equals(e.getUniqueId()) && !otherPlayer.getTeam().getName().equalsIgnoreCase(gamePlayer.getTeam().getName())) {

                        Player player1 = (Player) e;

                        Location eye = player.getEyeLocation();
                        Vector toEntity = player1.getEyeLocation().toVector().subtract(eye.toVector());
                        double dot = toEntity.normalize().dot(eye.getDirection());

                        if (dot > 0.99D)
                            inSight.add(otherPlayer);

                    }

                }

            }

        }

        for (GamePlayer otherPlayer : inSight) {

            if (PlayerInteractListener.canAttackAsger(gamePlayer, otherPlayer)) {

                ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
                explodeEffect.sound = Sound.ENTITY_GENERIC_EXPLODE;
                explodeEffect.particleCount = 1;
                explodeEffect.setDynamicOrigin(new DynamicLocation(Bukkit.getPlayer(otherPlayer.getUuid())));
                explodeEffect.start();

                if ((otherPlayer.getChampionType().getMaxHealth() * .3) < otherPlayer.getChampionType().getHealth()) {
                    otherPlayer.getChampionType().subtractHealth(100, plugin, null, otherPlayer);
                    TextUtils.damageMsg(gamePlayer, otherPlayer);
                } else
                    otherPlayer.getChampionType().subtractHealth(otherPlayer.getChampionType().getHealth(), plugin, gamePlayer, otherPlayer);

            }

        }

        PlayerInteractListener.frozen.add(gamePlayer.getUuid());
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

        if (nearestPlayer != null && nearestPlayer.getGameMode() != GameMode.SPECTATOR) {

            player.setVelocity(player.getEyeLocation().getDirection().add(new Vector(0, 2, 0)).normalize().multiply(1.2));

            new BukkitRunnable() {

                @Override
                public void run() {

                    if (nearestPlayer.getGameMode() != GameMode.SPECTATOR) {

                        player.teleport(nearestPlayer);
                        ParticlesUtil.helixEffect(plugin, nearestPlayer);
                        GamePlayer otherPlayer = GeneralUtils.getGamePlayer(nearestPlayer.getUniqueId());
                        otherPlayer.getChampion().subtractHealth(80+gamePlayer.getChampion().getAttackDamage(), plugin, gamePlayer, otherPlayer, false);
                        nearestPlayer.playEffect(EntityEffect.HURT);
                        GeneralUtils.cooldown(plugin, gamePlayer, 5, 100);

                    }

                }

            }.runTaskLater(plugin, 20);

        }

<<<<<<< HEAD
=======
        GeneralUtils.cooldown(plugin, gamePlayer, 5, 85);

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
    }

}
