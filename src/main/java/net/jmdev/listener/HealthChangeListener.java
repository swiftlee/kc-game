package net.jmdev.listener;

import net.jmdev.util.TextUtils;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/06/2017 | 18:16
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
public class HealthChangeListener implements Listener {

    @EventHandler
    public void onEntityHealthChange(EntityDamageByEntityEvent e) {

        if (e.isCancelled())
            return;

        if (e.getEntityType() == EntityType.ZOMBIE) {

            updateHealthBar((Zombie) e.getEntity());

        }

    }

    private void updateHealthBar(Zombie entity) {

        final double barPercent = (entity.getHealth() / entity.getMaxHealth());
        String levelBar = "::::::::::::::::::::".replace(":", "\u258C");
        int index = (int) (barPercent * 20);

        if (index > 0)
            levelBar = TextUtils.formatText("&a" + levelBar.substring(0, index) + "§c" + levelBar.substring(index, levelBar.length()));
        else
            levelBar = TextUtils.formatText("&c" + levelBar);

        if (!entity.isCustomNameVisible())
            entity.setCustomNameVisible(true);

        entity.setCustomName(levelBar);

    }

}
