package net.jmdev.listener;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;

import net.jmdev.Connection.Database;
import net.jmdev.Connection.Mount;
import net.jmdev.Connection.PlayerData;
import net.jmdev.Main;
import net.jmdev.champions.AsgerAbilities;
import net.jmdev.champions.CoreliaAbilities;
import net.jmdev.champions.KaineAbilities;
import net.jmdev.champions.XerolAbilities;
import net.jmdev.champions.XiauTsunAbilities;
import net.jmdev.entity.BasicMinion;
import net.jmdev.entity.DonkeyRideable;
import net.jmdev.entity.MysticalGolem;
import net.jmdev.entity.RangedMinion;
import net.jmdev.entity.RideableEntity;
import net.jmdev.entity.SiegeMinion;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.GameState;
import net.jmdev.game.Gold;
import net.jmdev.game.Nexus;
import net.jmdev.game.TurretPriorityHandler;
import net.jmdev.gui.ChampionGUI;
import net.jmdev.gui.MerchantGUI;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.TextUtils;
import net.minecraft.server.v1_9_R2.EnumHorseType;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
<<<<<<< HEAD
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
=======
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftVillager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
import org.bukkit.entity.Villager;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
<<<<<<< HEAD
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
<<<<<<< HEAD
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.ItemStack;
=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/19/2017 | 22:22
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
public class PlayerInteractListener implements Listener {

    private static ArrayList<UUID> frozen = new ArrayList<>();
    private EffectManager effectManager;
    private Main plugin;

    public PlayerInteractListener(EffectManager effectManager, Main plugin) {

        this.effectManager = effectManager;
        this.plugin = plugin;
    }

    public static boolean canAttackAsger(GamePlayer gamePlayer, GamePlayer targetPlayer) {

<<<<<<< HEAD
        return !targetPlayer.getChampion().getName().equalsIgnoreCase("asger") || !targetPlayer.getChampion().isUltActive() || targetPlayer.getChampion().isUltActive() && gamePlayer.getAsgerUlt() > 1;
=======
        return !targetPlayer.getChampionType().getName().equalsIgnoreCase("asger") || !targetPlayer.getChampionType().isUltActive() || targetPlayer.getChampionType().isUltActive() && gamePlayer.getAsgerUlt() > 1;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }

