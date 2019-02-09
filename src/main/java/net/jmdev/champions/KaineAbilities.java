package net.jmdev.champions;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.DynamicLocation;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.ParticlesUtil;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class KaineAbilities {

    private EffectManager effectManager;
    private Main plugin;

    public KaineAbilities(EffectManager effectManager, Main plugin) {

        this.effectManager = effectManager;
        this.plugin = plugin;

    }

    public void electroShock(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 55) {

            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;

        }

        WarpEffect warpEffect = new WarpEffect(effectManager);
        warpEffect.rings = 2;
        warpEffect.setDynamicOrigin(new DynamicLocation(player));
        warpEffect.grow = 5 * 20;
        warpEffect.duration = 5 * 20;
        warpEffect.iterations = 5 * 20;
        warpEffect.start();

        ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
        ExplodeEffect explodeEffect1 = new ExplodeEffect(effectManager);
        explodeEffect.particleCount = 200;
        explodeEffect.speed = 0.2F;
        explodeEffect.disappearWithOriginEntity = true;
        explodeEffect1.particleCount = 200;
        explodeEffect1.speed = 0.2F;
        explodeEffect1.disappearWithOriginEntity = true;

        for (Entity e : player.getNearbyEntities(5, 2, 5)) {

            if (e instanceof Player) {

                for (GamePlayer otherPlayer : Main.gamePlayers) {

                    if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName())) {

<<<<<<< HEAD
                        otherPlayer.getChampion().subtractHealth(30 + (gamePlayer.getChampion().getAbilityPower() * 0.5), plugin, gamePlayer, otherPlayer, false);
=======
                        otherPlayer.getChampionType().subtractHealth(35 + (gamePlayer.getChampionType().getAbilityPower() * 0.5), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                        TextUtils.damageMsg(gamePlayer, otherPlayer);
                        explodeEffect.setDynamicOrigin(new DynamicLocation(e));
                        explodeEffect.start();
                        e.playEffect(EntityEffect.HURT);

                        for (Entity entity : Bukkit.getPlayer(otherPlayer.getUuid()).getNearbyEntities(5, 2, 5)) {

                            for (GamePlayer otherPlayer1 : Main.gamePlayers) {

                                if (entity instanceof Player && entity.getUniqueId().equals(otherPlayer1.getUuid()) && entity.getUniqueId() != otherPlayer1.getUuid()) {

                                    if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer1.getTeam().getName())) {

                                        entity.playEffect(EntityEffect.HURT);
                                        explodeEffect1.setDynamicOrigin(new DynamicLocation(entity));
                                        explodeEffect1.start();
<<<<<<< HEAD
                                        otherPlayer.getChampion().subtractHealth(35 + (gamePlayer.getChampion().getAttackDamage() * 0.5), plugin, gamePlayer, otherPlayer, false);
=======
                                        otherPlayer.getChampionType().subtractHealth(35 + (gamePlayer.getChampionType().getAttackDamage() * 0.5), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                                        TextUtils.damageMsg(gamePlayer, otherPlayer);
                                        break;

                                    }

                                }

                            }

                        }

                        break;

                    }

                }

            }

        }

<<<<<<< HEAD
        gamePlayer.getChampion().subtractMana(55);
        GeneralUtils.cooldown(plugin, gamePlayer, 2, 8);
