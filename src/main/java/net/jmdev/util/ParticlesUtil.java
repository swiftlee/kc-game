package net.jmdev.util;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/03/2017 | 00:22
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
public class ParticlesUtil {

    private static void setValue(PacketPlayOutWorldParticles instance, String fieldName, java.io.Serializable value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    private static float getColor(float value) {
        if (value <= 0.0F) {
            value = -1.0F;
        }
        return value / 255.0F;
    }

    public static void broadCastColoredParticle(Color color, Location loc) {
<<<<<<< HEAD

=======
        
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
        playParticle(EnumParticle.REDSTONE, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), getColor(color.getRed()), getColor(color.getGreen()), getColor(color.getBlue()), 1, 0);

    }

    //this is for corelia's ability, so there is some damage code in this method
    public static void doRadialWaveEffect(Player player, GamePlayer gamePlayer, Main plugin) {

        Location loc = player.getLocation();

        new BukkitRunnable() {

            double t = Math.PI / 4; //just so it doesn't start at the center point of the player

            @Override
            public void run() {

                t += 0.1 * Math.PI;
                for (double theta = 0; theta <= (Math.PI * 2); theta += (Math.PI / 32)) {

                    double x = t * Math.cos(theta); //coordinate for the x plane
                    double y = 1.2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5; //basically (1 / e^(0.1*t)) * sin(t) + 1.5 -> amplitude * exponential function that exponentially decreases * oscillator + constant
                    double z = t * Math.sin(theta); //coordinate for the z plane
                    loc.add(x, y, z);

                    if (t > 2 && t < 6.9)
                        playParticle(EnumParticle.FLAME, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                    else if (t >= 6.9)
                        playParticle(EnumParticle.CLOUD, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                    else
                        playParticle(EnumParticle.SMOKE_LARGE, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);


                    loc.subtract(x, y, z);

                    theta += Math.PI / 64; //increase angle by half of current iteration to put lava particle in between

                    x = t * Math.cos(theta); //coordinate for the x plane
                    y = 1.2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5; //basically (1 / e^(0.1*t)) * sin(t) + 1.5 -> amplitude * exponential function that exponentially decreases * oscillator + constant
                    z = t * Math.sin(theta); //coordinate for the z plane

                    loc.add(x, y, z);

                    playParticle(EnumParticle.SMOKE_NORMAL, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);

                    loc.subtract(x, y, z);

                }

                if (t > 7)
                    cancel();

            }

        }.runTaskTimer(plugin, 1L, 1L);

        PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 20 * 4, 1, false, true);

        for (Entity entity : loc.getWorld().getNearbyEntities(loc, 7, 7, 7)) {

            if (!gamePlayer.getTeam().contains(entity.getUniqueId()) && entity.getLocation().distanceSquared(loc) <= 49) {

                for (GamePlayer otherPlayer : Main.gamePlayers) {

                    if (otherPlayer.getUuid().equals(entity.getUniqueId())) {

                        ((Player) entity).addPotionEffect(slow);
                        entity.playEffect(EntityEffect.HURT);
<<<<<<< HEAD
=======
                        otherPlayer.getChampionType().subtractHealth(15, plugin, gamePlayer, otherPlayer);

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                    }

                }

            } else if (entity.getType() == EntityType.ZOMBIE) {

                Zombie zombie = (Zombie) entity;
                if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.BLUE.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                    zombie.setVelocity(zombie.getLocation().getDirection().multiply(-1).add(new Vector(0, 0.5, 0)));

                    if (zombie.isDead()) {
                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                        player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                        GeneralUtils.levelUp(gamePlayer);
                    }

                } else if (((LeatherArmorMeta) zombie.getEquipment().getChestplate().getItemMeta()).getColor().asRGB() == Color.RED.asRGB() && gamePlayer.getTeam().getName().equalsIgnoreCase("blue")) {


                    if (zombie.isDead()) {
                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 30);
                        player.sendMessage(TextUtils.formatText("&6&l+30 EXP"));
                        GeneralUtils.levelUp(gamePlayer);
                    }

                }

            }

        }

    }

    public static void doHeatSeekerEffect(Player player, Player target, int particleCount, Main plugin) {

        new BukkitRunnable() {

            GamePlayer gamePlayer = GeneralUtils.getGamePlayer(player.getUniqueId());
            GamePlayer otherPlayer = GeneralUtils.getGamePlayer(target.getUniqueId());
            int t = 0;
            int height = 10;
            Vector from = player.getLocation().add(0, 2, 0).toVector();

            @Override
            public void run() {

                if (target.getGameMode() == GameMode.SPECTATOR || otherPlayer == null || gamePlayer == null)
                    cancel();

                t += 1;

                Vector targetVector = target.getLocation().add(0, 2, 0).toVector();
                Vector to = targetVector.subtract(from);

                float length = (float) to.length(); //length of the current vector
                float pitch = (float) (4 * height / Math.pow(length, 2)); //this determines the width modifier
<<<<<<< HEAD
                Vector v = to.clone().normalize().multiply(length * t / particleCount); //basically making the 'to' vector normal and on a local scale (this is proportional due to 't' eventually being particleCount)
                float x = ((float) t / particleCount) * length - length / 2; //x coordinate
=======
                Vector v = to.clone().normalize().multiply(length * t / 100); //basically making the 'to' vector normal and on a local scale (this is proportional due to 't' eventually being 100)
                float x = ((float) t / 100) * length - length / 2; //x coordinate
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                float y = (float) (-pitch * Math.pow(x, 2) + height); //y coordinate

                Location location = new Location(player.getWorld(), from.getX(), from.getY(), from.getZ()).add(v).add(0, y, 0);

<<<<<<< HEAD
                for (double i = 0; i <= Math.PI; i += Math.PI / 5) { //use this loop for individual radii of each circle
                    double radius = Math.sin(i) / 1.75; //radius of circle
                    double ySphere = Math.cos(i) / 1.75; //spaces out the y to look natural
                    for (double a = 0; a < Math.PI * 2; a += Math.PI / 5) { //loop to create the circle at x,y,z
                        double xSphere = Math.cos(a) * radius;
                        double zSphere = Math.sin(a) * radius;
                        location.add(xSphere, ySphere, zSphere);
                        broadCastColoredParticle(Color.ORANGE, location);
                        location.subtract(xSphere, ySphere, zSphere);
                    }
                }

                playParticle(EnumParticle.LAVA, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);
                playParticle(EnumParticle.CLOUD, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0.2F, 0.2F, 0.2F, 0.05F, 5);

                if (t >= particleCount) {

                    playParticle(EnumParticle.EXPLOSION_HUGE, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);

                    otherPlayer.getChampion().subtractHealth(90, plugin, gamePlayer, otherPlayer, false);
                    target.setVelocity(target.getLocation().getDirection().add(new Vector(0, 0.5, 0)).multiply(-1.5));
                    target.playEffect(EntityEffect.HURT);
                    TextUtils.damageMsg(gamePlayer, otherPlayer);

                    this.cancel();
                }

            }

        }.runTaskTimer(plugin, 0, 1);
=======
                playParticle(EnumParticle.FIREWORKS_SPARK, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);
                playParticle(EnumParticle.LAVA, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);

                if (t >= 100) {

                    playParticle(EnumParticle.EXPLOSION_LARGE, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);

                    this.cancel();
                }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }

    public static void helixEffect(Main plugin, Player player) {

        new BukkitRunnable() {
            Location loc = player.getLocation().add(0, 1, 0);
            double t = 0;
            double r = 2;

            public void run() {
                t += Math.PI / 16;
                double x = r * Math.cos(t);
                double y = r * Math.sin(t);
                double z = r * Math.sin(t);

                loc.add(x, y, z);
                playParticle(EnumParticle.VILLAGER_ANGRY, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                playParticle(EnumParticle.LAVA, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                loc.subtract(x, y, z);

                x = r * -Math.cos(t);
                z = r * -Math.sin(t);

                loc.add(x, y, z);
                playParticle(EnumParticle.VILLAGER_ANGRY, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                playParticle(EnumParticle.LAVA, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                loc.subtract(x, y, z);

                z = r * Math.sin(t) / 0.6;

                if (t > (Math.PI * 3) / 2) {
                    x = r * Math.cos(t) * Math.sin(t) / 2;
                    y = r * Math.cos(t);
                } else {
                    x = r * Math.cos(t) * Math.sin(t);
                    y = r * Math.sin(t) * 0.1;
                }

                loc.add(x, y, z);
                playParticle(EnumParticle.SMOKE_NORMAL, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                loc.subtract(x, y, z);

                if (t > (Math.PI * 3) / 2)
                    y = r * Math.cos(t);
                else
                    y = r * Math.sin(t) * 0.1;

                loc.add(0, y, 0);
                playParticle(EnumParticle.EXPLOSION_LARGE, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
                loc.subtract(0, y, 0);

                if (t > Math.PI * 3) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);

    }

    public static void swirlEffect(Main plugin, Player player, int radius, int iterations, int strands, int particles) {

        for (int i = 0; i < (iterations * 4); i++) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    Location location = player.getLocation().add(0, 5, 0);

                    for (int i = 1; i <= strands; i++) {
                        for (int j = 1; j <= particles; j++) {
                            float ratio = (float) j / particles;
                            double angle = 10 * ratio * 2 * Math.PI / strands + (2 * Math.PI * i / strands) + (Math.PI / 4);
                            double x = Math.cos(angle) * ratio * radius;
                            double z = Math.sin(angle) * ratio * radius;
                            location.add(x, 0, z);
                            if (j < (particles - 10))
                                broadCastColoredParticle(Color.WHITE, location);
                            else if (j < (particles - 6))
                                broadCastColoredParticle(Color.AQUA, location);
                            else if (j < (particles - 2))
                                broadCastColoredParticle(Color.TEAL, location);
                            else
                                broadCastColoredParticle(Color.NAVY, location);

                            if (j % 3 == 0)
                                playParticle(EnumParticle.WATER_DROP, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);
                            else if (j % 5 == 0)
                                playParticle(EnumParticle.CLOUD, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0, 0, 0, 0, 1);

                            location.subtract(x, 0, z);
                        }
                    }

                }

            }.runTaskLater(plugin, i * 5);

<<<<<<< HEAD
        }

    }

    public static void doGolemKnockupEffect(Main plugin, Entity entity, float radius, byte fillMod) {

        new BukkitRunnable() {

            double theta = 0;
            double incrementY = 0;
            Location location = entity.getLocation();

            @Override
            public void run() {

                theta += Math.PI / 20;
                incrementY += 0.25D;

                double x = location.getX() + (Math.cos(theta) * radius);
                double y = location.getY() + incrementY;
                double z = location.getZ() + (Math.sin(theta) * radius);
                Location loc = new Location(entity.getWorld(), x, y, z);

                broadCastColoredParticle(Color.ORANGE, loc);
                if (theta > Math.PI) {
                    double oldX = x;
                    double oldZ = z;
                    loc.subtract(x, 0 , z);
                    x = location.getX() + (Math.sin(theta) * radius);
                    z = location.getZ() + (Math.cos(theta) * radius);
                    loc.add(x, 0, z);
                    broadCastColoredParticle(Color.RED, loc);
                    loc.setX(oldX);
                    loc.setZ(oldZ);
                }

                if (theta >= Math.PI * 2) {

                    for (float i = 0; i < fillMod * radius * (Math.PI / Math.E); i += 0.5F) {

                        z = (i / 2) >= radius ? radius : i / 2;
                        location.add(i >= radius ? radius : i, 0, z);
                        for (double j = 0; j < 2; j += 0.2D) {

                            playParticle(EnumParticle.CRIT_MAGIC, (float) location.getX(), (float) (location.getY() + j), (float) location.getZ(), 0, 0, 0, 0, 1);

                        }

                        location.subtract(i, 0, z);

                    }

                    cancel();
                }

            }

        }.runTaskTimer(plugin, 0, 1);

    }

    private static Vector rotateFunction(Vector v, Location location) {

        double yawR = location.getYaw() / 180.0 * Math.PI;
        double pitchR = location.getPitch() / 180 * Math.PI;

        v = rotateAboutX(v, pitchR);
        v = rotateAboutY(v, -yawR); //welcome to Minecraft kids
        return v;

    }

    private static Vector rotateAboutZ(Vector vect, double γ) {
        // z stays the same, no need to create a variable
        double x = Math.cos(γ) * vect.getX() - Math.sin(γ) * vect.getY();
        double y = Math.sin(γ) * vect.getX() + Math.cos(γ) * vect.getY();

        return vect.setX(x).setY(y);
    }

    private static Vector rotateAboutY(Vector vect, double b) {
        // y stays the same, no need to create a variable
        double x = Math.cos(b) * vect.getX() + Math.sin(b) * vect.getZ();
        double z = -Math.sin(b) * vect.getX() + Math.cos(b) * vect.getZ();

        return vect.setX(x).setZ(z);
    }

    private static Vector rotateAboutX(Vector vect, double a) {
        // x stays the same, no need to create a variable
        double y = Math.cos(a) * vect.getY() - Math.sin(a) * vect.getZ();
        double z = Math.sin(a) * vect.getY() + Math.cos(a) * vect.getZ();

        return vect.setY(y).setZ(z);
=======
        }.runTaskTimer(plugin, 1, 1);

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
    }

    private static void playParticle(EnumParticle enumParticle, float x, float y, float z, float offsetX, float offSetY, float offsetZ, float speed, int particleCount) {

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();

        try {
            setValue(packet, "a", enumParticle);
            setValue(packet, "b", x);
            setValue(packet, "c", y);
            setValue(packet, "d", z);
            setValue(packet, "e", offsetX);
            setValue(packet, "f", offSetY);
            setValue(packet, "g", offsetZ);
            setValue(packet, "h", speed);
            setValue(packet, "i", particleCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Player online : Bukkit.getOnlinePlayers())
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);

    }

}
