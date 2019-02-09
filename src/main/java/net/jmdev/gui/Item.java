package net.jmdev.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/22/2017 | 12:06
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
public class Item {
    private int gold;
    List<String> lore = new ArrayList<>();

    private String name = "";
    private double attackDamage = 0;
    private int abilityPower = 0;
    private double health = 0;
    private int mana = 0;
    private int healthRegen = 0;
    private int manaRegen = 0;
    private byte cooldownReduction = 0;
    private byte criticalStrikeChance = 0;
    private byte lifeSteal = 0;
    private byte spellVamp = 0;
    private String material = "BOOK";
    private byte data = 0;
    private int tier = 1;

    public Item(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAbilityPower() {
        return abilityPower;
    }

    public void setAbilityPower(int abilityPower) {
        this.abilityPower = abilityPower;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getHealthRegen() {
        return healthRegen;
    }

    public void setHealthRegen(int healthRegen) {
        this.healthRegen = healthRegen;
    }

    public int getManaRegen() {
        return manaRegen;
    }

    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }

    public byte getCooldownReduction() {
        return cooldownReduction;
    }

    public void setCooldownReduction(byte cooldownReduction) {
        this.cooldownReduction = cooldownReduction;
    }

    public byte getCriticalStrikeChance() {
        return criticalStrikeChance;
    }

    public void setCriticalStrikeChance(byte criticalStrikeChance) {
        this.criticalStrikeChance = criticalStrikeChance;
    }

    public byte getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(byte lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public byte getSpellVamp() {
        return spellVamp;
    }

    public void setSpellVamp(byte spellVamp) {
        this.spellVamp = spellVamp;
    }

    public ItemStack createItem(boolean has) {
        ItemStack is = new ItemStack(Material.valueOf(material), 1, data);
        ItemMeta ism = is.getItemMeta();
        if (has) {
            is.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }
        ism.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> list = new ArrayList<>();
        for (String s : lore) {
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        ism.addItemFlags(ItemFlag.values());
        ism.setLore(list);
        is.setItemMeta(ism);
        return is;
    }
}

