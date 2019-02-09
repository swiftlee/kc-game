package net.jmdev.entity;

import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntitySlime;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R2.World;

import org.bukkit.Location;

import java.lang.reflect.Field;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/09/2017 | 15:05
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
public class HitBox extends EntitySlime {

    public HitBox(World world) {

        super(world);

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
    public void collide(Entity e) {
    }

    public void setPosition(Location location) {

        super.setPosition(location.getX(), location.getY(), location.getZ());

    }
}
