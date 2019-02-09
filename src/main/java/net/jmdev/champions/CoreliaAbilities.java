package net.jmdev.champions;

import de.slikey.effectlib.EffectManager;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.listener.PlayerInteractListener;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.ParticlesUtil;
import net.jmdev.util.TextUtils;

import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/01/2017 | 18:40
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
public class CoreliaAbilities {

    private Main plugin;
    private EffectManager effectManager;

    public CoreliaAbilities(EffectManager effectManager, Main plugin) {

        this.plugin = plugin;
        this.effectManager = effectManager;

    }

    public void nukeEm(Player player, GamePlayer gamePlayer) {

        Vector playerLookDirection = player.getPlayer().getLocation().getDirection();

        //create a vector perpendicular to the look direction so that it updates position as the yaw changes
        Vector perpVector = new Vector(-playerLookDirection.getZ(), playerLookDirection.getY(), playerLookDirection.getX()).normalize();

        //use this vector to place the particle beam to the right of the player
        double x = player.getPlayer().getLocation().getX() + (perpVector.getX() * .75);//The .75 just places it .75-ish blocks away
        double y = player.getPlayer().getLocation().getY() + 1.75D;
        double z = player.getPlayer().getLocation().getZ() + (perpVector.getZ() * .75);

        TNTPrimed primed = player.getWorld().spawn(new Location(player.getWorld(), x, y, z), TNTPrimed.class);
        primed.setFuseTicks(10000);
        primed.setVelocity(playerLookDirection);
        primed.setIsIncendiary(false);
        primed.setYield(4);
        primed.setMetadata("noblockdamage", new FixedMetadataValue(plugin, true));

        new BukkitRunnable() {

            @Override
            public void run() {

                if (primed.getLocation().subtract(0, 0.5, 0).getBlock().getType() != Material.AIR) {

                    primed.setFuseTicks(0);

                    for (Entity entity : primed.getNearbyEntities(4, 4, 4)) {

                        if (entity instanceof Player) {

                            if (((Player) entity).getGameMode() != GameMode.SPECTATOR) {

                                for (GamePlayer otherPlayer : Main.gamePlayers) {

                                    if (otherPlayer.getUuid().equals(entity.getUniqueId())) {

                                        if (gamePlayer.getTeam().getName().equalsIgnoreCase("red") && otherPlayer.getTeam().getName().equalsIgnoreCase("blue")) {

<<<<<<< HEAD
                                            otherPlayer.getChampion().subtractHealth(15 + (gamePlayer.getChampion().getAttackDamage() * 0.6), plugin, gamePlayer, otherPlayer, false);
=======
                                            otherPlayer.getChampionType().subtractHealth(30 + (gamePlayer.getChampionType().getAttackDamage() * 0.3), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                                            entity.setVelocity(entity.getLocation().getDirection().multiply(-1).add(new Vector(0, 0.5, 0)));

                                            entity.playEffect(EntityEffect.HURT);

                                        } else if (gamePlayer.getTeam().getName().equalsIgnoreCase("blue") && otherPlayer.getTeam().getName().equalsIgnoreCase("red")) {

<<<<<<< HEAD
                                            otherPlayer.getChampion().subtractHealth(15 + (gamePlayer.getChampion().getAttackDamage() * 0.6), plugin, gamePlayer, otherPlayer, false);
=======
                                            otherPlayer.getChampionType().subtractHealth(30 + (gamePlayer.getChampionType().getAttackDamage() * 0.3), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                                            entity.setVelocity(entity.getLocation().getDirection().multiply(-1).add(new Vector(0, 0.5, 0)));

                                            entity.playEffect(EntityEffect.HURT);

                                        }

                                    }

                                }

                            }

                        } else if (entity.getType() == EntityType.ZOMBIE) {

                            Zombie zombie = (Zombie) entity;
                            if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                                zombie.damage(15 + (gamePlayer.getChampion().getAttackDamage() * 0.6), primed);
                                zombie.setVelocity(zombie.getLocation().getDirection().multiply(-0.5).add(new Vector(0, 0.5, 0)));

                                if (zombie.isDead()) {
                                    gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                                    player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                                    GeneralUtils.levelUp(gamePlayer);
                                }

                            } else if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.RED.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("blue")) {

                                zombie.damage(15 + (gamePlayer.getChampion().getAttackDamage() * 0.6), primed);
                                zombie.setVelocity(zombie.getLocation().getDirection().multiply(-0.5).add(new Vector(0, 0.5, 0)));

                                if (zombie.isDead()) {
                                    gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                                    player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                                    GeneralUtils.levelUp(gamePlayer);
                                }

                            }

                        }

                    }

                    cancel();

                }

            }

        }.runTaskTimer(plugin, 0, 5);

<<<<<<< HEAD
        GeneralUtils.cooldown(plugin, gamePlayer, 2, ((Corelia) gamePlayer.getChampion()).isBlastZoneActive() ? 2 : 9);
=======
        GeneralUtils.cooldown(plugin, gamePlayer, 2, ((Corelia) gamePlayer.getChampionType()).isBlastZoneActive() ? 20 : 20 * 6);

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }

