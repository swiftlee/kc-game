package net.jmdev.game;

import net.jmdev.util.TextUtils;
import net.minecraft.server.v1_9_R2.GameProfileSerializer;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/12/2017 | 17:12
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
public class ChampionSkull extends ItemStack {

    private final ItemStack skull;
    private final Skin skin;

    public ChampionSkull(Skin skin) {

        super(Material.SKULL_ITEM, 1, (byte) 3);
        this.skin = skin;

        NBTTagCompound nbt = new NBTTagCompound();
        net.minecraft.server.v1_9_R2.ItemStack items = CraftItemStack.asNMSCopy(this);

        nbt = GameProfileSerializer.serialize(nbt, skin);
        nbt.set("SkullOwner", nbt);
        items.setTag(nbt);

        ItemStack item = CraftItemStack.asBukkitCopy(items);
        ItemMeta skullMeta = item.getItemMeta();

        skullMeta.setDisplayName(TextUtils.formatText("&e&l" + skin.getChampion().getName() + " " + skin.getChampion().getChampion().toString()));

        ArrayList<String> lore = new ArrayList<>();
        lore.add(TextUtils.formatText("&7Click here to view abilities!"));
        skullMeta.setLore(lore);

        item.setItemMeta(skullMeta);
        skull = item;

    }

    public ItemStack getSkull() {
        return skull;
    }

    public Skin getSkin() {
        return skin;
    }
}
