package net.jmdev.entity;

import net.jmdev.util.ParticlesUtil;
import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.EntitySlime;
import net.minecraft.server.v1_9_R2.EntityZombie;
import net.minecraft.server.v1_9_R2.IRangedEntity;
import net.minecraft.server.v1_9_R2.MinecraftKey;
import net.minecraft.server.v1_9_R2.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R2.SoundEffect;
import net.minecraft.server.v1_9_R2.SoundEffects;
import net.minecraft.server.v1_9_R2.World;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Random;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/10/2017 | 13:57
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
public class RangedMinion extends EntityZombie implements IRangedEntity {

    public RangedMinion(World world) {
        super(world);
        LinkedHashSet goalB = (LinkedHashSet) getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet) getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet) getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet) getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        //this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, null, true, true));
        //this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, null, true, false));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 20, (int) (1.6 * 20), 10.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        //this.goalSelector.a(4, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        //this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.0D));
        //this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 0.0F));
        //this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));

    }

    @Override
    public void collide(Entity e) {

        if (e instanceof HitBox || e instanceof EntitySlime)
            return;

        super.collide(e);

    }

    private Object getPrivateField(String fieldName, Class<PathfinderGoalSelector> clazz, PathfinderGoalSelector object) {
        Field field;
        Object o = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public void a(EntityLiving entityLiving, float v) {

        Entity target = this.getGoalTarget();
        if (target == null)
            return;

        Vector playerLookDirection = getBukkitEntity().getLocation().getDirection();

        //create a vector perpendicular to the look direction so that it updates position as the yaw changes
        Vector perpVector = new Vector(-playerLookDirection.getZ(), playerLookDirection.getY(), playerLookDirection.getX()).normalize();

        //use this vector to place the particle beam to the right of the player
        double x = getBukkitEntity().getLocation().getX() + (perpVector.getX() * .75);//The .75 just places it .75-ish blocks away
        double y = getBukkitEntity().getLocation().getY() + 1.75D;
        double z = getBukkitEntity().getLocation().getZ() + (perpVector.getZ() * .75);

        Location from = new Location(getBukkitEntity().getWorld(), x, y, z);
        Location to = target.getBukkitEntity().getLocation().add(0, 1.5, 0);
        Vector dir = to.subtract(from).toVector();
        float length = (float) dir.length();
        dir.normalize();
        float ratio = length / 100; //100 is the amount of particles, this is the number to increment by to create a complete line based on the amount of particles you have

        Vector vect = dir.multiply(ratio);
        Location loc = from.clone().subtract(vect);

        for (int i = 0; i < 100; i++) {

            loc.add(vect);
            ParticlesUtil.broadCastColoredParticle(Color.YELLOW, loc);

        }

        int damage = new Random().nextInt(20 - 16 + 1) + 16;

        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(getBukkitEntity(), target.getBukkitEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));

        String soundEffect = getSoundFromSoundEffect(SoundEffects.bp);
        if (soundEffect != null)
            a(SoundEffect.a.get(new MinecraftKey(soundEffect)), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));

    }

    private String getSoundFromSoundEffect(SoundEffect soundEffect) {
        for (MinecraftKey sound : SoundEffect.a.keySet()) {
            if (SoundEffect.a.get(sound) == soundEffect) return sound.a();
        }
        return null;
    }

}
