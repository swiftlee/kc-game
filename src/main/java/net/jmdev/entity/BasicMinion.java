package net.jmdev.entity;


import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntitySlime;
import net.minecraft.server.v1_9_R2.EntityZombie;
import net.minecraft.server.v1_9_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_9_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R2.World;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/28/2017 | 15:11
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
public class BasicMinion extends EntityZombie {

    public BasicMinion(World world) {
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
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(3, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(4, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
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

}
