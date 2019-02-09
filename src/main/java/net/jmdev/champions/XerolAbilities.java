package net.jmdev.champions;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.util.GeneralUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/**
 * Created by kedri on 8/4/2017.
 */
public class XerolAbilities {

    private EffectManager effectManager;
    private Main plugin;

    public XerolAbilities(EffectManager effectManager, Main plugin) {
        this.effectManager = effectManager;
        this.plugin = plugin;
    }


    public void spawnGrave(Player player, GamePlayer gamePlayer) {
        if (gamePlayer.getChampion().getMana() - 30 >= 0) {
            gamePlayer.getChampion().subtractMana(30);
            Location grave = player.getLocation().add(player.getLocation().getDirection().setY(0).normalize().multiply(3));
            createGrave(gamePlayer, grave);
            GeneralUtils.cooldown(plugin, gamePlayer, 2, 9);
        }
    }

    public void eternalHunger(Player player, GamePlayer gamePlayer) {
        //need an event
        if (!gamePlayer.xerolEternalHunger) {
            if (gamePlayer.getChampion().getMana() - 50 >= 0) {
                gamePlayer.getChampion().subtractMana(50);
                gamePlayer.xerolEternalHunger = true;
                GeneralUtils.cooldown(plugin, gamePlayer, 3, 7);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        gamePlayer.xerolEternalHunger = false;
                    }
                },20*5);
            }
        }
    }

    public void createGrave(GamePlayer gamePlayer, Location grave) {
        int y = Bukkit.getPlayer(gamePlayer.getUuid()).getLocation().getBlockY();
        grave.setY(y);
        while (grave.getBlock().getType() == Material.AIR) {
            grave.subtract(0, 1, 0);
        }
        while(grave.getBlock().getType().isSolid() || grave.getBlock().getType().isOccluding()){
            grave.add(0, 1, 0);
        }

        grave.getBlock().setType(Material.COBBLESTONE);
        grave.clone().add(0, 1, 0).getBlock().setType(Material.COBBLESTONE);
        final boolean[] canHeal = {true};

        BukkitTask bt = new BukkitRunnable() {

            @Override
            public void run() {

                for (Entity ent : grave.getWorld().getNearbyEntities(grave, 1.5, 15, 1.5)) {
                    if (ent instanceof Player) {
                        if(!canHeal[0]) break;
                        GamePlayer gp = GeneralUtils.getGamePlayer(ent.getUniqueId());
                        if (gp != null) {
                            if (gp.getTeam().equals(gamePlayer.getTeam())) {
                                if (gp.getChampion().getHealth() < gp.getChampion().getMaxHealth()) {
                                    gp.getChampion().addHealth(15);
                                    ArcEffect ce = new ArcEffect(effectManager);
                                    ce.setDynamicOrigin(new DynamicLocation(grave.clone().add(0,2,0)));
                                    ce.setDynamicTarget(new DynamicLocation(ent));
                                    ce.particle = ParticleEffect.BLOCK_DUST;
                                    ce.material = Material.CACTUS;
                                    ce.duration = 500;

                                    ce.start();
                                    this.cancel();
                                    grave.getBlock().setType(Material.AIR);
                                    grave.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
                                    canHeal[0] = false;
                                    CloudEffect ce2 = new CloudEffect(effectManager);
                                    ce2.setDynamicOrigin(new DynamicLocation(grave));
                                    ce2.iterations = 1;
                                    ce2.period = 1;
                                    ce2.cloudParticle = ParticleEffect.BLOCK_DUST;
                                    ce2.mainParticle = ParticleEffect.BLOCK_DUST;
                                    ce2.material = Material.COBBLESTONE;
                                    ce2.cloudSize = 1.2f;
                                    ce2.start();
                                    break;
                                }

                            }

                        }

                    }

                }

            }

        }.runTaskTimer(plugin, 0, 5);
        Bukkit.getScheduler().runTaskLater(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                bt.cancel();
                if (grave.getBlock().getType() == Material.COBBLESTONE) {
                    grave.getBlock().setType(Material.AIR);
                    grave.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
                    CloudEffect ce = new CloudEffect(effectManager);
                    ce.setDynamicOrigin(new DynamicLocation(grave));
                    ce.iterations = 1;
                    ce.period = 1;
                    ce.cloudParticle = ParticleEffect.BLOCK_DUST;
                    ce.mainParticle = ParticleEffect.BLOCK_DUST;
                    ce.material = Material.COBBLESTONE;
                    ce.cloudSize = 1.2f;
                    ce.start();
                }
            }
        }, 20 * 5);


    }

    public void undeadRising(Player player, GamePlayer gamePlayer) {
        if (gamePlayer.getChampion().getMana() - 90 >= 0) {
            gamePlayer.getChampion().subtractMana(90);
            LineEffect le = new LineEffect(effectManager);
            le.setDynamicOrigin(new DynamicLocation(player));
            WitherSkull ws = player.launchProjectile(WitherSkull.class);
            ws.setMetadata("XerolUndeadRising", new FixedMetadataValue(plugin, gamePlayer));
            ws.setMetadata("noblockdamage", new FixedMetadataValue(plugin, null));
            le.setDynamicTarget(new DynamicLocation(ws));
            le.particle = ParticleEffect.SMOKE_NORMAL;
            le.iterations = -1;
            le.particles = 15;
<<<<<<< HEAD
            GeneralUtils.cooldown(plugin, gamePlayer, 4, 13);
=======
            GeneralUtils.cooldown(plugin, gamePlayer, 4, 16);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
            le.start();
            new BukkitRunnable(){
                @Override
                public void run() {

                    if (ws.isDead()){
                        this.cancel();
                        ws.remove();
                        le.cancel();
                        return;
                    }
                    double direction = ws.getLocation().distance(player.getLocation());
                    if (direction >= 5) {
                        this.cancel();
                        ws.remove();
                        le.cancel();
                    }

                }
            }.runTaskTimer(plugin, 0, 20);
        }
    }

    public void resurrection(GamePlayer gamePlayer, GamePlayer target) {
        if (gamePlayer.getChampion().getMana() - 150 >= 0) {
            gamePlayer.getChampion().subtractMana(150);
            target.resurrection = true;

            Player player = Bukkit.getPlayer(target.getUuid());
            CloudEffect ce = new CloudEffect(effectManager);
            DynamicLocation dl = new DynamicLocation(player);
            dl.addOffset(new Vector(0, -2, 0));
            ce.setDynamicOrigin(dl);
            ce.setDynamicTarget(dl);
            ce.cloudParticle = ParticleEffect.REDSTONE;
            ce.mainParticle = ParticleEffect.REDSTONE;
            ce.color = Color.GREEN;
            ce.cloudColor = Color.GRAY;
            ce.start();
            Bukkit.getScheduler().runTaskLater(plugin, () -> target.resurrection = false, 20 * 16);
            GeneralUtils.cooldown(plugin, gamePlayer, 5, 90);
        }
    }

}
