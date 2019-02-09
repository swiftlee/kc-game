package net.jmdev.entity;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.ParticlesUtil;
import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityIronGolem;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.IRangedEntity;
import net.minecraft.server.v1_9_R2.MinecraftKey;
import net.minecraft.server.v1_9_R2.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R2.SoundEffect;
import net.minecraft.server.v1_9_R2.SoundEffects;
import net.minecraft.server.v1_9_R2.World;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Random;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/23/2017 | 14:24
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
public class MysticalGolem extends EntityIronGolem implements IRangedEntity {

    private final Location home = new Location(getBukkitEntity().getWorld(), 1, 1, 1); // TODO: 8/29/2017 CHANGE THIS LOCATION
    private Main plugin;
    public byte attackCount;

    public MysticalGolem(World world, Main plugin) {
        super(world);
        attackCount = 0;
        LinkedHashSet goalB = (LinkedHashSet) getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet) getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet) getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet) getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        this.plugin = plugin;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        //this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, null, true, true));
        //this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, null, true, false));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(3, new PathfinderGoalArrowAttack(this, 1.0D, 20, 40, 5));
        //this.goalSelector.a(3, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        //this.goalSelector.a(4, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        //this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.0D));
        //this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 0.0F));
        //this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));

    }

    public void returnHome() {

        getNavigation().a(home.getX(), home.getY(), home.getZ(), 1.3);
        setGoalTarget(null, EntityTargetEvent.TargetReason.FORGOT_TARGET, true);

    }

    private void doKnockup() {

        ParticlesUtil.doGolemKnockupEffect(plugin, getBukkitEntity(), 5, (byte) 2);

        for (org.bukkit.entity.Entity entity : getBukkitEntity().getNearbyEntities(5, 5, 5)) {

            entity.setVelocity(new Vector(0, 0.2F, 0));

            if (entity.getType() == EntityType.PLAYER) {

                GamePlayer gamePlayer = GeneralUtils.getGamePlayer(entity.getUniqueId());
                gamePlayer.getChampion().subtractHealth(45, plugin, null, gamePlayer, false);

            } else
                Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(getBukkitEntity(), entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 22.5D));

            entity.playEffect(EntityEffect.HURT);

        }

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

        if (attackCount % 3 == 0)
            doKnockup();

    }

    private String getSoundFromSoundEffect(SoundEffect soundEffect) {
        for (MinecraftKey sound : SoundEffect.a.keySet()) {
            if (SoundEffect.a.get(sound) == soundEffect) return sound.a();
        }
        return null;
    }
}
