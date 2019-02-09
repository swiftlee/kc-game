package net.jmdev.entity;

import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityTypes;
import net.minecraft.server.v1_9_R2.MinecraftKey;

import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/28/2017 | 16:53
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
public class CustomEntities {

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends Entity> nmsClass;
    private Class<? extends Entity> customClass;
    private MinecraftKey key;

    @SuppressWarnings("unchecked")
    CustomEntities(String name, int id, EntityType entityType, Class<? extends Entity> nmsClass, Class<? extends Entity> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.key = new MinecraftKey(name);
    }

    private static Object getPrivateStatic(final Class<?> clazz, final String f) {
        try {
            Field field = clazz.getDeclaredField(f);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registerEntity(String name, int id, Class<? extends Entity> customClass) {
        try {

            /*
             * First, we make a list of all HashMap's in the EntityTypes class
             * by looping through all fields. I am using reflection here so we
             * have no problems later when Mojang changes the field's name. By
             * creating a list of these maps we can easily modify them later on.
             */
            List<Map<?, ?>> dataMaps = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }

            // we need maps d (1) and f (3)

            ((Map<Class<? extends Entity>, String>) dataMaps.get(1)).put(customClass, name);
            ((Map<Class<? extends Entity>, Integer>) dataMaps.get(3)).put(customClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<?> getCustomClass() {
        return customClass;
    }
}