    public static Entity getNearestEntityInSight(GamePlayer gamePlayer, Player player, int range) {

        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight((Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<>();

        for (Block aSightBlock : sightBlock) sight.add(aSightBlock.getLocation());

        for (Location aSight : sight) {

            for (Entity entity : entities) {

                if (Math.abs(entity.getLocation().getX() - aSight.getX()) < 1.3) {

                    if (Math.abs(entity.getLocation().getY() - aSight.getY()) < 1.5) {

                        if (Math.abs(entity.getLocation().getZ() - aSight.getZ()) < 1.3) {

                            if (entity instanceof Player && ((Player) entity).getGameMode() != GameMode.SPECTATOR) {

                                for (GamePlayer otherPlayer : Main.gamePlayers) {

                                    if (otherPlayer.getUuid().equals(entity.getUniqueId())) {

                                        if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName()))
                                            return entity;

                                    }

                                }

                            } else
                                return entity;
                        }

                    }

                }

            }

        }
        return null; //Return null/nothing if no entity was found
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie) {
            if (e.getEntity().hasMetadata("XerolZombie")) {
                new XerolAbilities(effectManager, plugin).createGrave(GeneralUtils.getGamePlayer(Bukkit.getPlayer((UUID) e.getEntity().getMetadata("XerolZombie").get(0).value())), e.getEntity().getLocation());
            }
        }
    }

    private void backStab(Player player, Player target, GamePlayer gamePlayer, GamePlayer targetPlayer) {

        final Location loc = target.getEyeLocation();
        Location o = target.getEyeLocation();

        Vector c = loc.toVector().subtract(o.toVector()); // Get vector between you and other
        Vector d = target.getEyeLocation().getDirection(); // Get direction target is looking at
        double delta = c.dot(d);

        if (canAttackAsger(gamePlayer, targetPlayer)) {

            if (delta > 0) {

<<<<<<< HEAD
                targetPlayer.getChampion().subtractHealth(15 + gamePlayer.getChampion().getAttackDamage(), plugin, gamePlayer, targetPlayer, true);

            } else {

                targetPlayer.getChampion().subtractHealth(gamePlayer.getChampion().getAttackDamage(), plugin, gamePlayer, targetPlayer, true);
=======
                targetPlayer.getChampionType().subtractHealth(10 + gamePlayer.getChampionType().getAttackDamage(), plugin, gamePlayer, targetPlayer);

            } else {

                targetPlayer.getChampionType().subtractHealth(gamePlayer.getChampionType().getAttackDamage(), plugin, gamePlayer, targetPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

            }

        }

    }

    private void demoralize(Player targetPlayer) {

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 4 * 20, 0, false, true);
        targetPlayer.addPotionEffect(potionEffect);

    }

    private void reapersKiss(GamePlayer gamePlayer, GamePlayer targetPlayer) {

        if (canAttackAsger(gamePlayer, targetPlayer)) {

            if (gamePlayer.getChampion().getHealth() + (targetPlayer.getChampion().getHealth() * .05) < gamePlayer.getChampion().getMaxHealth())
                gamePlayer.getChampion().addHealth(20);
            else
                gamePlayer.getChampion().setHealth(gamePlayer.getChampion().getMaxHealth());


<<<<<<< HEAD
            targetPlayer.getChampion().subtractHealth(targetPlayer.getChampion().getHealth() * .05, plugin, gamePlayer, targetPlayer, false);
=======
            targetPlayer.getChampionType().subtractHealth(targetPlayer.getChampionType().getHealth() * .05, plugin, gamePlayer, targetPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

        }

    }

    private void forcefullness(GamePlayer gamePlayer, GamePlayer targetPlayer) {

        if (canAttackAsger(gamePlayer, targetPlayer))
<<<<<<< HEAD
            targetPlayer.getChampion().subtractHealth(gamePlayer.getChampion().getAttackDamage() + (gamePlayer.getChampion().getAttackDamage() * .15), plugin, gamePlayer, targetPlayer, false);
=======
            targetPlayer.getChampionType().subtractHealth(gamePlayer.getChampionType().getAttackDamage() + (gamePlayer.getChampionType().getAttackDamage() * .15), plugin, gamePlayer, targetPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
        //8.2 = ((400 * .40) / x)

    }

    private void decapacitation(GamePlayer targetPlayer) {
<<<<<<< HEAD
        frozen.add(targetPlayer.getUuid());
        new BukkitRunnable() {
=======

        Random random = new Random();

        if (random.nextFloat() <= .3F) {

            frozen.add(targetPlayer.getUuid());
            new BukkitRunnable() {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

            @Override
            public void run() {

                frozen.remove(targetPlayer.getUuid());

            }

        }.runTaskLater(plugin, 40);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        if (e.isCancelled())
            return;

        Location locFrom = e.getFrom();
        Location locTo = e.getTo();

        if (frozen.contains(e.getPlayer().getUniqueId())) {

            if (locFrom.getBlockX() != locTo.getBlockX() || locFrom.getBlockY() != locTo.getBlockY() || locFrom.getBlockZ() != locTo.getBlockZ()) {

                e.setTo(e.getFrom());

            }

        }

    }

    private void doRunicDaggers(Player player, Player target, GamePlayer gamePlayer, GamePlayer targetPlayer) {

        if (canAttackAsger(gamePlayer, targetPlayer)) {

            TextEffect textEffect = new TextEffect(effectManager);
            textEffect.size = 0.1F;
            textEffect.setDynamicOrigin(new DynamicLocation(target.getLocation().add(new Vector(0, 3, 0))));
            textEffect.yaw = 180F;
            textEffect.duration = 5;

            if (gamePlayer.lastAttack == 0 || System.currentTimeMillis() > (gamePlayer.lastAttack + 7000) || gamePlayer.getXiauTsunStacks() == 1) {

                textEffect.text = "2";
                textEffect.start();
                gamePlayer.setXiauTsunStacks(2);
                gamePlayer.lastAttack = System.currentTimeMillis();
                backStab(player, target, gamePlayer, targetPlayer);
                TextUtils.damageMsg(gamePlayer, targetPlayer);

            } else if (gamePlayer.getXiauTsunStacks() == 2) {

                textEffect.text = "3";
                textEffect.start();
                gamePlayer.setXiauTsunStacks(3);
                gamePlayer.lastAttack = System.currentTimeMillis();
                backStab(player, target, gamePlayer, targetPlayer);
                demoralize(target);
                TextUtils.damageMsg(gamePlayer, targetPlayer);

            } else if (gamePlayer.getXiauTsunStacks() == 3) {

                textEffect.text = "4";
                textEffect.start();
                gamePlayer.setXiauTsunStacks(4);
                gamePlayer.lastAttack = System.currentTimeMillis();
                backStab(player, target, gamePlayer, targetPlayer);
                demoralize(target);
                TextUtils.damageMsg(gamePlayer, targetPlayer);

                if (targetPlayer.getChampion().getHealth() <= 0) {
                    TextUtils.damageMsg(gamePlayer, targetPlayer);
                    return;
                }

                reapersKiss(gamePlayer, targetPlayer);
                TextUtils.damageMsg(gamePlayer, targetPlayer);

            } else if (gamePlayer.getXiauTsunStacks() == 4) {

                textEffect.text = "5";
                textEffect.start();
                gamePlayer.setXiauTsunStacks(5);
                gamePlayer.lastAttack = System.currentTimeMillis();
                backStab(player, target, gamePlayer, targetPlayer);
                demoralize(target);

                if (targetPlayer.getChampion().getHealth() <= 0) {
                    TextUtils.damageMsg(gamePlayer, targetPlayer);
                    return;
                }

                reapersKiss(gamePlayer, targetPlayer);

                if (targetPlayer.getChampion().getHealth() <= 0) {
                    TextUtils.damageMsg(gamePlayer, targetPlayer);
                    return;
                }

                forcefullness(gamePlayer, targetPlayer);
                TextUtils.damageMsg(gamePlayer, targetPlayer);


            } else {

                textEffect.text = "x";
                textEffect.start();
                gamePlayer.setXiauTsunStacks(1);
                gamePlayer.lastAttack = System.currentTimeMillis();
                backStab(player, target, gamePlayer, targetPlayer);
                demoralize(target);
                if (targetPlayer.getChampion().getHealth() <= 0) {
                    TextUtils.damageMsg(gamePlayer, targetPlayer);
                    return;
                }
                reapersKiss(gamePlayer, targetPlayer);
                if (targetPlayer.getChampion().getHealth() <= 0) {
                    TextUtils.damageMsg(gamePlayer, targetPlayer);
                    return;
                }
                forcefullness(gamePlayer, targetPlayer);
                decapacitation(targetPlayer);
                TextUtils.damageMsg(gamePlayer, targetPlayer);
            }


        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {

        if (e.getEntity() != null && e.getEntity().hasMetadata("noblockdamage"))
            e.blockList().clear();

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(ExplosionPrimeEvent e) {

        if (e.getEntity().hasMetadata("noblockdamage")) {

            e.setRadius(4);
            return;
        }

        if (e.getEntityType() == EntityType.PRIMED_TNT)
            e.setRadius(16);

    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {

        e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {

        if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            e.setCancelled(true);
            return;
        } else if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            e.setCancelled(true);
            return;
        } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
            return;
        }

        if (e.getEntityType() == EntityType.VILLAGER) {

            Villager v = (Villager) e.getEntity();

            if (v.getProfession() == Villager.Profession.LIBRARIAN)
                e.setCancelled(true);

        } else
            e.setCancelled(true);

    }

    @EventHandler
    public void onEntityDamageByXerol(EntityDamageByEntityEvent e) {
        if (GameState.getGameState() == GameState.ENDED) {
            return;
        }
        if (e.getDamager() instanceof Player) {
            GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getDamager().getUniqueId());
            if (gamePlayer != null) {
                if (gamePlayer.xerolEternalHunger) {

                    if (e.getEntity() instanceof Player) {

                        GamePlayer target = GeneralUtils.getGamePlayer(e.getEntity().getUniqueId());
                        if (target != null) {
                            if (!target.getTeam().equals(gamePlayer.getTeam())) {
                                gamePlayer.xerolEternalHunger = false;
                                LineEffect le = new LineEffect(effectManager);
                                le.particle = ParticleEffect.VILLAGER_HAPPY;
                                le.setDynamicOrigin(new DynamicLocation(e.getDamager().getLocation().add(-1, 2, 1)));
                                le.setDynamicTarget(new DynamicLocation(e.getDamager().getLocation().add(1, 2, -1)));
                                le.start();
                                LineEffect le2 = new LineEffect(effectManager);
                                le2.particle = ParticleEffect.REDSTONE;
                                le2.setDynamicOrigin(new DynamicLocation(e.getEntity().getLocation().add(-1, 2, 1)));
                                le2.setDynamicTarget(new DynamicLocation(e.getEntity().getLocation().add(1, 2, -1)));
                                le2.start();
                                BukkitTask bt = new BukkitRunnable() {

                                    @Override
                                    public void run() {

<<<<<<< HEAD
                                        target.getChampion().subtractHealth(target.getChampion().getMaxHealth() * 0.04, plugin, gamePlayer, target, false);
                                        gamePlayer.getChampion().addHealth(target.getChampion().getMaxHealth() * 0.1);
=======
                                        target.getChampionType().subtractHealth(target.getChampionType().getMaxHealth() * 0.04, plugin, gamePlayer, target);
                                        gamePlayer.getChampionType().addHealth(target.getChampionType().getMaxHealth() * 0.1);
                                        GeneralUtils.hasDied(plugin, gamePlayer, target);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                                        BleedEffect be = new BleedEffect(effectManager);
                                        be.setDynamicOrigin(new DynamicLocation(e.getEntity()));
                                        be.iterations = 1;
                                        be.period = 1;
                                        be.start();

                                    }

                                }.runTaskTimer(plugin, 0, 19);

                                new BukkitRunnable() {

                                    @Override
                                    public void run() {

                                        bt.cancel();

                                    }

                                }.runTaskLater(plugin, 20 * 5);

                            }
                        }
                    } else {
                        Entity ent = e.getEntity();
                        if (ent instanceof Zombie) {

                            gamePlayer.xerolEternalHunger = false;

                            BukkitTask bt = new BukkitRunnable() {

                                @Override
                                public void run() {
                                    gamePlayer.getChampion().addHealth(((LivingEntity) ent).getMaxHealth() * 0.1);
                                    ((LivingEntity) ent).damage(((LivingEntity) ent).getMaxHealth() * 0.04);
                                    float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                                    gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);

                                }

                            }.runTaskTimer(plugin, 0, 19);

                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    bt.cancel();

                                }

                            }.runTaskLater(plugin, 20 * 5);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerHitByWither(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> e.getEntity().remove(), 20 * 5);
        }
        if (e.getEntity() instanceof WitherSkull) {
            if (e.getEntity().hasMetadata("XerolUndeadRising")) {
                for (Entity ent : e.getEntity().getNearbyEntities(2, 2, 2)) {
                    if (ent instanceof Player) {
                        if (!((GamePlayer) e.getEntity().getMetadata("XerolUndeadRising").get(0).value()).getTeam().equals(GeneralUtils.getGamePlayer(ent.getUniqueId()).getTeam())) {

                            for (int i = 0; i < 5; i++) {
                                PigZombie zombie = ent.getWorld().spawn(ent.getLocation(), PigZombie.class);
                                zombie.setMetadata("XerolZombie", new FixedMetadataValue(plugin, e.getEntity().getMetadata("XerolUndeadRising").get(0).value()));
                                zombie.setAngry(true);
                                zombie.setTarget(((Player) ent));
<<<<<<< HEAD
                                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        zombie.remove();
                                        new XerolAbilities(effectManager, plugin).createGrave(GeneralUtils.getGamePlayer(Bukkit.getPlayer((UUID) e.getEntity().getMetadata("XerolUndeadRising").get(0).value())), zombie.getLocation());

                                    }
                                }, 25 * 5);
=======
                                Bukkit.getScheduler().runTaskLater(plugin, zombie::remove, 25 * 5);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof WitherSkull) {
            if (e.getDamager().hasMetadata("XerolUndeadRising")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onXerolZombieTarget(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {
<<<<<<< HEAD

            if (((Player) e.getTarget()).getGameMode() != GameMode.SURVIVAL) {
                e.setCancelled(true);
                return;
            }

=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
            if (e.getEntity() instanceof PigZombie) {
                if (e.getEntity().hasMetadata("XerolZombie")) {
                    GamePlayer gp = (GamePlayer) e.getEntity().getMetadata("XerolZombie").get(0).value();
                    if (gp != null) {
<<<<<<< HEAD
                        if (gp.getTeam().equals(GeneralUtils.getGamePlayer(e.getTarget().getUniqueId()).getTeam()))
                            e.setCancelled(true);
=======
                        if (gp.getTeam().equals(GeneralUtils.getGamePlayer(e.getTarget().getUniqueId()).getTeam())) {
                            e.setCancelled(true);
                        }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {

        if (e.getInventory().getType() == InventoryType.MERCHANT)
            e.setCancelled(true);

    }

<<<<<<< HEAD
    @EventHandler
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent e) {
        e.setCollisionCancelled(true);
        e.setCancelled(true);
    }

=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (GameState.getGameState() == GameState.ENDED) {
            e.setCancelled(true);
            return;
        }
<<<<<<< HEAD
        if (e.getDamager().getType() == EntityType.LIGHTNING)
            return;

        if (e.getEntity() instanceof Player) {
            if (e.getEntity().isInsideVehicle()) {
                Entity vehicle = e.getEntity().getVehicle();
                e.getEntity().eject();
                vehicle.remove();
            }
        }
=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03


        if (e.getDamager() instanceof Player)
            ((Player) e.getDamager()).updateInventory();

<<<<<<< HEAD
        if (e.getDamager() instanceof Player && e.getEntityType() == EntityType.PLAYER || e.getDamager() instanceof Player && e.getEntityType() == EntityType.SLIME || e.getDamager().getType() == EntityType.ZOMBIE || e.getDamager() instanceof Player && e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.PIG_ZOMBIE || e.getDamager() instanceof CraftTNTPrimed || e.getDamager() instanceof Arrow || e.getDamager().getType() == EntityType.PIG_ZOMBIE) {
=======
        if (e.getDamager() instanceof Player && e.getEntityType() == EntityType.PLAYER || e.getDamager() instanceof Player && e.getEntityType() == EntityType.VILLAGER || e.getDamager().getType() == EntityType.ZOMBIE || e.getDamager() instanceof Player && e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.PIG_ZOMBIE || e.getDamager() instanceof CraftTNTPrimed || e.getDamager() instanceof Arrow) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

            if (e.getEntity() instanceof Player) {
                if (((Player) e.getEntity()).getGameMode() == GameMode.SPECTATOR) {
                    e.setCancelled(true);
                    return;
                }
            }

            if (e.getDamager().getType() == EntityType.ZOMBIE || e.getDamager().getType() == EntityType.PIG_ZOMBIE) {

                int randomDamage;

                if (e.getDamager() instanceof BasicMinion)
                    randomDamage = new Random().nextInt((25 - 15) + 1) + 15;
                else if (e.getDamager() instanceof RangedMinion)
                    randomDamage = new Random().nextInt((20 - 16) + 1) + 16;
                else if (e.getDamager() instanceof PigZombie)
                    randomDamage = 10;
                else
                    randomDamage = new Random().nextInt((40 - 25) + 1) + 25;

                if (!(e.getEntity() instanceof Player)) {

                    if (e.getEntityType() == EntityType.ZOMBIE)
                        ((Zombie) e.getEntity()).damage(randomDamage);
                    else if (e.getEntityType() == EntityType.SLIME)
                        ((Slime) e.getEntity()).damage(randomDamage);
                    else if (e.getEntityType() == EntityType.PIG_ZOMBIE)
                        ((PigZombie) e.getEntity()).damage(randomDamage);


                    e.getEntity().playEffect(EntityEffect.HURT);
                    e.setCancelled(true);
                    return;

                } else {

<<<<<<< HEAD
                    GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getEntity().getUniqueId());
                    if (gamePlayer != null) {
=======
                    for (GamePlayer gamePlayer : Main.gamePlayers) {

                        if (gamePlayer.getUuid().equals(e.getEntity().getUniqueId())) {

                            gamePlayer.getChampionType().subtractHealth(randomDamage, plugin, null, gamePlayer);
                            e.getEntity().playEffect(EntityEffect.HURT);
                            e.setCancelled(true);
                            return;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                        gamePlayer.getChampion().subtractHealth(randomDamage, plugin, null, gamePlayer, true);
                        e.getEntity().playEffect(EntityEffect.HURT);

                    }

                    e.setCancelled(true);
                    return;

                }

            } else if (e.getDamager() instanceof CraftTNTPrimed) {

                if (e.getEntity() instanceof Slime)
                    e.setCancelled(true);
                else if (e.getDamage() == 0)
                    e.setCancelled(true);

                return;

            } else if (e.getDamager() instanceof IronGolem) {

                MysticalGolem golem = (MysticalGolem) e.getDamager();

                golem.attackCount++;

                if (golem.attackCount == 3)
                    golem.attackCount = 0;

                if (e.getEntity().getType() == EntityType.PLAYER) {

                    GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getEntity().getUniqueId());
                    gamePlayer.getChampion().subtractHealth(30, plugin, null, gamePlayer, false);
                    e.getEntity().playEffect(EntityEffect.HURT);
                    e.setCancelled(true);

                } else {

                    ((Damageable) e.getEntity()).damage(60);

                }

                return;

            }

            Player player = null;

            GamePlayer gamePlayer = null;
            GamePlayer otherPlayer = null;

            if (e.getDamager() instanceof Player) {
                player = (Player) e.getDamager();
                gamePlayer = GeneralUtils.getGamePlayer(e.getDamager().getUniqueId());
            } else if (e.getDamager() instanceof Arrow) {

                Arrow a = (Arrow) e.getDamager();

                if (a.getShooter() instanceof Player) {

                    Player p = (Player) a.getShooter();
                    player = p;
                    gamePlayer = GeneralUtils.getGamePlayer(p.getUniqueId());

                }

            }


            String name = gamePlayer.getChampion().getName();

            if (player == null || player.getInventory().getHeldItemSlot() != 0 || (!name.equalsIgnoreCase("Asger") && !name.equalsIgnoreCase("Xiau Tsun") && !name.equalsIgnoreCase("Xerol") && !name.equalsIgnoreCase("Corelia") || (name.equalsIgnoreCase("Corelia") && !(e.getDamager() instanceof Arrow)))) {
                e.setCancelled(true);
                return;
            }


            if (e.getEntity() instanceof Arrow)
                e.getEntity().remove();

            if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger")) {

                if ((gamePlayer.basicAttack + 900) > System.currentTimeMillis()) {

                    System.out.println("Cooldown: " + ((gamePlayer.basicAttack + 900) > System.currentTimeMillis()));
                    e.setCancelled(true);
                    return;
                }

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xiau Tsun")) {

                if ((gamePlayer.basicAttack + 1000) > System.currentTimeMillis()) {

                    System.out.println("Cooldown: " + ((gamePlayer.basicAttack + 1000) > System.currentTimeMillis()));
                    e.setCancelled(true);
                    return;
                }

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xerol")) {

                for (Entity ent : e.getEntity().getNearbyEntities(8, 8, 8)) {
                    if (!ent.equals(e.getDamager())) {

                        if (ent instanceof Player) {

                            GamePlayer target = GeneralUtils.getGamePlayer(ent.getUniqueId());

                            if (target != null) {
                                if (target.getTeam().equals(gamePlayer.getTeam())) {
                                    target.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * 0.3);
                                }
                            }
                        }
                    }

                }

                if (gamePlayer.xerolEternalHunger) {

<<<<<<< HEAD
                    if (e.getEntity() instanceof Player) {

                        GamePlayer target = GeneralUtils.getGamePlayer(e.getEntity().getUniqueId());
                        if (target != null) {
                            gamePlayer.getChampion().addHealth(target.getChampion().getMaxHealth() * 0.1);
=======
                    for (Entity ent : e.getEntity().getNearbyEntities(8, 8, 8)) {

                        if (!ent.equals(e.getEntity())) {

                            if (ent instanceof Player) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                            final GamePlayer finalGamePlayer = gamePlayer;
                            BukkitTask bt = new BukkitRunnable() {

                                @Override
                                public void run() {

                                    target.getChampion().subtractHealth(target.getChampion().getHealthRegen() * 0.03, plugin, finalGamePlayer, target, false);

                                }

                            }.runTaskTimer(plugin, 0, 19);

<<<<<<< HEAD
                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    bt.cancel();
=======
                        }

                    }

                    if (gamePlayer.xerolEternalHunger) {

                        if (e.getEntity() instanceof Player) {

                            GamePlayer target = GeneralUtils.getGamePlayer(e.getEntity().getUniqueId());
                            if (target != null) {
                                gamePlayer.getChampionType().addHealth(target.getChampionType().getMaxHealth() * 0.05);

                                final GamePlayer finalGamePlayer = gamePlayer;
                                BukkitTask bt = new BukkitRunnable() {

                                    @Override
                                    public void run() {

                                        target.getChampionType().subtractHealth(target.getChampionType().getHealthRegen() * 0.01, plugin, finalGamePlayer, target);

                                    }

                                }.runTaskTimer(plugin, 0, 19);

                                new BukkitRunnable() {

                                    @Override
                                    public void run() {

                                        bt.cancel();

                                    }

                                }.runTaskLater(plugin, 20 * 5);

                            }

                        }

                    }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                                }

                            }.runTaskLater(plugin, 20 * 5);

                        }

                    }

                }

                if ((gamePlayer.basicAttack + 1200) > System.currentTimeMillis()) {

                    e.setCancelled(true);
                    return;

                }

            }


            if (e.getEntityType() == EntityType.ZOMBIE) {

                if (((LeatherArmorMeta) ((Zombie) e.getEntity()).getEquipment().getBoots().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                    ((Zombie) e.getEntity()).damage(gamePlayer.getChampion().getAttackDamage());
                    float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                    gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                    e.getEntity().playEffect(EntityEffect.HURT);

                    if (e.getEntity().isDead()) {

                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                        e.getDamager().sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                        GeneralUtils.levelUp(gamePlayer);

                        if (((CraftZombie) e.getEntity()).getHandle() instanceof BasicMinion) {

                            int gold = Gold.basicMinionKill();
                            gamePlayer.setGold(gamePlayer.getGold() + gold);
                            player.sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                        } else if (((CraftZombie) e.getEntity()).getHandle() instanceof RangedMinion) {

                            int gold = Gold.rangedMeleeMinionKill();
                            gamePlayer.setGold(gamePlayer.getGold() + gold);
                            player.sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                        } else if (((CraftZombie) e.getEntity()).getHandle() instanceof SiegeMinion) {

                            int gold = Gold.siegeMinionKill();
                            gamePlayer.setGold(gamePlayer.getGold() + gold);
                            player.sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                        }

                    }

                    e.setCancelled(true);
                    return;

                } else if (((LeatherArmorMeta) ((Zombie) e.getEntity()).getEquipment().getBoots().getItemMeta()).getColor().asRGB() == Color.RED.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("blue")) {

                    System.out.print("blue attack red");
                    ((Zombie) e.getEntity()).damage(gamePlayer.getChampion().getAttackDamage());
                    float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                    gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                    e.getEntity().playEffect(EntityEffect.HURT);

                    if (e.getEntity().isDead()) {

                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                        e.getDamager().sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                        GeneralUtils.levelUp(gamePlayer);

                        if (((CraftZombie) e.getEntity()).getHandle() instanceof BasicMinion) {

                            int gold = Gold.basicMinionKill();
                            gamePlayer.setGold(gamePlayer.getGold() + gold);
                            player.sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                        } else if (((CraftZombie) e.getEntity()).getHandle() instanceof RangedMinion) {

                            int gold = Gold.rangedMeleeMinionKill();
                            gamePlayer.setGold(gamePlayer.getGold() + gold);
                            player.sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                        } else if (((CraftZombie) e.getEntity()).getHandle() instanceof SiegeMinion) {

                            int gold = Gold.siegeMinionKill();
                            gamePlayer.setGold(gamePlayer.getGold() + gold);
                            player.sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                        }

                    }

                    e.setCancelled(true);
                    return;

                }

                e.setCancelled(true);
                return;

            } else if (e.getEntityType() == EntityType.SLIME) {

                Slime v = (Slime) e.getEntity();
                if (!v.getUniqueId().equals(Nexus.redUUID) && !v.getUniqueId().equals(Nexus.blueUUID)) {
                    if (gamePlayer.getTeam().getName().equalsIgnoreCase("red") && TurretPriorityHandler.canAttack("blue", v.getUniqueId())) {

                        v.damage(gamePlayer.getChampion().getAttackDamage());
                        e.getEntity().playEffect(EntityEffect.HURT);
                        v.getWorld().spawnParticle(Particle.BLOCK_CRACK, v.getLocation().add(0, 1.5, 0), 100, 4, 0, 4, new MaterialData(Material.SANDSTONE));
                        gamePlayer.basicAttack = System.currentTimeMillis();

                        e.setCancelled(true);
                        return;

                    }

                    if (gamePlayer.getTeam().getName().equalsIgnoreCase("blue") && TurretPriorityHandler.canAttack("red", v.getUniqueId())) {

                        v.damage(gamePlayer.getChampion().getAttackDamage());
                        e.getEntity().playEffect(EntityEffect.HURT);
                        v.getWorld().spawnParticle(Particle.BLOCK_CRACK, v.getLocation().add(0, 1.5, 0), 100, 4, 0, 4, new MaterialData(Material.SANDSTONE));
                        gamePlayer.basicAttack = System.currentTimeMillis();

                        e.setCancelled(true);
                        return;

                    }

                } else {

                    if (gamePlayer.getTeam().getName().equalsIgnoreCase("red") && v.getUniqueId().equals(Nexus.blueUUID)) {

                        v.damage(gamePlayer.getChampion().getAttackDamage());
                        v.getWorld().spawnParticle(Particle.BLOCK_CRACK, v.getLocation().add(0, 1.5, 0), 100, 4, 0, 4, new MaterialData(Material.SANDSTONE));
                        gamePlayer.basicAttack = System.currentTimeMillis();
                        e.setCancelled(true);
                        return;

                    } else if (gamePlayer.getTeam().getName().equalsIgnoreCase("blue") && v.getUniqueId().equals(Nexus.redUUID)) {

                        v.damage(gamePlayer.getChampion().getAttackDamage());
                        v.getWorld().spawnParticle(Particle.BLOCK_CRACK, v.getLocation().add(0, 1.5, 0), 100, 4, 0, 4, new MaterialData(Material.SANDSTONE));
                        gamePlayer.basicAttack = System.currentTimeMillis();
                        e.setCancelled(true);
                        return;

                    }

                }

            }

            for (GamePlayer gamePlayer1 : Main.gamePlayers) {

                if (gamePlayer1.getUuid().equals(e.getEntity().getUniqueId())) {

                    otherPlayer = gamePlayer1;
                    break;
                }

<<<<<<< HEAD
            }
=======
                        }
                        otherPlayer.getChampionType().subtractHealth(gamePlayer.getChampionType().getAttackDamage() * otherPlayer.getAsgerUlt(), plugin, gamePlayer, otherPlayer);
                        gamePlayer.basicAttack = System.currentTimeMillis();
                        TextUtils.damageMsg(gamePlayer, otherPlayer);
                        e.getEntity().playEffect(EntityEffect.HURT);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

            if (otherPlayer != null && !gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName())) {

                if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger")) {

<<<<<<< HEAD
                    if (otherPlayer.getSlaughterfestStacks() < 3 && gamePlayer.getLevel() >= 3) {

                        otherPlayer.setSlaughterfestStacks((byte) (otherPlayer.getSlaughterfestStacks() + (byte) 1));

                    }
                    otherPlayer.getChampion().subtractHealth(gamePlayer.getChampion().getAttackDamage() * otherPlayer.getAsgerUlt(), plugin, gamePlayer, otherPlayer, true);
                    gamePlayer.basicAttack = System.currentTimeMillis();
                    TextUtils.damageMsg(gamePlayer, otherPlayer);
                    e.getEntity().playEffect(EntityEffect.HURT);

                } else if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName()) && gamePlayer.getChampion().getName().equalsIgnoreCase("Xiau Tsun") && gamePlayer.getLevel() >= 3) {
=======
                    } else {
                        TextUtils.damageMsg(gamePlayer, otherPlayer);
                        otherPlayer.getChampionType().subtractHealth(gamePlayer.getChampionType().getAttackDamage(), plugin, gamePlayer, otherPlayer);
                        gamePlayer.basicAttack = System.currentTimeMillis();
                        e.getEntity().playEffect(EntityEffect.HURT);
                    }

                } else {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    doRunicDaggers((Player) e.getDamager(), (Player) e.getEntity(), gamePlayer, otherPlayer);
                    gamePlayer.basicAttack = System.currentTimeMillis();
                    e.getEntity().playEffect(EntityEffect.HURT);

                } else {
                    TextUtils.damageMsg(gamePlayer, otherPlayer);
                    otherPlayer.getChampion().subtractHealth(gamePlayer.getChampion().getAttackDamage(), plugin, gamePlayer, otherPlayer, true);
                    gamePlayer.basicAttack = System.currentTimeMillis();
                    e.getEntity().playEffect(EntityEffect.HURT);
                }

            } else {

                e.setCancelled(true);
                return;

            }

            e.setCancelled(true);
            return;

        }

        e.setCancelled(true);

    }

    @EventHandler
    public void playerInteractAtEntity(PlayerInteractAtEntityEvent e) {

        if (e.getRightClicked().getType() == EntityType.VILLAGER) {

            Villager v = (Villager) e.getRightClicked();

            if (v.getProfession() == Villager.Profession.LIBRARIAN) {

                new MerchantGUI(plugin).open(e.getPlayer());
                e.setCancelled(true);

            } else
                Bukkit.getPluginManager().callEvent(new PlayerInteractEvent(e.getPlayer(), Action.RIGHT_CLICK_AIR, e.getPlayer().getInventory().getItemInMainHand(), null, null));

        } else
            Bukkit.getPluginManager().callEvent(new PlayerInteractEvent(e.getPlayer(), Action.RIGHT_CLICK_AIR, e.getPlayer().getInventory().getItemInMainHand(), null, null));

    }

<<<<<<< HEAD
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
=======
   /* @EventHandler
    public void test(PlayerToggleSneakEvent e) {

        GamePlayer gamePlayer = null;

        for (GamePlayer gamePlayer1 : Main.gamePlayers) {

            if (gamePlayer1.getUuid().equals(e.getPlayer().getUniqueId())) {

                gamePlayer = gamePlayer1;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

        if (e.isCancelled())
            e.setCancelled(false);

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Material type = e.getPlayer().getInventory().getItemInMainHand().getType();

            if (GameState.getGameState() != GameState.STARTED) {

<<<<<<< HEAD
                if (type == Material.SKULL_ITEM)
                    new ChampionGUI().open(e.getPlayer());
=======
    }*/
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                return;

            }

            GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getPlayer());

<<<<<<< HEAD
            if (type == Material.WHEAT || type == Material.SULPHUR)
                return;
            else if (type == Material.IRON_BARDING && e.getPlayer().getVehicle() == null) {

                PlayerData pd = Database.getPlayerByUUID(e.getPlayer().getUniqueId());

                if (pd.getActiveMount() != null) {
                    if (pd.getActiveMount() == Mount.DONKEY) {
                        World world = e.getPlayer().getWorld();
                        DonkeyRideable entity = new DonkeyRideable(((CraftWorld) world).getHandle(), plugin);
                        Location loc = e.getPlayer().getLocation();
                        entity.setPosition(loc.getX(), loc.getY(), loc.getZ());
                        ((CraftWorld) world).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
                        entity.setTame(true);
                        entity.setHasChest(false);
                        entity.setType(EnumHorseType.DONKEY);
                        ((Horse) entity.getBukkitEntity()).setVariant(Horse.Variant.DONKEY);
                        ((Horse) entity.getBukkitEntity()).getInventory().setSaddle(new ItemStack(Material.SADDLE));
                        entity.getBukkitEntity().setMetadata("Mount", new FixedMetadataValue(plugin, true));
                        GeneralUtils.cooldown(plugin, gamePlayer, 8, 5);
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                ((Horse) entity.getBukkitEntity()).setPassenger(e.getPlayer());
                            }
                        }, 5);


                    } else {
                        World world = e.getPlayer().getWorld();
                        RideableEntity entity = new RideableEntity(((CraftWorld) world).getHandle(), plugin);
                        Location loc = e.getPlayer().getLocation();
                        entity.setPosition(loc.getX(), loc.getY(), loc.getZ());
                        ((CraftWorld) world).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

                        ((ArmorStand) entity.getBukkitEntity()).setVisible(false);
                        ((ArmorStand) entity.getBukkitEntity()).setHelmet(new ItemStack(Material.valueOf(pd.getActiveMount().getMaterial()), 1, (short) pd.getActiveMount().getBy()));
                        entity.getBukkitEntity().setPassenger(e.getPlayer());
                        ((ArmorStand) entity.getBukkitEntity()).setSmall(true);
                        entity.getBukkitEntity().setMetadata("Mount", new FixedMetadataValue(plugin, true));
                        GeneralUtils.cooldown(plugin, gamePlayer, 8, 5);
                    }

                }

                return;

            }
