package net.jmdev.entity;

import net.jmdev.Main;
import net.minecraft.server.v1_9_R2.*;

import org.bukkit.scheduler.BukkitRunnable;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/17/2017 | 15:32
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
public class RideableEntity extends EntityArmorStand {

    private Main plugin;

    public RideableEntity(World world, Main plugin) {
        super(world);
        this.plugin = plugin;

    }

    @Override
    public void g(float f, float f1) {

        // steering passegner
        EntityLiving entityliving = null;
        // search first human passenger
        for (final Entity e : passengers) {
            if (e instanceof EntityPlayer ||e  instanceof EntityHuman) {
                entityliving = (EntityLiving) e;
                break;
            }
        }

        if (entityliving == null) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    boolean isHuman = false;

                    for (final Entity e : passengers) {
                        if (e instanceof EntityPlayer || e instanceof EntityHuman) {
                            isHuman = true;
                            break;
                        }
                    }

                    if (!isHuman)
                        getBukkitEntity().remove();

                }

            }.runTaskLater(plugin, 20);
            return;
        }

        final float yaw = entityliving.yaw;
        this.yaw = yaw;
        lastYaw = yaw;
        pitch = entityliving.pitch * 0.5f;
        setYawPitch(this.yaw, pitch);
        final float yaw2 = this.yaw;
        aM = yaw2;
        aO = yaw2;

        f = ((EntityHuman) entityliving).be * 0.25F;
        f1 = entityliving.bf;
        if (f1 <= 0.0f) {
            f1 *= 0.5F;// backwards slower
        }

        // set speed an go!
        this.l(0.2F);
        super.g(f, f1);

        P = 1.0F;

    }
}
