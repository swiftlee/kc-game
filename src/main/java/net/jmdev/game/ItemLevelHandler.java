package net.jmdev.game;

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
 * J&M CONFIDENTIAL - @author Jon - 07/21/2017 | 23:32
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
public class ItemLevelHandler {

    public static void giveLevelItem(GamePlayer gamePlayer, byte level) {

        Player player = Bukkit.getPlayer(gamePlayer.getUuid());
        Inventory inv = player.getInventory();

        ItemStack abilityTwo = null;
        ItemStack abilityThree = null;
        ItemStack ultimate = null;

        if (gamePlayer.getChampion().getName().equalsIgnoreCase("asger")) { //ASGER

            if (level == 3)
                abilityTwo = createAbility("Two", "Slaughterfest", "Basic attacking enemies gives them stacks of Slaughter, capping at 3. After a target has reached 3 stacks, you may activate this ability to dash at them and knock them up.", stat("Dash Damage", "30 Damage"), stat("Cost", "0 Mana"), stat("Cooldown", "12 Seconds"), stat("Scaling", "60% of your Attack Damage"));
            else if (level == 5)
                abilityThree = createAbility("Three", "Blood Spikes", "Enchants the ground around you, making spikes rise up in a 5 block radius around you. Anyone who is hit by the spikes bleeds for a short period of time.", stat("Bleed Damage", "20 Damage/Second"), stat("Bleed Duration", "4 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "9 Seconds"), stat("Scaling", "80% of your Attack Damage"));
            else if (level == 7)
                ultimate = createUltimate("Limitless Anger", "Upon activation, player in sight will be targeted. After your target has been selected, your attacks and abilities deal double damage against your target, and you are immune to damage from any source except your target.", stat("Duration", "5 Seconds"));

        } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("xiau tsun")) {

            if (level == 3)
                abilityTwo = createAbility("Two", "Runic Daggers", TextUtils.addLinebreaks("When using basic attacks, each attack will grant you a special power, stacking up to 5 times. If you do not attack anyone within 7 seconds, all of your stacks will disappear.|&eLvL. 1 - &7Backstab: Basic attacks deal + 10 damage when you attack enemies from behind.|&eLvL. 2 - &7Demoralize: Every 3rd basic attack applies a Slow I debuff for 2 seconds.|&eLvL. 3 - &7Reaper's Kiss: Every 3rd basic attack steals 5% health from your enemy.|&eLvL. 4 - &7Forcefulness: Every 3rd basic attack will deal 15% more damage.|&eLvL. 5 - Decapitation: All basic attacks have a 30% chance to immobilize the target for 2 seconds."), stat("Cost", "0 Mana"), stat("Cooldown", "None (Permanently activated ability)"));
            else if (level == 5)
                abilityThree = createAbility("Three", "Ambush", "Upon activation, stealths you for a short time. When the stealth period is over, you will automatically jump at the location you're looking at 5 blocks away from you.", stat("Stealth Duration", "3 Seconds"), stat("Cost", "60 Mana"), stat("Cooldown", "22 Seconds"));
            else if (level == 7)
                ultimate = createUltimate("Final Calling", "Xiau Tsun summons his inner-Otium to rise in the air and strike the nearest enemy player dealing great damage.", stat("Damage", "105"), stat("Cost", "100 Mana"), stat("Cooldown", "85 Seconds"));

        } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("kaine")) {

            if (level == 3)
                abilityTwo = createAbility("Two", "Devastating Sparks", "Using this ability strikes lightning upon all enemy units within a 5x5 block area around you, damaging them and applying a Slow II Debuff.", stat("Damage", "50 Damage"), stat("Cost", "80 Mana"), stat("Cooldown", "15 Seconds"), stat("Cooldown", "15 Seconds"), stat("Scaling", "80% of your Ability Power"), stat("Slow II Debuff Duration", "5 Seconds"));
            else if (level == 5)
                abilityThree = createAbility("Three", "Rain Dance", "Creates a gray cloud above you, causing rain and thunder to strike from it in a 5x5 area around you for 4 seconds. Allies that stay inside this area are healed every second, and enemies that stay in this area are damaged every second.", stat("Damage", "15 Damage/Second"), stat("Heal", "25 Health/Second"), stat("Cost", "75 Mana"), stat("Cooldown", "12 Seconds"));
            else if (level == 7)
                ultimate = createUltimate("Static Field", "Once activated, makes a 8x8 area around you a static field, damaging everyone and giving Slow III to whoever is in it.", stat("Damage", "30 Damage/Second"), stat("Cost", "125 Mana"), stat("Cooldown", "72 Seconds"), stat("Static Field Duration", "5 Seconds"));

        } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("xerol")) {

            if (level == 3)
                abilityTwo = createAbility("Two", "Eternal Hunger", "Your next basic attack turns into a bite which heals you 5% of your enemy's maximum health. Eternal Hunger also makes your enemy bleed for a short time for the amount of health you've healed for.", stat("Heal", "5% of Enemy Maximum Health"), stat("Bleed Damage", "1% of Enemy Maximum Health/Second"), stat("Bleed Duration", "5 Seconds"), stat("Cost", "35 Mana"), stat("Cooldown", "7 Seconds"), stat("Scaling", "None"));
            else if (level == 5)
                abilityThree = createAbility("Three", "Undead Rising", "Throws a magical bolt 5 blocks forward. If this bolt hits an enemy, 5 zombies will spawn around them and will start attacking them. Zombies that die turn into graves which your allies can over to be healed. Zombies die by themselves if they are not killed within 6 seconds.", stat("Zombie Damage Per Hit", "5 Damage"), stat("Zombie Health", "30 Health"), stat("Grave Heals", "15 Health"), stat("Cost", "90 Mana"), stat("Cooldown", "16 Seconds"), stat("Scaling", "None"));
            else if (level == 7)
                ultimate = createUltimate("Resurrection", "Cast this ability on an allied champion to give them the 'Resurrection' buff. When a player with this buff dies, it will rise from the dead as a zombie with all of their stats and abilities for 16 seconds. Once this time runes out, the player is automatically executed and their respawn timer starts. If the player with the 'Resurrection' buff does not die within 6 seconds of ability being cast on them, they will not be resurrected upon death.", stat("Cost", "150 Mana"), stat("Cooldown", "90 Seconds"), stat("Scaling", "None"));

        } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("corelia")) {

            if (level == 3)
                abilityTwo = createAbility("Two", "Blast Zone", "&7Makes a 7x7 area around a blast zone for a short period of time. While in the blast zone, you have increased health regeneration and your &eNuke 'Em! &7ability cooldown is deduced to 1 second.", stat("Blast Zone duration", "7 Seconds"), stat("Health Regeneration increase", "+5 HPS"), stat("Cost", "0 Mana"), stat("Cooldown", "16 Seconds"), stat("Scaling", "None"));
            else if (level == 5)
                abilityThree = createAbility("Three", "Stupefy", "&7Summons an explosive at your feet and explodes creating a shockwave slowing (Slow II) enemies within 7 blocks of the origin.", stat("Damage", "15"), stat("Slowness II", "4 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "11 Seconds"), stat("Scaling", "None"));
            else if (level == 7)
                ultimate = createUltimate("KABOOM!", "Target one player to throw a homing TNT missile at. This missile deals massive damage and knocks them back a very large distance.", stat("Damage", "125 Damage"), stat("Knockback", "15 Blocks"), stat("Cost", "0 Mana"), stat("Cooldown", "80 Seconds"));

        }

        if (level == 3)
            inv.setItem(3, abilityTwo);
        else if (level == 5)
            inv.setItem(4, abilityThree);
        else if (level == 7)
            inv.setItem(5, ultimate);

    }

    private static ItemStack createUltimate(String name, String desc, String... stats) {
        return createItem(Material.SUGAR_CANE, 0, "&7Ultimate Ability - &e&l" + name, TextUtils.addLinebreaks(desc), stats);
    }

    private static String stat(String name, String statistic) {
        return "&7" + name + " &7- &e" + statistic;
    }

    static ItemStack createAbility(String num, String name, String desc, String... stats) {
        Material mat = Material.BOWL;
        if (num.equalsIgnoreCase("one")) {
            mat = Material.CLAY_BRICK;
        } else if (num.equalsIgnoreCase("two")) {
            mat = Material.FLINT;
        } else if (num.equalsIgnoreCase("three")) {
            mat = Material.FEATHER;
        }
        return createItem(mat, 0, "&7Ability " + num + " - &e" + name, TextUtils.addLinebreaks(desc), stats);
    }

    private static ItemStack createItem(Material mat, int data, String name, String lore, String... stats) {

        ItemStack is = new ItemStack(mat, 1, (short) data);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> lores = new ArrayList<>();
        String[] lo = lore.split(";");

        for (String l : lo) {

            lores.add(TextUtils.formatText("&7" + l));

        }

        if (stats.length != 0) {

            lores.add(TextUtils.formatText("&e---"));
            for (String l : stats) {

                lores.add(TextUtils.formatText(l));

            }

        }

        ism.setLore(lores);
        ism.addItemFlags(ItemFlag.values());
        is.setItemMeta(ism);
        return is;

    }

}