    public void blastZone(Player player, GamePlayer gamePlayer) {

        Location origin = player.getLocation();
        ((Corelia) gamePlayer.getChampion()).setBlastZoneActive(true);

        for (int i = 0; i < 5; i++) {

            final boolean[] flip = {false};
            int j = i;

            new BukkitRunnable() {

                @Override
                public void run() {

                    //typically it would be originX and originY, but since we're in some lopsided universe, Z is on our horizontal plane
                    //a point on a circle with given radius is equal to (originX + (radius * sin(degree/rad)), originZ + (radius * cos(degree/rad)))

                    for (int i = 0; i < 360; i++) {

                        float x = (float) (origin.getX() + (7 * Math.sin(i)));
                        float z = (float) (origin.getZ() + (7 * Math.cos(i)));

                        Location particleLocation = new Location(player.getWorld(), x, origin.getY() + 1.2F, z);

                        if (flip[0]) {
                            flip[0] = !flip[0];
                            ParticlesUtil.broadCastColoredParticle(Color.BLACK, particleLocation);

                        } else {
                            flip[0] = !flip[0];
                            ParticlesUtil.broadCastColoredParticle(Color.YELLOW, particleLocation);

                        }

                    }

                    if (player.getLocation().distanceSquared(origin) <= 49) {

<<<<<<< HEAD
                        ((Corelia) gamePlayer.getChampion()).setBlastZoneActive(true);

=======
                        ((Corelia) gamePlayer.getChampionType()).setBlastZoneActive(true);
                        gamePlayer.getChampionType().addHealth(1);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    } else
                        ((Corelia) gamePlayer.getChampion()).setBlastZoneActive(false);

                    if (j == 4)
                        ((Corelia) gamePlayer.getChampion()).setBlastZoneActive(false);

                }

            }.runTaskLater(plugin, i * 20);

        }

        GeneralUtils.cooldown(plugin, gamePlayer, 3, 16);

    }

    public void stupefy(Player player, GamePlayer gamePlayer) {

        ParticlesUtil.doRadialWaveEffect(player, gamePlayer, plugin);
<<<<<<< HEAD
        GeneralUtils.cooldown(plugin, gamePlayer, 4, 9);
=======
        GeneralUtils.cooldown(plugin, gamePlayer, 4, 11);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }

    public void kaboom(Player player, GamePlayer gamePlayer) {

        Entity entity = PlayerInteractListener.getNearestEntityInSight(gamePlayer, player, 15);
        if (entity != null && entity instanceof Player) {

            for (GamePlayer otherPlayer : Main.gamePlayers) {

                if (otherPlayer.getUuid().equals(entity.getUniqueId())) {

<<<<<<< HEAD
                    ParticlesUtil.doHeatSeekerEffect(player, (Player) entity, 50, plugin);
=======
                    ParticlesUtil.doHeatSeekerEffect(player, (Player) entity, plugin);

                    otherPlayer.getChampionType().subtractHealth(125, plugin, gamePlayer, otherPlayer);
                    entity.setVelocity(entity.getLocation().getDirection().add(new Vector(0, 0.5, 0)).multiply(-1.5));
                    entity.playEffect(EntityEffect.HURT);

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                    GeneralUtils.cooldown(plugin, gamePlayer, 5, 80);

                    break;

                }

            }

        }

    }

}