=======
            GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getPlayer());
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

            PlayerData pd = Database.getPlayerByUUID(e.getPlayer().getUniqueId());
            ItemStack cosmetic = null;

<<<<<<< HEAD
            String name = gamePlayer.getChampion().getName();
            if (e.getPlayer().getInventory().getHeldItemSlot() == 0 && (name.equalsIgnoreCase("Kaine") && gamePlayer.basicAttack == 0 || name.equalsIgnoreCase("Kaine") && gamePlayer.basicAttack + 1150 <= System.currentTimeMillis() || name.equalsIgnoreCase("Corelia") && gamePlayer.basicAttack == 0 || name.equalsIgnoreCase("Corelia") && gamePlayer.basicAttack + 900 <= System.currentTimeMillis())) {
=======
            if (gamePlayer != null && (type == Material.DIAMOND_HOE && gamePlayer.basicAttack == 0 || type == Material.DIAMOND_HOE && (gamePlayer.basicAttack + 1150) <= System.currentTimeMillis() || type == Material.WOOD_HOE && gamePlayer.basicAttack == 0 || type == Material.WOOD_HOE && (gamePlayer.basicAttack + 900) <= System.currentTimeMillis())) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                Entity entity = null;

                if (gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia"))
                    entity = getNearestEntityInSight(gamePlayer, e.getPlayer(), 8);
                else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine"))
                    entity = getNearestEntityInSight(gamePlayer, e.getPlayer(), 7);

                if (entity == null)
                    return;

                if (entity instanceof Player && ((Player) entity).getGameMode() == GameMode.SPECTATOR)
                    return;

                LineEffect lineEffect = null;
                Arrow a;

                if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine")) {
                    lineEffect = new LineEffect(effectManager);
                    lineEffect.particle = ParticleEffect.CRIT_MAGIC;
                    lineEffect.speed = 0.2F;
                }

                Vector playerLookDirection = e.getPlayer().getLocation().getDirection();

                //create a vector perpendicular to the look direction so that it updates position as the yaw changes
                Vector perpVector = new Vector(-playerLookDirection.getZ(), playerLookDirection.getY(), playerLookDirection.getX()).normalize();

                //use this vector to place the particle beam to the right of the player
                double x = e.getPlayer().getLocation().getX() + (perpVector.getX() * .75);//The .75 just places it .75-ish blocks away
                double y = e.getPlayer().getLocation().getY() + 1.75D;
                double z = e.getPlayer().getLocation().getZ() + (perpVector.getZ() * .75);

                Vector from = null;
                Vector to;
                Vector direction = null;

                if (lineEffect != null) {
                    lineEffect.setDynamicOrigin(new DynamicLocation(new Location(entity.getWorld(), x, y, z)));
                    lineEffect.setDynamicTarget(new DynamicLocation(entity));
                } else {
                    from = new Location(entity.getWorld(), x, y, z).toVector();
                    to = entity.getLocation().toVector().add(new Vector(0, 1.5, 0));
                    direction = to.subtract(from).normalize().multiply(2);
                }
                //gamePlayer.lastAttack = System.currentTimeMillis();

                if (entity instanceof Player && ((Player) entity).getGameMode() != GameMode.SPECTATOR) {

                    for (GamePlayer otherPlayer : Main.gamePlayers) {

                        if (otherPlayer.getUuid().equals(entity.getUniqueId())) {

                            gamePlayer.basicAttack = System.currentTimeMillis();
<<<<<<< HEAD
                            otherPlayer.getChampion().subtractHealth(gamePlayer.getChampion().getAttackDamage(), plugin, gamePlayer, otherPlayer, true);
=======
                            otherPlayer.getChampionType().subtractHealth(gamePlayer.getChampionType().getAttackDamage(), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                            if (lineEffect != null)
                                lineEffect.start();
                            else {
                                a = e.getPlayer().launchProjectile(Arrow.class);
                                a.setFireTicks(50);
                                a.setShooter(e.getPlayer());
                            }
                            entity.playEffect(EntityEffect.HURT);
                            if (!gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia"))
                                TextUtils.damageMsg(gamePlayer, otherPlayer);
                            break;

                        }

                    }

                } else if (entity.getType() == EntityType.ZOMBIE) {

                    if (((LeatherArmorMeta) ((Zombie) entity).getEquipment().getBoots().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                        if (lineEffect != null)
                            lineEffect.start();
                        else {
                            a = e.getPlayer().launchProjectile(Arrow.class);
                            a.setFireTicks(50);
                            a.setShooter(e.getPlayer());
                        }
                        ((Zombie) entity).damage(gamePlayer.getChampion().getAttackDamage());
                        float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                        gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                        entity.playEffect(EntityEffect.HURT);
                        gamePlayer.basicAttack = System.currentTimeMillis();

                        if (entity.isDead()) {

                            gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                            Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                            GeneralUtils.levelUp(gamePlayer);

                            if (((CraftZombie) entity).getHandle() instanceof BasicMinion) {

                                int gold = Gold.basicMinionKill();
                                gamePlayer.setGold(gamePlayer.getGold() + gold);
                                e.getPlayer().sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                            } else if (((CraftZombie) entity).getHandle() instanceof RangedMinion) {

                                int gold = Gold.rangedMeleeMinionKill();
                                gamePlayer.setGold(gamePlayer.getGold() + gold);
                                e.getPlayer().sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                            } else if (((CraftZombie) entity).getHandle() instanceof SiegeMinion) {

                                int gold = Gold.siegeMinionKill();
                                gamePlayer.setGold(gamePlayer.getGold() + gold);
                                e.getPlayer().sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                            }

                        }

                        return;

                    } else if (((LeatherArmorMeta) ((Zombie) entity).getEquipment().getBoots().getItemMeta()).getColor().asRGB() == Color.RED.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("blue")) {

                        if (lineEffect != null)
                            lineEffect.start();
                        else {
                            a = e.getPlayer().launchProjectile(Arrow.class);
                            a.setFireTicks(50);
                            a.setShooter(e.getPlayer());
                        }
                        ((Zombie) entity).damage(gamePlayer.getChampion().getAttackDamage());
                        float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                        gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                        entity.playEffect(EntityEffect.HURT);
                        gamePlayer.basicAttack = System.currentTimeMillis();

                        if (entity.isDead()) {

                            gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                            Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                            GeneralUtils.levelUp(gamePlayer);

                            if (((CraftZombie) entity).getHandle() instanceof BasicMinion) {

                                int gold = Gold.basicMinionKill();
                                gamePlayer.setGold(gamePlayer.getGold() + gold);
                                e.getPlayer().sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                            } else if (((CraftZombie) entity).getHandle() instanceof RangedMinion) {

                                int gold = Gold.rangedMeleeMinionKill();
                                gamePlayer.setGold(gamePlayer.getGold() + gold);
                                e.getPlayer().sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                            } else if (((CraftZombie) entity).getHandle() instanceof SiegeMinion) {

                                int gold = Gold.siegeMinionKill();
                                gamePlayer.setGold(gamePlayer.getGold() + gold);
                                e.getPlayer().sendMessage(TextUtils.formatText("&7[&e+" + gold + " Gold&7]"));

                            }

                        }

                        return;

                    }

                    return;

                } else if (entity.getType() == EntityType.SLIME) {

                    //System.out.print("PASSED");
                    Slime v = (Slime) entity;
                    if (!v.getUniqueId().equals(Nexus.redUUID) && !v.getUniqueId().equals(Nexus.blueUUID)) {

                        //System.out.println(UUID.fromString(uuidTeam.split(",")[1]) + "Slime: " + (v.getUniqueId()));

                        if (gamePlayer.getTeam().getName().equalsIgnoreCase("red") && TurretPriorityHandler.canAttack("blue", v.getUniqueId())) {

                            if (lineEffect != null)
                                lineEffect.start();
                            else {
                                a = e.getPlayer().launchProjectile(Arrow.class);
                                a.setFireTicks(50);
                                a.setShooter(e.getPlayer());
                            }
                            gamePlayer.basicAttack = System.currentTimeMillis();
                            float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                            gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                            v.damage(gamePlayer.getChampion().getAttackDamage());
                            v.getWorld().spawnParticle(Particle.BLOCK_CRACK, v.getLocation().add(0, 1.5, 0), 100, 4, 0, 4, new MaterialData(Material.SANDSTONE));
                            v.setInvulnerable(false);

                            return;

                        }

                        //System.out.println(UUID.fromString(uuidTeam.split(",")[1]) + "Slime: " + (v.getUniqueId()));

                        if (gamePlayer.getTeam().getName().equalsIgnoreCase("blue") && TurretPriorityHandler.canAttack("red", v.getUniqueId())) {

                            if (lineEffect != null)
                                lineEffect.start();
                            else {
                                a = e.getPlayer().launchProjectile(Arrow.class);
                                a.setFireTicks(50);
                                a.setShooter(e.getPlayer());
                            }
                            gamePlayer.basicAttack = System.currentTimeMillis();
                            float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                            gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                            v.damage(gamePlayer.getChampion().getAttackDamage());
                            v.setInvulnerable(false);

                            return;

                        }

                    } else {

                        if (gamePlayer.getTeam().getName().equalsIgnoreCase("red") && v.getUniqueId().equals(Nexus.blueUUID)) {

                            if (lineEffect != null)
                                lineEffect.start();
                            else {
                                //a = e.getPlayer().launchProjectile(Arrow.class);
                                a = e.getPlayer().launchProjectile(Arrow.class);
                                a.setFireTicks(50);
                                a.setShooter(e.getPlayer());
                            }
                            gamePlayer.basicAttack = System.currentTimeMillis();
                            float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                            gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                            v.damage(gamePlayer.getChampion().getAttackDamage());
                            return;
                        } else if (gamePlayer.getTeam().getName().equalsIgnoreCase("blue") && v.getUniqueId().equals(Nexus.redUUID)) {

                            if (lineEffect != null)
                                lineEffect.start();
                            else {
                                a = e.getPlayer().launchProjectile(Arrow.class);
                                //a = e.getPlayer().launchProjectile(Arrow.class);
                                a.setFireTicks(50);
                                a.setShooter(e.getPlayer());
                            }
                            gamePlayer.basicAttack = System.currentTimeMillis();
                            float lifeSteal = (gamePlayer.getChampion().getLifeSteal() / 100F);
                            gamePlayer.getChampion().addHealth(gamePlayer.getChampion().getAttackDamage() * lifeSteal);
                            v.damage(gamePlayer.getChampion().getAttackDamage());
                            v.getWorld().spawnParticle(Particle.BLOCK_CRACK, v.getLocation().add(0, 1.5, 0), 100, 4, 0, 4, new MaterialData(Material.SANDSTONE));
                            return;

                        }

                    }

                }

                return;

            }

<<<<<<< HEAD
            /**
             * Asger
             */
            if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger") && type == Material.CLAY_BRICK) {

                new AsgerAbilities(effectManager, plugin).insanityAura(e.getPlayer(), gamePlayer);

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger") && type == Material.FLINT) {

                new AsgerAbilities(effectManager, plugin).slaughterfest(e.getPlayer(), gamePlayer);

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger") && type == Material.FEATHER) {

                new AsgerAbilities(effectManager, plugin).bloodspikes(e.getPlayer(), gamePlayer);

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger") && type == Material.SUGAR_CANE) {

                new AsgerAbilities(effectManager, plugin).limitlessAnger(e.getPlayer(), gamePlayer);

            }

            /**
             * Xiau Tsun
             */
            else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xiau Tsun") && type == Material.CLAY_BRICK) {
=======
            if (gamePlayer != null) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                /**
                 * Asger
                 */
                if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Asger") && type == Material.CLAY_BRICK) {

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xiau Tsun") && type == Material.FEATHER) {
=======
                    new AsgerAbilities(effectManager, plugin).insanityAura(e.getPlayer(), gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Asger") && type == Material.FLINT) {

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xiau Tsun") && type == Material.SUGAR_CANE) {
=======
                    new AsgerAbilities(effectManager, plugin).slaughterfest(e.getPlayer(), gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Asger") && type == Material.FEATHER) {

                    new AsgerAbilities(effectManager, plugin).bloodspikes(e.getPlayer(), gamePlayer);

<<<<<<< HEAD
            /**
             * Xerol
             */
            else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xerol") && type == Material.CLAY_BRICK) {
=======
                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Asger") && type == Material.SUGAR_CANE) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    new AsgerAbilities(effectManager, plugin).limitlessAnger(e.getPlayer(), gamePlayer);

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xerol") && type == Material.FLINT) {
=======
                }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                /**
                 * Xiau Tsun
                 */
                else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xiau Tsun") && type == Material.CLAY_BRICK) {

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xerol") && type == Material.FEATHER) {
=======
                    new XiauTsunAbilities(effectManager, plugin).assassination(e.getPlayer(), gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xiau Tsun") && type == Material.FEATHER) {

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xerol") && type == Material.SUGAR_CANE) {
=======
                    new XiauTsunAbilities(effectManager, plugin).ambush(e.getPlayer(), gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xiau Tsun") && type == Material.SUGAR_CANE) {

                    new XiauTsunAbilities(effectManager, plugin).finalCalling(e.getPlayer(), gamePlayer);

<<<<<<< HEAD
                    if (ent instanceof Player) {

                        target = GeneralUtils.getGamePlayer(((Player) ent));

                        if (target != null) {

                            if (target != gamePlayer) {

                                if (target.getTeam().equals(gamePlayer.getTeam())) {
=======
                }

                /**
                 * Xerol
                 */
                else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xerol") && type == Material.CLAY_BRICK) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    new XerolAbilities(effectManager, plugin).spawnGrave(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xerol") && type == Material.FLINT) {

<<<<<<< HEAD
                            }

                        }

                    }
=======
                    new XerolAbilities(effectManager, plugin).eternalHunger(e.getPlayer(), gamePlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xerol") && type == Material.FEATHER) {

                    new XerolAbilities(effectManager, plugin).undeadRising(e.getPlayer(), gamePlayer);

<<<<<<< HEAD
            /**
             * Kaine
             */
            else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine") && type == Material.CLAY_BRICK) {
=======
                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Xerol") && type == Material.SUGAR_CANE) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    GamePlayer target;

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine") && type == Material.FLINT) {
=======
                    for (Entity ent : e.getPlayer().getWorld().getNearbyEntities(e.getPlayer().getLocation(), 5, 5, 5)) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                        if (ent instanceof Player) {

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine") && type == Material.FEATHER) {
=======
                            target = GeneralUtils.getGamePlayer(((Player) ent));
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                            if (target != null) {

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine") && type == Material.SUGAR_CANE) {
=======
                                if (target != gamePlayer) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                                    if (target.getTeam().equals(gamePlayer.getTeam())) {

                                        new XerolAbilities(effectManager, plugin).resurrection(gamePlayer, target);

<<<<<<< HEAD
            /**
             * Corelia
             */
            else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia") && type == Material.CLAY_BRICK) {
=======
                                    }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                                }

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia") && type == Material.FLINT) {
=======
                            }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                        }

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia") && type == Material.FEATHER) {
=======
                    }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                }

<<<<<<< HEAD
            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia") && type == Material.SUGAR_CANE) {
=======
                /**
                 * Kaine
                 */
                else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Kaine") && type == Material.CLAY_BRICK) {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                    new KaineAbilities(effectManager, plugin).electroShock(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Kaine") && type == Material.FLINT) {

                    new KaineAbilities(effectManager, plugin).devastatingSparks(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Kaine") && type == Material.FEATHER) {

<<<<<<< HEAD
=======
                    new KaineAbilities(effectManager, plugin).rainDance(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Kaine") && type == Material.SUGAR_CANE) {

                    new KaineAbilities(effectManager, plugin).staticField(e.getPlayer(), gamePlayer);

                }

                /**
                 * Corelia
                 */
                else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Corelia") && type == Material.CLAY_BRICK) {

                    new CoreliaAbilities(effectManager, plugin).nukeEm(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Corelia") && type == Material.FLINT) {

                    new CoreliaAbilities(effectManager, plugin).blastZone(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Corelia") && type == Material.FEATHER) {

                    new CoreliaAbilities(effectManager, plugin).stupefy(e.getPlayer(), gamePlayer);

                } else if (gamePlayer.getChampionType().getName().equalsIgnoreCase("Corelia") && type == Material.SUGAR_CANE) {

                    new CoreliaAbilities(effectManager, plugin).kaboom(e.getPlayer(), gamePlayer);

                }
            }

        }

    }

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
}