=======
        gamePlayer.getChampionType().subtractMana(55);
        GeneralUtils.cooldown(plugin, gamePlayer, 2, 14);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }

    public void devastatingSparks(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 35) {
            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;
        }

        for (Entity entity : player.getNearbyEntities(5, 1, 5)) {
            if (entity instanceof Player) {
                for (GamePlayer otherPlayer : Main.gamePlayers) {
                    if (otherPlayer.getUuid().equals(entity.getUniqueId())) {
                        if (!gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName())) {
                            World world = Bukkit.getPlayer(otherPlayer.getUuid()).getWorld();

                            world.strikeLightning(entity.getLocation());
                            ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 2, false, true));

<<<<<<< HEAD
                            otherPlayer.getChampion().subtractHealth(20 + gamePlayer.getChampion().getAbilityPower()*0.4, plugin, gamePlayer, otherPlayer, false);
                            TextUtils.damageMsg(gamePlayer, otherPlayer);
                            gamePlayer.getChampion().subtractMana(35);

                            GeneralUtils.cooldown(plugin, gamePlayer, 3, 4);
=======
                            otherPlayer.getChampionType().subtractHealth(50, plugin, gamePlayer, otherPlayer);
                            TextUtils.damageMsg(gamePlayer, otherPlayer);
                            gamePlayer.getChampionType().subtractMana(80);

                            GeneralUtils.cooldown(plugin, gamePlayer, 3, 15);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                        }
                    }
                }
            }
        }
    }

    public void rainDance(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 75) {
            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;
        }

        ParticlesUtil.swirlEffect(plugin, player, 5, 5, 10, 40);

        final boolean[] hasUsed = {false};

        for (int i = 0; i < 5; i++) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    for (Entity entity : player.getNearbyEntities(5, 5, 5)) {

                        if (entity instanceof Player && entity.getLocation().distanceSquared(player.getLocation()) <= 25) {

                            GamePlayer otherPlayer = GeneralUtils.getGamePlayer(entity.getUniqueId());

                            if (otherPlayer != null && otherPlayer.getUuid().equals(entity.getUniqueId())) {

                                if (!(gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName()))) {

<<<<<<< HEAD
                                    otherPlayer.getChampion().subtractHealth(10, plugin, gamePlayer, otherPlayer, false);
                                    TextUtils.damageMsg(gamePlayer, otherPlayer);
=======
                                        hasUsed[0] = true;
                                        otherPlayer.getChampionType().subtractHealth(15, plugin, gamePlayer, otherPlayer);
                                        TextUtils.damageMsg(gamePlayer, otherPlayer);

                                    } else {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

                                } else {

                                    otherPlayer.getChampion().addHealth(25);

                                }

                            }


                        }

                    }
                }

            }.runTaskLater(plugin, i * 20);
        }

<<<<<<< HEAD
            gamePlayer.getChampion().subtractMana(75);

            GeneralUtils.cooldown(plugin, gamePlayer, 4, 15);
=======
        if (hasUsed[0]) {
            gamePlayer.getChampionType().subtractMana(75);

            GeneralUtils.cooldown(plugin, gamePlayer, 4, 15);

        }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }


    public void staticField(Player player, GamePlayer gamePlayer) {

        if (gamePlayer.getChampion().getMana() < 125) {
            player.sendMessage(TextUtils.formatText("&cYou do not have enough mana to use this ability!"));
            return;
        }

        for (int i = 0; i < 5; i++) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    player.getWorld().strikeLightning(player.getLocation().add(new Vector(4, 0, 0)));
                    player.getWorld().strikeLightning(player.getLocation().add(new Vector(-4, 0, 0)));
                    player.getWorld().strikeLightning(player.getLocation().add(new Vector(0, 0, 4)));
                    player.getWorld().strikeLightning(player.getLocation().add(new Vector(0, 0, -4)));

                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 1, 2, false, true);

                    for (Entity e : player.getNearbyEntities(4, 2, 4)) {

                        if (e instanceof Player) {

                            for (GamePlayer otherPlayer : Main.gamePlayers) {

                                if (otherPlayer.getUuid().equals(e.getUniqueId()) && !gamePlayer.getTeam().getName().equalsIgnoreCase(otherPlayer.getTeam().getName())) {

                                    Bukkit.getPlayer(otherPlayer.getUuid()).addPotionEffect(potionEffect);
<<<<<<< HEAD
                                    otherPlayer.getChampion().subtractHealth(30, plugin, gamePlayer, otherPlayer, false);
=======
                                    otherPlayer.getChampionType().subtractHealth(30, plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                                    TextUtils.damageMsg(gamePlayer, otherPlayer);

                                }

                            }

                        }

                    }

                }

            }.runTaskLater(plugin, 20 * i);
        }

<<<<<<< HEAD
        gamePlayer.getChampion().subtractMana(125);
=======
        gamePlayer.getChampionType().subtractMana(125);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

        GeneralUtils.cooldown(plugin, gamePlayer, 5, 72);

    }
}
