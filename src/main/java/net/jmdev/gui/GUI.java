package net.jmdev.gui;

import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/11/2017 | 15:05
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
public abstract class GUI {

    private Inventory inventory;

    Inventory createGui(String name, int size) {

        return inventory = Bukkit.createInventory(null, size, TextUtils.formatText(name));

    }

    public Inventory getGui() {

        return inventory;

    }

    public void removeItem(int slot) {

        inventory.setItem(slot, new ItemStack(Material.AIR));

    }

    public void open(Player p) {

        p.openInventory(inventory);

    }

    public void close() {

        inventory.getViewers().forEach(HumanEntity::closeInventory);

    }

}
