package net.jmdev.ai;

import net.jmdev.Main;
import net.jmdev.entity.MysticalGolem;
import net.jmdev.game.GameMode;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.entity.CreatureSpawnEvent;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/20/2017 | 16:41
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
public class MobCampHandler {

    private Location centerLoc;
    //private List<Entity> campEntities = new ArrayList<>();
    private Main plugin;

    public MobCampHandler(Main plugin) {

        this.plugin = plugin;

    }

    /**
     * public int entityCount() {
     *
     * return campEntities.size();
     *
     * }
     */

    public void spawnCamps() {

        spawnMercenaryCamps(true, true);
        spawnBossCamps(true, true);
        spawnBuffCamps(true, true);

    }

    public void spawnMercenaryCamps(boolean red, boolean blue) {

        spawnMonsters(CampType.MERCENARY, red, blue);

    }

    public void spawnBossCamps(boolean red, boolean blue) {

        spawnMonsters(CampType.BOSS, red, blue);

    }

    public void spawnBuffCamps(boolean red, boolean blue) {

        spawnMonsters(CampType.BUFF, red, blue);

    }

    private void spawnMonsters(CampType type, boolean red, boolean blue) {

        if (type == CampType.BOSS) {

        } else if (type == CampType.BUFF) {
            //spawn mob
        } else if (type == CampType.MERCENARY) {

            Location locR;
            Location locB;
            if (GameMode.getMode() == GameMode._1V1) {
                locR = null;
                locB = null;
            } else if (GameMode.getMode() == GameMode._3V3) {
                locR = CampLocation._3V3MR;
                locB = CampLocation._3V3MB;
            } else {
                locR = null;
                locB = null;
            }

            for (byte i = 0; i < 4; i++) {

                MysticalGolem mysticalGolem;
                if (i < 2 && red) {
                    mysticalGolem = new MysticalGolem(((CraftWorld) locR.getWorld()).getHandle(), plugin);
                    mysticalGolem.setPositionRotation(locR.getX(), locR.getY(), locR.getZ(), 0, 0);
                    ((CraftWorld) locR.getWorld()).getHandle().addEntity(mysticalGolem, CreatureSpawnEvent.SpawnReason.CUSTOM);
                } else {
                    if (!blue)
                        break;
                    mysticalGolem = new MysticalGolem(((CraftWorld) locB.getWorld()).getHandle(), plugin);
                    mysticalGolem.setPositionRotation(locB.getX() + 2, locB.getY(), locB.getZ() - 2, 0, 0);
                    ((CraftWorld) locB.getWorld()).getHandle().addEntity(mysticalGolem, CreatureSpawnEvent.SpawnReason.CUSTOM);
                }
                ((CraftWorld) locR.getWorld()).getHandle().addEntity(mysticalGolem, CreatureSpawnEvent.SpawnReason.CUSTOM);
                ((IronGolem) mysticalGolem.getBukkitEntity()).setRemoveWhenFarAway(false);
                ((IronGolem) mysticalGolem.getBukkitEntity()).setTarget(null);
                ((IronGolem) mysticalGolem.getBukkitEntity()).setMaxHealth(750);
                ((IronGolem) mysticalGolem.getBukkitEntity()).setHealth(750);

            }
        }

        //create custom entity for each mob

    }

    enum CampType {

        BUFF, MERCENARY, BOSS

    }

}
