package net.jmdev.entity;

import net.jmdev.Main;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by kedri on 8/19/2017.
 */
public class DonkeyRideable extends EntityHorse {

    private Main plugin;

    public DonkeyRideable(World world, Main plugin) {
        super(world);
        this.plugin = plugin;
        this.setType(EnumHorseType.DONKEY);
    }

    @Override
    public void g(float f, float f1) {

        // steering passegner
        EntityLiving entityliving = null;
        // search first human passenger
        for (final Entity e : passengers) {
            if (e instanceof EntityPlayer || e instanceof EntityHuman) {
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
