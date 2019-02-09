package net.jmdev.gui;

import net.jmdev.Main;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/22/2017 | 12:05
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
public class MerchantGUI extends GUI {

    private Main plugin;

    public MerchantGUI(Main main) {
        this.plugin = main;
    }

    @Override
    public void open(Player p) {

        //Check if the player has the item
        Inventory inv = Bukkit.createInventory(null, 9 * 6, TextUtils.formatText("&8Merchant"));
        int tier1 = 0;
        int tier2 = 27;
        int tier3 = 45;
        //18 36
        for (int i = 0; i < 9; i++) {
            inv.setItem(i + 18, createItem(Material.STAINED_GLASS_PANE, ((byte) 5), ChatColor.GRAY.toString() + "", new ArrayList<>()));
        }
        for (int i = 0; i < 9; i++) {
            inv.setItem(i + 36, createItem(Material.STAINED_GLASS_PANE, ((byte) 5), ChatColor.GRAY.toString() + "", new ArrayList<>()));
        }
        for (String s : plugin.items.keySet()) {
            Item item = plugin.items.get(s);
            if (item.getTier() == 1) {
                inv.setItem(tier1, item.createItem(false));
                tier1++;
            } else if (item.getTier() == 2) {
                inv.setItem(tier2, item.createItem(false));
                tier2++;
            } else if (item.getTier() == 3) {
                inv.setItem(tier3, item.createItem(false));
                tier3++;
            }
        }
        p.openInventory(inv);

    }

    private ItemStack createItem(Material material, byte data, String name, List<String> lore) {
        ItemStack is = new ItemStack(material, 1, data);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> list = new ArrayList<>();
        for (String s : lore) {
            list.add(TextUtils.formatText(s));
        }
        ism.addItemFlags(ItemFlag.values());
        ism.setLore(list);
        is.setItemMeta(ism);
        return is;
    }
}

