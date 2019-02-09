package net.jmdev.listener;

import net.jmdev.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/13/2017 | 15:03
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
public class InventoryCloseListener implements Listener {

    private Main plugin;
    private boolean bool = true;

    public InventoryCloseListener(Main plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void onPlayerPickUpItem(PlayerPickupItemEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent e) {

        Inventory inv = e.getInventory();

        if (inv.getName().substring(2, inv.getName().length()).equalsIgnoreCase("Merchant") && bool) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    e.getPlayer().closeInventory();
                    e.getPlayer().openInventory(inv);

                }

            }.runTaskLater(plugin, 0);

            bool = false;

        }

    }

}
