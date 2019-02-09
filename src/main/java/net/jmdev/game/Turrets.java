package net.jmdev.game;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;

import net.jmdev.Main;
import net.jmdev.entity.HitBox;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.TextUtils;
import net.jmdev.util.Title;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/23/2017 | 02:52
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
public class Turrets {

    private EffectManager effectManager;
    private Main plugin;

    public Turrets(Main plugin, EffectManager effectManager) {
        this.plugin = plugin;
        this.effectManager = effectManager;
    }

    private Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (Entity entity : chunk.getEntities()) {
                    if (entity.getUniqueId().equals(uniqueId))
                        return entity;
                }
            }
        }

        return null;
    }

    // TODO: Track enemy attacks
    void searchBlueLocations(World world) {

        if (GameMode.getMode() == GameMode._3V3) {

            ArrayList<String> locations = new ArrayList<>();
            locations.add(TextUtils.locationToString(new Location(world, 693.5, 22, 6.5), false) + "%left%2"); //location,left,2
            locations.add(TextUtils.locationToString(new Location(world, 716.5, 22, -24.5), false) + "%left%1");
            locations.add(TextUtils.locationToString(new Location(world, 754.5, 22, -24.5), false) + "%left%0");
            locations.add(TextUtils.locationToString(new Location(world, 693.5, 22, 68.5), false) + "%right%2");
            locations.add(TextUtils.locationToString(new Location(world, 716.5, 22, 99.5), false) + "%right%1");
            locations.add(TextUtils.locationToString(new Location(world, 754.5, 22, 99.5), false) + "%right%0");

            for (String location : locations) {
                HitBox villager = new HitBox(((CraftWorld) world).getHandle());
                villager.setPosition(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 14, 0));
                ((CraftWorld) world).getHandle().addEntity(villager, CreatureSpawnEvent.SpawnReason.CUSTOM);
                TurretPriorityHandler.addTurret(villager.getUniqueID(), "blue", Lane.fromString(location.split("%")[1]), TextUtils.parseLocation(location.split("%")[0]), Byte.valueOf(location.split("%")[2]));
                //Bukkit.broadcastMessage(blueLocations.get(location));
                GeneralUtils.addPersitence(villager);
                villager.setCustomNameVisible(false);
                ((Slime) villager.getBukkitEntity()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
                ((Slime) villager.getBukkitEntity()).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
                ((Slime) villager.getBukkitEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(3000D);
                ((Slime) villager.getBukkitEntity()).setAI(false);
                ((Slime) villager.getBukkitEntity()).setRemoveWhenFarAway(false);
                villager.getBukkitEntity().setInvulnerable(false);
                villager.persistent = true;
                villager.setSize(6);
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
                ((Slime) villager.getBukkitEntity()).addPotionEffect(potionEffect);
                ArmorStand armorStand = world.spawn(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 15.5, 0), ArmorStand.class);
                armorStand.setGravity(false);
                armorStand.setVisible(false);
                armorStand.setInvulnerable(true);
                armorStand.setSmall(false);
                armorStand.setCanPickupItems(false);
                armorStand.setPassenger(villager.getBukkitEntity());
                Slime slime = world.spawn(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 15.5, 0), Slime.class);
                slime.setAI(false);
                slime.setInvulnerable(true);
                slime.setCustomNameVisible(false);
                slime.setSize(-1);
                slime.addPotionEffect(potionEffect);
                slime.setRemoveWhenFarAway(false);
                ArmorStand armorStand1 = world.spawn(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 16.5, 0), ArmorStand.class);
                armorStand1.setGravity(false);
                armorStand1.setVisible(false);
                armorStand1.setCustomNameVisible(true);
                armorStand1.setInvulnerable(true);
                armorStand1.setSmall(true);
                armorStand1.setCanPickupItems(false);
                slime.setPassenger(armorStand1);
                villager.getBukkitEntity().setPassenger(slime);
                ((Slime) villager.getBukkitEntity()).setMaxHealth(1500);
                ((Slime) villager.getBukkitEntity()).setHealth(1500);
                //slime.setCollidable(false);
                //((Slime) villager.getBukkitEntity()).setCollidable(false);
                villager.getBukkitEntity().setMetadata("blue", new FixedMetadataValue(plugin, true));

            }

        }

        new BukkitRunnable() {

            @Override
            public void run() {

                for (Iterator<Turret> iterator = TurretPriorityHandler.getBlueTurrets().iterator(); iterator.hasNext(); ) {
                   Turret entry = iterator.next();
                    Entity entity = getEntityByUniqueId(entry.getTurretUUID());
                    if (entity != null && !entity.isDead()) {

                        updateTurretHealth(entity);

                    } else {


                        Location loc = entry.getLocation();
                        TNTPrimed tntPrimed = loc.getWorld().spawn(loc.add(0, 3, 0), TNTPrimed.class);
                        tntPrimed.setFuseTicks(0);
                        tntPrimed.setIsIncendiary(false);
                        new Title("", TextUtils.formatText("&7&lA &9&lblue &7&lturret has been destroyed! &8(" + (TurretPriorityHandler.getBlueTurrets().size() - 1) + "/6)"), 1, 1, 1).broadcast();
                        TurretPriorityHandler.removeTurret(entry);
                        for (Object uuid : Main.teams.get(0)) {

                            GamePlayer gamePlayer1 = GeneralUtils.getGamePlayer((UUID) uuid);
<<<<<<< HEAD

                            gamePlayer1.getChampion().setExp(gamePlayer1.getChampion().getExp() + 100);
                            Bukkit.getPlayer((UUID) uuid).sendMessage(TextUtils.formatText("&6&l+100 EXP"));
                            GeneralUtils.levelUp(gamePlayer1);

                            if (GameMode.getMode() == GameMode._3V3 ) {

                                if ((TurretPriorityHandler.getRedTurrets().size() - 1) == 5) {

                                    gamePlayer1.setGold(gamePlayer1.getGold() + Gold.firstEnemyTowerKill);
                                    Bukkit.getPlayer(gamePlayer1.getUuid()).sendMessage(TextUtils.formatText("&7[&e+300 Gold&7]"));

                                } else {

                                    gamePlayer1.setGold(gamePlayer1.getGold() + Gold.enemyTowerKill);
                                    Bukkit.getPlayer(gamePlayer1.getUuid()).sendMessage(TextUtils.formatText("&7[&e+200 Gold&7]"));

                                }
=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                            gamePlayer1.getChampionType().setExp(gamePlayer1.getChampionType().getExp() + 100);
                            Bukkit.getPlayer((UUID) uuid).sendMessage(TextUtils.formatText("&6&l+100 EXP"));
                            GeneralUtils.levelUp(gamePlayer1);

                        }
                        if (entity != null) {
                            entity.getPassenger().getPassenger().remove(); //removes the armorstand with health
                            entity.getPassenger().remove(); //removes the mini slime
                            entity.getVehicle().remove(); //removes the armorstand being ridden
                        }

                        iterator.remove();
                        return;

                    }
                }

            }

        }.runTaskTimer(plugin, 0, 10);

        new BukkitRunnable() {

            @Override
            public void run() {

                for (Turret turret : TurretPriorityHandler.getBlueTurrets()) {

                    for (org.bukkit.entity.Entity entity : turret.getLocation().getWorld().getNearbyEntities(turret.getLocation(), 10, 20, 10)) {

                        if (entity instanceof Zombie) {

                            if (((LeatherArmorMeta) ((Zombie) entity).getEquipment().getBoots().getItemMeta()).getColor().asRGB() == Color.RED.asRGB()) {

                                shootTurret((Zombie) entity, turret);
                                entity.playEffect(EntityEffect.HURT);
                                break;

                            }

                        } else {

                            GamePlayer gamePlayer = null;

                            for (GamePlayer otherPlayer : Main.gamePlayers) {

                                if (otherPlayer.getUuid().equals(entity.getUniqueId()) && Bukkit.getPlayer(otherPlayer.getUuid()).getGameMode() != org.bukkit.GameMode.SPECTATOR) {

                                    gamePlayer = otherPlayer;
                                    break;

                                }

                            }

                            if (gamePlayer != null && !turret.getTeamColor().equalsIgnoreCase(gamePlayer.getTeam().getName())) {

                                shootTurret(gamePlayer, entity, turret);
                                break;

                            }

                        }

                    }

                }

            }

        }.runTaskTimer(plugin, 0, (long) (1.5 * 20));

    }


    public void searchRedLocations(World world) {

        if (GameMode.getMode() == GameMode._3V3) {


            ArrayList<String> locations = new ArrayList<>();

            locations.add(TextUtils.locationToString(new Location(world, 806.5, 22, 99.5), false) + "%left%0");
            locations.add(TextUtils.locationToString(new Location(world, 844.5, 22, 99.5), false) + "%left%1");
            locations.add(TextUtils.locationToString(new Location(world, 867.5, 22, 68.5), false) + "%left%2");
            locations.add(TextUtils.locationToString(new Location(world, 806.5, 22, -24.5), false) + "%right%0");
            locations.add(TextUtils.locationToString(new Location(world, 844.5, 22, -24.5), false) + "%right%1");
            locations.add(TextUtils.locationToString(new Location(world, 867.5, 22, 6.5), false) + "%right%2");

            for (String location : locations) {
                HitBox villager = new HitBox(((CraftWorld) world).getHandle());
                villager.setPosition(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 14, 0));
                ((CraftWorld) world).getHandle().addEntity(villager, CreatureSpawnEvent.SpawnReason.CUSTOM);
                TurretPriorityHandler.addTurret(villager.getUniqueID(), "red", Lane.fromString(location.split("%")[1]), TextUtils.parseLocation(location.split("%")[0]), Byte.valueOf(location.split("%")[2]));
                //Bukkit.broadcastMessage(redLocations.get(location));
                GeneralUtils.addPersitence(villager);
                villager.setCustomNameVisible(false);
                ((Slime) villager.getBukkitEntity()).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
                ((Slime) villager.getBukkitEntity()).getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
                ((Slime) villager.getBukkitEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(3000D);
                ((Slime) villager.getBukkitEntity()).setAI(false);
                ((Slime) villager.getBukkitEntity()).setRemoveWhenFarAway(false);
                villager.getBukkitEntity().setInvulnerable(false);
                villager.persistent = true;
                villager.setSize(6);
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
                ((Slime) villager.getBukkitEntity()).addPotionEffect(potionEffect);
                ArmorStand armorStand = world.spawn(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 15.5, 0), ArmorStand.class);
                armorStand.setGravity(false);
                armorStand.setVisible(false);
                armorStand.setInvulnerable(true);
                armorStand.setSmall(false);
                armorStand.setCanPickupItems(false);
                armorStand.setPassenger(villager.getBukkitEntity());
                Slime slime = world.spawn(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 15.5, 0), Slime.class);
                slime.setAI(false);
                slime.setInvulnerable(true);
                slime.setCustomNameVisible(false);
                slime.setSize(-1);
                slime.addPotionEffect(potionEffect);
                slime.setRemoveWhenFarAway(false);
                ArmorStand armorStand1 = world.spawn(TextUtils.parseLocation(location.split("%")[0]).subtract(0, 16.5, 0), ArmorStand.class);
                armorStand1.setGravity(false);
                armorStand1.setVisible(false);
                armorStand1.setCustomNameVisible(true);
                armorStand1.setInvulnerable(true);
                armorStand1.setSmall(true);
                armorStand1.setCanPickupItems(false);
                slime.setPassenger(armorStand1);
                villager.getBukkitEntity().setPassenger(slime);
                ((Slime) villager.getBukkitEntity()).setMaxHealth(1500);
                ((Slime) villager.getBukkitEntity()).setHealth(1500);
                //slime.setCollidable(false);
                //((Slime) villager.getBukkitEntity()).setCollidable(false);
                villager.getBukkitEntity().setMetadata("red", new FixedMetadataValue(plugin, true));

            }

        }

        new BukkitRunnable() {

            @Override
            public void run() {

                for (Iterator<Turret> iterator = TurretPriorityHandler.getRedTurrets().iterator(); iterator.hasNext(); ) {
                   Turret entry = iterator.next();
                    Entity entity = getEntityByUniqueId(entry.getTurretUUID());
                    if (entity != null && !entity.isDead()) {

                        updateTurretHealth(entity);

                    } else {

                        Location loc = entry.getLocation();
                        TNTPrimed tntPrimed = loc.getWorld().spawn(loc.add(0, 3, 0), TNTPrimed.class);
                        tntPrimed.setFuseTicks(0);
                        tntPrimed.setIsIncendiary(false);
                        new Title("", TextUtils.formatText("&7&lA &c&lred &7&lturret has been destroyed! &8(" + (TurretPriorityHandler.getRedTurrets().size() - 1) + "/6)"), 1, 1, 1).broadcast();
                        TurretPriorityHandler.removeTurret(entry);
                        for (Object uuid : Main.teams.get(1)) {

                            GamePlayer gamePlayer1 = GeneralUtils.getGamePlayer((UUID) uuid);

                            gamePlayer1.getChampion().setExp(gamePlayer1.getChampion().getExp() + 100);
                            Bukkit.getPlayer((UUID) uuid).sendMessage(TextUtils.formatText("&6&l+100 EXP"));
                            GeneralUtils.levelUp(gamePlayer1);

                            if (GameMode.getMode() == GameMode._3V3 ) {

                                if ((TurretPriorityHandler.getRedTurrets().size() - 1) == 5) {

                                    gamePlayer1.setGold(gamePlayer1.getGold() + Gold.firstEnemyTowerKill);
                                    Bukkit.getPlayer(gamePlayer1.getUuid()).sendMessage(TextUtils.formatText("&7[&e+300 Gold&7]"));

                                } else {

                                    gamePlayer1.setGold(gamePlayer1.getGold() + Gold.enemyTowerKill);
                                    Bukkit.getPlayer(gamePlayer1.getUuid()).sendMessage(TextUtils.formatText("&7[&e+200 Gold&7]"));

                                }

                            }


                        }

                        if (entity != null) {
                            entity.getPassenger().getPassenger().remove(); //removes the armorstand with health
                            entity.getPassenger().remove(); //removes the mini slime
                            entity.getVehicle().remove(); //removes the armorstand being ridden
                        }
                        iterator.remove();
                        return;

                    }
                }

            }

        }.runTaskTimer(plugin, 0, 10);

        new BukkitRunnable() {

            @Override
            public void run() {

                for (Turret turret : TurretPriorityHandler.getRedTurrets()) {

                    for (org.bukkit.entity.Entity entity : turret.getLocation().getWorld().getNearbyEntities(turret.getLocation(), 10, 20, 10)) {

                        if (entity instanceof Zombie) {

                            if (((LeatherArmorMeta) ((Zombie) entity).getEquipment().getBoots().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB()) {

                                shootTurret((Zombie) entity, turret);
                                entity.playEffect(EntityEffect.HURT);
                                break;

                            }

                        } else {

                            GamePlayer gamePlayer = null;

                            for (GamePlayer otherPlayer : Main.gamePlayers) {

                                if (otherPlayer.getUuid().equals(entity.getUniqueId()) && Bukkit.getPlayer(otherPlayer.getUuid()).getGameMode() != org.bukkit.GameMode.SPECTATOR) {

                                    gamePlayer = otherPlayer;
                                    break;

                                }

                            }

                            if (gamePlayer != null && !turret.getTeamColor().equalsIgnoreCase(gamePlayer.getTeam().getName())) {

                                shootTurret(gamePlayer, entity, turret);
                                break;

                            }

                        }

                    }

                }

            }

        }.runTaskTimer(plugin, 0, (long) (1.5 * 20));

    }

    private void shootTurret(GamePlayer gamePlayer, Entity entity, Turret turret) {

        LineEffect lineEffect = new LineEffect(effectManager);
        lineEffect.particle = ParticleEffect.CRIT;
        lineEffect.speed = 0.2F;

        SmokeEffect smokeEffect = new SmokeEffect(effectManager);
        smokeEffect.speed = 0.2F;
        smokeEffect.particleCount = 275;

        Random random = new Random();
        int damage = random.nextInt(50 - 30 + 1) + 30;
        smokeEffect.setDynamicOrigin(new DynamicLocation(turret.getLocation()));
        smokeEffect.start();
        lineEffect.setDynamicOrigin(new DynamicLocation(turret.getLocation()));
        lineEffect.setDynamicTarget(new DynamicLocation(entity.getLocation().add(new Vector(0, 1.5, 0))));
        lineEffect.start();
<<<<<<< HEAD
        gamePlayer.getChampion().subtractHealth(damage, plugin, null, gamePlayer, false);
=======
        gamePlayer.getChampionType().subtractHealth(damage, plugin, null, gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
        Bukkit.getPlayer(gamePlayer.getUuid()).playEffect(EntityEffect.HURT);

    }

    private void shootTurret(Zombie minion, Turret turret) {

        LineEffect lineEffect = new LineEffect(effectManager);
        lineEffect.particle = ParticleEffect.CRIT;
        lineEffect.speed = 0.2F;

        SmokeEffect smokeEffect = new SmokeEffect(effectManager);
        smokeEffect.speed = 0.2F;
        smokeEffect.particleCount = 275;

        Random random = new Random();
        int damage = random.nextInt(50 - 30 + 1) + 30;
        smokeEffect.setDynamicOrigin(new DynamicLocation(turret.getLocation()));
        smokeEffect.start();
        lineEffect.setDynamicOrigin(new DynamicLocation(turret.getLocation()));
        lineEffect.setDynamicTarget(new DynamicLocation(minion.getLocation().add(new Vector(0, 1.5, 0))));
        lineEffect.start();
        minion.damage(damage);
        minion.playEffect(EntityEffect.HURT);

    }

    public void updateTurretHealth(Entity entity) {

        final double barPercent = (((LivingEntity) entity).getHealth() / ((LivingEntity) entity).getMaxHealth());
        String levelBar = "::::::::::::::::::::".replace(":", "\u258C");
        int index = (int) (barPercent * 20);

        if (index > 0)
            levelBar = TextUtils.formatText("&a" + levelBar.substring(0, index) + "§c" + levelBar.substring(index, levelBar.length()));
        else
            levelBar = TextUtils.formatText("&c" + levelBar);
        entity.getPassenger().getPassenger().setCustomName(levelBar);

    }

}
