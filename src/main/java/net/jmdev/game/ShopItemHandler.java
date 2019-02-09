package net.jmdev.game;

import net.jmdev.Main;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/12/2017 | 14:51
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
public class ShopItemHandler {

    private Main plugin;

    public ShopItemHandler(Main plugin) {

        this.plugin = plugin;

    }

    public static void doPandoraStormbow(GamePlayer attacked) {

        Player player = Bukkit.getPlayer(attacked.getUuid());
        player.getWorld().strikeLightning(player.getLocation());
        player.playEffect(EntityEffect.HURT);

    }

    //returns true if item has a consumable effect
    public void buyEffect(GamePlayer gamePlayer, String name) {

        System.out.println("Before: " + (int) gamePlayer.getChampion().getAttackDamage());

        if (name.equalsIgnoreCase("Battle Axe")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addAttackDamage(10);
            gamePlayer.getChampion().addLifeSteal((byte) (gamePlayer.getChampion().getLifeSteal() * 0.05));

        } else if (name.equalsIgnoreCase("Book of Power")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addAbilityPower(20);

        } else if (name.equalsIgnoreCase("Training Sword")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addAttackDamage(10);
            System.out.println("After: " + (int) gamePlayer.getChampion().getAttackDamage());

        } else if (name.equalsIgnoreCase("Shining Stone")) { //NOT CONSUMABLE

            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() + 30);
            gamePlayer.getChampion().addHealthRegen(3);

        } else if (name.equalsIgnoreCase("Energy Soul")) { //NOT CONSUMABLE

            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() + 30);
            gamePlayer.getChampion().addManaRegen(3);

        } else if (name.equalsIgnoreCase("Cloak & Dagger")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addCriticalStrikeChance((byte) 10);
            gamePlayer.getChampion().addAttackDamage(5);

        } else if (name.equalsIgnoreCase("Iron Broadsword")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addAttackDamage(25);
            System.out.println("After: " + (int) gamePlayer.getChampion().getAttackDamage());

        } else if (name.equalsIgnoreCase("Bloodborn Hatchet")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addAttackDamage(25);
            gamePlayer.getChampion().addLifeSteal((byte) (gamePlayer.getChampion().getLifeSteal() * 0.08));

        } else if (name.equalsIgnoreCase("Sealed Pendant")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addAbilityPower(45);
            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() + 50);
            gamePlayer.getChampion().addManaRegen(5);

        } else if (name.equalsIgnoreCase("Passion's Embrace")) { //PASSIVE

            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() + 90);
            gamePlayer.getChampion().addHealthRegen(5);

        } else if (name.equalsIgnoreCase("Cowl of The Assassin")) { //NOT CONSUMABLE

            gamePlayer.getChampion().addCriticalStrikeChance((byte) 20);
            gamePlayer.getChampion().addAttackDamage(20);

        } else if (name.equalsIgnoreCase("Material Spirit")) { //PASSIVE

            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() + 80);
            gamePlayer.getChampion().addManaRegen(7);

        } else if (name.equalsIgnoreCase("Blade of Iron Will")) { //NOT CONSUMABLE

            new BukkitRunnable() {

                int count = 0;
                int addedDamage = 0;
                int addedHealth = 0;

                @Override
                public void run() {

                    if (gamePlayer.hasItem("Blade of Iron Will")) {

                        if (count < 10) {

                            gamePlayer.ironWillDamage += 2; //change this to gameplayer obj implementation
                            gamePlayer.ironWillHealth += 15; //change this to gameplayer obj implementation
                            count++;

                            gamePlayer.getChampion().addAttackDamage(2);
                            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() + 15);

                            for (ItemStack stack : Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().getContents()) {

                                String name = stack.getItemMeta().getDisplayName();

                                if (name.substring(2, name.length()).equalsIgnoreCase("Blade of Iron Will")) {

                                    ItemMeta meta = stack.getItemMeta();
                                    List<String> lore = new ArrayList<>();

                                    for (String s : meta.getLore()) {

                                        if (s.matches("(?i)Attack Damage.*")) {

                                            s = s.replaceAll("[0-9]{1,2}/", addedDamage + "/");

                                        } else if (s.matches("(?i)Health.*")) {

                                            s = s.replaceAll("[0-9]{1,3}/", addedHealth + "/");

                                        }

                                        lore.add(s);

                                    }

                                    meta.setLore(lore);
                                    stack.setItemMeta(meta);

                                    break;

                                }

                            }

                        } else
                            cancel();

                    } else
                        cancel();

                }

            }.runTaskLater(plugin, 20 * 60);

        } else if (name.equalsIgnoreCase("Illusionist's Gem")) { //TODO: ADD PASSIVE

            gamePlayer.getChampion().addAbilityPower(70);
            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() + 200);
            gamePlayer.getChampion().addManaRegen(10);

        } else if (name.equalsIgnoreCase("Pandora's Stormbow")) { //TODO: ADD PASSIVE -> STRIKE LIGHTNING AT ENEMY WITHIN 10 BLOCKS DEALING 10% THEIR MAX HEALTH, COOLDOWN 25s

            gamePlayer.getChampion().addAttackDamage(35);
            gamePlayer.getChampion().addCriticalStrikeChance((byte) 30);

        } else if (name.equalsIgnoreCase("Soulstealer")) { //TODO: ADD PASSIVE -> ADD 15 HEALTH FOR EVERY BASIC ATTACK

            gamePlayer.getChampion().addAttackDamage(40);
            gamePlayer.getChampion().addLifeSteal((byte) 15);

        } else if (name.equalsIgnoreCase("Reflecting Shield")) { //TODO: ADD PASSIVE

            gamePlayer.getChampion().setHealth(gamePlayer.getChampion().getHealth() + 200);
            gamePlayer.getChampion().addHealthRegen(1);

        } else if (name.equalsIgnoreCase("Vow of The Protector")) { //TODO: ADD PASSIVE

            gamePlayer.getChampion().addAbilityPower(80);
            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() + 250);
            gamePlayer.getChampion().addManaRegen(15);

        }

    }

    public void replaceEffect(GamePlayer gamePlayer, String name) {

        if (name.equalsIgnoreCase("Battle Axe")) {

            gamePlayer.getChampion().subtractAttackDamage(10);
            gamePlayer.getChampion().subtractLifeSteal((byte) (gamePlayer.getChampion().getLifeSteal() * 0.05));

        } else if (name.equalsIgnoreCase("Book of Power")) {

            gamePlayer.getChampion().subtractAbilityPower(20);

        } else if (name.equalsIgnoreCase("Training Sword")) {

            gamePlayer.getChampion().subtractAttackDamage(10);

        } else if (name.equalsIgnoreCase("Shining Stone")) {

            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() - 30);
            gamePlayer.getChampion().subtractHealthRegen(3);

        } else if (name.equalsIgnoreCase("Energy Soul")) {

            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() - 30);
            gamePlayer.getChampion().subtractManaRegen(3);

        } else if (name.equalsIgnoreCase("Cloak & Dagger")) {

            gamePlayer.getChampion().subtractCriticalStrikeChance((byte) 10);
            gamePlayer.getChampion().subtractAttackDamage(5);

        } else if (name.equalsIgnoreCase("Iron Broadsword")) {

            gamePlayer.getChampion().subtractAttackDamage(25);

        } else if (name.equalsIgnoreCase("Bloodborn Hatchet")) {

            gamePlayer.getChampion().subtractAttackDamage(25);
            gamePlayer.getChampion().subtractLifeSteal((byte) (gamePlayer.getChampion().getLifeSteal() * 0.08));

        } else if (name.equalsIgnoreCase("Sealed Pendant")) {

            gamePlayer.getChampion().subtractAbilityPower(45);
            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() - 50);
            gamePlayer.getChampion().subtractManaRegen(5);

        } else if (name.equalsIgnoreCase("Passion's Embrace")) {

            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() - 90);
            gamePlayer.getChampion().subtractHealthRegen(5);

        } else if (name.equalsIgnoreCase("Cowl of The Assassin")) {

            gamePlayer.getChampion().subtractCriticalStrikeChance((byte) 20);
            gamePlayer.getChampion().subtractAttackDamage(20);

        } else if (name.equalsIgnoreCase("Material Spirit")) {

            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() - 80);
            gamePlayer.getChampion().subtractManaRegen(7);
            if (!gamePlayer.hasItem("Material Spirit")) {
                gamePlayer.getChampion().subtractManaRegen(gamePlayer.materialSpirit);
                gamePlayer.materialSpirit = 0;
            }

        } else if (name.equalsIgnoreCase("Blade of Iron Will")) {

            gamePlayer.getChampion().subtractAttackDamage(gamePlayer.ironWillDamage);
            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() - gamePlayer.ironWillHealth);

        } else if (name.equalsIgnoreCase("Illusionist's Gem")) {

            gamePlayer.getChampion().subtractAbilityPower(70);
            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() - 200);
            gamePlayer.getChampion().subtractManaRegen(10);

        } else if (name.equalsIgnoreCase("Pandora's Stormbow")) {

            gamePlayer.getChampion().subtractAttackDamage(35);
            gamePlayer.getChampion().subtractCriticalStrikeChance((byte) 30);

        } else if (name.equalsIgnoreCase("Soulstealer")) {

            gamePlayer.getChampion().subtractAttackDamage(40);
            gamePlayer.getChampion().subtractLifeSteal((byte) 15);

        } else if (name.equalsIgnoreCase("Reflecting Shield")) {

            gamePlayer.getChampion().setHealth(gamePlayer.getChampion().getHealth() - 200);
            gamePlayer.getChampion().subtractHealthRegen(1);

        } else if (name.equalsIgnoreCase("Vow of The Protector")) {

            gamePlayer.getChampion().subtractAbilityPower(80);
            gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() - 250);
            gamePlayer.getChampion().subtractManaRegen(15);

        }

    }

    public void doClickEffect(GamePlayer gamePlayer, String name, int slot) {

        ItemStack purchasableItem = new ItemStack(Material.SEEDS, 1);
        ItemMeta purchasableMeta = purchasableItem.getItemMeta();
        purchasableMeta.setDisplayName(TextUtils.formatText("&a&lVisit the merchant to buy items!"));
        purchasableItem.setItemMeta(purchasableMeta);

        if (name.equalsIgnoreCase("Health Potion") && !gamePlayer.healthPotion) {

            gamePlayer.getChampion().addHealthRegen(10);
            gamePlayer.healthPotion = true;

            new BukkitRunnable() {

                @Override
                public void run() {

                    gamePlayer.healthPotion = false;
                    gamePlayer.getChampion().subtractHealthRegen(10);

                }

            }.runTaskLater(plugin, 20 * 5);

            Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(slot, purchasableItem);

        } else if (name.equalsIgnoreCase("Potion of Mist")) {

            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 15 * 20, 0, false, true);
            Bukkit.getPlayer(gamePlayer.getUuid()).addPotionEffect(potionEffect);

            Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(slot, purchasableItem);

        } else if (name.equalsIgnoreCase("Potion of Resistance") && !gamePlayer.resistancePotion) {

            gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() + 75);
            gamePlayer.resistancePotion = true;

            new BukkitRunnable() {

                @Override
                public void run() {

                    if (gamePlayer.resistancePotion) {

                        gamePlayer.getChampion().setMaxHealth(gamePlayer.getChampion().getMaxHealth() - 75);
                        gamePlayer.resistancePotion = false;

                    }

                }

            }.runTaskLater(plugin, 20 * 5 * 60);

            Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(slot, purchasableItem);

        } else if (name.equalsIgnoreCase("Potion of Power")) {

            if (!gamePlayer.powerPotion) {

                gamePlayer.getChampion().addAttackDamage(18);

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        if (gamePlayer.powerPotion)
                            gamePlayer.getChampion().subtractAttackDamage(18);

                    }

                }.runTaskLater(plugin, 20 * 5 * 60);

                Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(slot, purchasableItem);

            }

        }

    }

}
