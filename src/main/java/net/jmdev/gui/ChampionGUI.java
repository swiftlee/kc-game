package net.jmdev.gui;

import net.jmdev.Main;
import net.jmdev.game.Champion;
import net.jmdev.game.ChampionSkull;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.Skin;
import net.jmdev.util.TextUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/11/2017 | 15:04
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
public class ChampionGUI extends GUI {

    ArrayList<ItemStack> stacks = new ArrayList<>();

    public ChampionGUI() {

        super.createGui("Champion Selector", 54);

        int counter = 0;

        for (Champion champion : Main.champions) {

            ChampionSkull championSkull = new ChampionSkull(champion.getSkin());

            if (counter == 0) //asger
                getGui().setItem(11, championSkull.getSkull());
            else if (counter == 1) //corelia
                getGui().setItem(13, championSkull.getSkull());
            else if (counter == 2) //kaine
                getGui().setItem(15, championSkull.getSkull());
            else if (counter == 3) //xerol
                getGui().setItem(30, championSkull.getSkull());
            else if (counter == 4) //xiau tsun
                getGui().setItem(32, championSkull.getSkull());

            counter++;

        }

    }

    public ChampionGUI(String championName, GamePlayer whoOpened) {

        Champion champion = null;

        for (Champion c : Main.champions) {

            if (c.getName().equalsIgnoreCase(championName)) {

                champion = c;
                break;

            }

        }

        super.createGui(champion != null ? champion.getName() : null, 54);

        ChampionSkull skull = new ChampionSkull(champion.getSkin());

        String championStatus;
        boolean playerHasTaken = (whoOpened.getChampion() != null && whoOpened.getChampion().getName().equalsIgnoreCase(championName)); // if taken by player who opened
        boolean championTaken = false;

        for (Object uuid : whoOpened.getTeam()) {

            for (GamePlayer player : Main.gamePlayers) {

                if (uuid == player.getUuid()) {

                    if (player.getChampion() != null) {

                        if (player.getChampion().getName().equalsIgnoreCase(championName))
                            championTaken = true;

                    }

                }

            }

        }

        ItemMeta statusStackMeta;

        ItemStack championStatusStack;

        if (playerHasTaken) {

            championStatus = TextUtils.formatText("&aYou have chosen this champion!");
            championStatusStack = new ItemStack(Material.INK_SACK, 1, (byte) 10);
            statusStackMeta = championStatusStack.getItemMeta();
            statusStackMeta.setDisplayName(championStatus);
            championStatusStack.setItemMeta(statusStackMeta);

        } else if (championTaken) {

            championStatus = TextUtils.formatText("&cSomeone else has already claimed this champion!");
            championStatusStack = new ItemStack(Material.BARRIER, 1);
            statusStackMeta = championStatusStack.getItemMeta();
            statusStackMeta.setDisplayName(championStatus);
            championStatusStack.setItemMeta(statusStackMeta);

        } else {

            championStatus = TextUtils.formatText("&eThis champion has not been claimed!");
            championStatusStack = new ItemStack(Material.INK_SACK, 1, (byte) 8);
            statusStackMeta = championStatusStack.getItemMeta();
            statusStackMeta.setDisplayName(championStatus);
            championStatusStack.setItemMeta(statusStackMeta);

        }


        for (int i = 0; i < 6; i++) {

            if (i == 0 || i == 5) {

                for (int j = 0; j < 9; j++) {

                    if (j == 4) {

                        if (i == 0) {

                            continue;

                        } else {

                            getGui().setItem(i * 9 + 3, createItem(Material.ARROW, 0, "&7Previous page"));
                            getGui().setItem(i * 9 + 4, championStatusStack);
                            continue;

                        }

                    }

                    getGui().setItem(i * 9 + j, clearName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0)));
                    getGui().setItem(i * 9 + j, clearName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0)));

                }
            }

            getGui().setItem(i * 9, clearName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0)));
            getGui().setItem(i * 9 + 8, clearName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0)));

        }

        setLevels(getGui());

        if (skull != null) {

            getGui().setItem(4, skull.getSkull());

        } else
            getGui().setItem(4, new ItemStack(Material.SKULL, 1, (byte) 3));

        if (champion.getName().equalsIgnoreCase("asger")) {

            getGui().setItem(20, createPassive("RAGE!!!", "When below &e10% &7health, you regenerate &e40% &7of your maximum health over &e35 &7seconds."));
            getGui().setItem(21, createAbility("One", "Insanity Aura", "Asger unleashes the flaming rage within him, damaging anyone within a 5 block radius around him.", stat("Damage", "35"), stat("Cost", "0 Mana"), stat("Cooldown", "12"), stat("Scaling", "None")));
            getGui().setItem(22, createAbility("Two", "Slaughterfest", "Basic attacking enemies gives them stacks of Slaughter, capping at 3. After a target has reached 3 stacks, you may activate this ability to dash at them and knock them up.", stat("Dash Damage", "30 Damage"), stat("Cost", "0 Mana"), stat("Cooldown", "12 Seconds"), stat("Scaling", "60% of your Attack Damage")));
            getGui().setItem(23, createAbility("Three", "Blood Spikes", "Enchants the ground around you, making spikes rise up in a 5 block radius around you. Anyone who is hit by the spikes bleeds for a short period of time.", stat("Bleed Damage", "20 Damage/Second"), stat("Bleed Duration", "4 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "9 Seconds"), stat("Scaling", "80% of your Attack Damage")));
            getGui().setItem(24, createUltimate("Limitless Anger", "Upon activation, player in sight will be targeted. After your target has been selected, your attacks and abilities deal double damage against your target, and you are immune to damage from any source except your target.", stat("Duration", "5 Seconds")));

        } else if (champion.getName().equalsIgnoreCase("xiau tsun")) {

            getGui().setItem(20, createPassive("Adrenaline", "On kill, assist or within stealth mode, gain a Speed III buff for 7 Seconds."));
            getGui().setItem(21, createAbility("One", "Assassination", "When you're within 10 blocks of an enemy player activate this ability to instantly teleport behind them, stabbing them in the back. This ability will prioritize the closest enemy player.", stat("Damage", "40 Damage"), stat("Cost", "80 Mana"), stat("Cooldown", "16 Seconds"), stat("Scaling", "20% of your Attack Damage")));
            getGui().setItem(22, createAbility("Two", "Runic Daggers", TextUtils.addLinebreaks("When using basic attacks, each attack will grant you a special power, stacking up to 5 times. If you do not attack anyone within 7 seconds, all of your stacks will disappear.|&eLvL. 1 - &7Backstab: Basic attacks deal + 10 damage when you attack enemies from behind.|&eLvL. 2 - &7Demoralize: Every basic attack applies a Slow I debuff for 2 seconds.|&eLvL. 3 - &7Reaper's Kiss: Every basic attack steals 5% health from your enemy.|&eLvL. 4 - &7Forcefulness: Every basic attack will deal 15% more damage.|&eLvL. 5 - Decapitation: All basic attacks have a 30% chance to immobilize the target for 2 seconds."), stat("Cost", "0 Mana"), stat("Cooldown", "None (Permanently activated ability)")));//WAIT TO DO THIS
            getGui().setItem(23, createAbility("Three", "Ambush", "Upon activation, stealths you for a short time. When the stealth period is over, you will automatically jump at the location you're looking at 5 blocks away from you.", stat("Stealth Duration", "3 Seconds"), stat("Cost", "60 Mana"), stat("Cooldown", "22 Seconds")));
            getGui().setItem(24, createUltimate("Final Calling", "Xiau Tsun summons his inner-Otium to rise in the air and strike the nearest enemy player dealing great damage.", stat("Damage", "105"), stat("Cost", "100 Mana"), stat("Cooldown", "85 Seconds")));

        } else if (champion.getName().equalsIgnoreCase("kaine")) {

            getGui().setItem(20, createPassive("Hypercharge", "After you've used 3 abilities, your next one is guaranteed to deal double damage and apply a 3 second stun."));
            getGui().setItem(21, createAbility("One", "Electroshock", "Activating this ability sends forth a shockwave to your weapon for the next 5 seconds. If within that period you attack an enemy, it will deal damage to them. If there is an enemy unit within 5 blocks of your target, the shock will transfer to them.", stat("Damage", "35 Damage"), stat("Cost", "55 Mana"), stat("Cooldown", "6 Seconds"), stat("Scaling", "50% of your Ability Power")));
            getGui().setItem(22, createAbility("Two", "Devastating Sparks", "Using this ability strikes lightning upon all enemy units within a 5x5 block area around you, damaging them and applying a Slow II Debuff.", stat("Damage", "50 Damage"), stat("Cost", "80 Mana"), stat("Cooldown", "15 Seconds"), stat("Cooldown", "15 Seconds"), stat("Scaling", "80% of your Ability Power"), stat("Slow II Debuff Duration", "5 Seconds")));
            getGui().setItem(23, createAbility("Three", "Rain Dance", "Creates a gray cloud above you, causing rain and thunder to strike from it in a 5x5 area around you for 4 seconds. Allies that stay inside this area are healed every second, and enemies that stay in this area are damaged every second.", stat("Damage", "15 Damage/Second"), stat("Heal", "25 Health/Second"), stat("Cost", "75 Mana"), stat("Cooldown", "12 Seconds")));
            getGui().setItem(24, createUltimate("Static Field", "Once activated, makes a 8x8 area around you a static field, damaging everyone and giving Slow III to whoever is in it.", stat("Damage", "30 Damage/Second"), stat("Cost", "125 Mana"), stat("Cooldown", "72 Seconds"), stat("Static Field Duration", "5 Seconds")));

        } else if (champion.getName().equalsIgnoreCase("xerol")) {

            getGui().setItem(20, createPassive("Grasp of the Undying", "All damage dealt against enemies restores health to every ally within 8 blocks of your enemy. Health restored is equal to 30% of damage dealt to the enemy."));
            getGui().setItem(21, createAbility("One", "Spawn: Grave", "Spawns a grave 3 blocks in front of you. Graves will heal you or an ally if they are walked over.", stat("Heal", "15 Health"), stat("Cost", "30 Mana"), stat("Cooldown", "5 Seconds")));
            getGui().setItem(22, createAbility("Two", "Eternal Hunger", "Your next basic attack turns into a bite which heals you 5% of your enemy's maximum health. Eternal Hunger also makes your enemy bleed for a short time for the amount of health you've healed for.", stat("Heal", "5% of Enemy Maximum Health"), stat("Bleed Damage", "1% of Enemy Maximum Health/Second"), stat("Bleed Duration", "5 Seconds"), stat("Cost", "35 Mana"), stat("Cooldown", "7 Seconds"), stat("Scaling", "None")));

            getGui().setItem(23, createAbility("Three", "Undead Rising", "Throws a magical bolt 5 blocks forward. If this bolt hits an enemy, 5 zombies will spawn around them and will start attacking them. Zombies that die turn into graves which your allies can over to be healed. Zombies die by themselves if they are not killed within 6 seconds.", stat("Zombie Damage Per Hit", "5 Damage"), stat("Zombie Health", "30 Health"), stat("Grave Heals", "15 Health"), stat("Cost", "90 Mana"), stat("Cooldown", "16 Seconds"), stat("Scaling", "None")));
            getGui().setItem(24, createUltimate("Resurrection", "Cast this ability on an allied champion to give them the 'Resurrection' buff. When a player with this buff dies, it will rise from the dead as a zombie with all of their stats and abilities for 16 seconds. Once this time runes out, the player is automatically executed and their respawn timer starts. If the player with the 'Resurrection' buff does not die within 6 seconds of ability being cast on them, they will not be resurrected upon death.", stat("Cost", "150 Mana"), stat("Cooldown", "90 Seconds"), stat("Scaling", "None")));

        } else if (champion.getName().equalsIgnoreCase("corelia")) {

            getGui().setItem(20, createPassive("Need More Firepower!", "&7Using &e3 &7abilities within &e12 &7seconds increases your basic attack and ability damages by &e100% &7for &e8 &7seconds."));
            getGui().setItem(21, createAbility("One", "Nuke 'Em!", "&7Fires a TNT Block 8 blocks forward which ignites mid air. Once the TNT block lands on the group, it instantly explodes damaging and knocking back anyone it hits.", stat("Damage", "30"), stat("Cost", "0 Mana"), stat("Cooldown", "6 Seconds"), stat("Scaling", "30% of your Attack Damage")));
            getGui().setItem(22, createAbility("Two", "Blast Zone", "&7Makes a 7x7 area around a blast zone for a short period of time. While in the blast zone, you have increased health regeneration and your &eNuke 'Em! &7ability cooldown is deduced to 1 second.", stat("Blast Zone duration", "7 Seconds"), stat("Health Regeneration increase", "+5 HPS"), stat("Cost", "0 Mana"), stat("Cooldown", "16 Seconds"), stat("Scaling", "None")));
            getGui().setItem(23, createAbility("Three", "Stupefy", "&7Summons an explosive at your feet and explodes creating a shockwave slowing (Slow II) enemies within 7 blocks of the origin.", stat("Damage", "15"), stat("Slowness II", "4 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "11 Seconds"), stat("Scaling", "None")));
            getGui().setItem(24, createUltimate("KABOOM!", "Target one player to throw a homing TNT missile at. This missile deals massive damage and knocks them back a very large distance.", stat("Damage", "125 Damage"), stat("Knockback", "15 Blocks"), stat("Cost", "0 Mana"), stat("Cooldown", "80 Seconds")));

        }

    }

    private ItemStack createUltimate(String name, String desc, String... stats) {
        return createItem(Material.SUGAR_CANE, 0, "&7Ultimate Ability - &e&l" + name, TextUtils.addLinebreaks(desc), stats);
    }

    private String stat(String name, String statistic) {
        return "&7" + name + " &7- &e" + statistic;
    }

    private ItemStack createItem(Material mat, int data, String name) {

        ItemStack is = new ItemStack(mat, 1, (short) data);
        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        ism.addItemFlags(ItemFlag.values());
        is.setItemMeta(ism);
        return is;

    }

    private void setLevels(Inventory inv) {

        inv.setItem(30, levelItem("One", 1));
        inv.setItem(31, levelItem("Three", 3));
        inv.setItem(32, levelItem("Five", 5));
        inv.setItem(33, levelItem("Seven", 7));

    }

    private ItemStack createPassive(String name, String desc) {
        return createItem(Material.BOWL, 0, "&7Passive Ability - &e" + name, TextUtils.addLinebreaks(desc));
    }

    ItemStack createAbility(String num, String name, String desc, String... stats) {
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

    private ItemStack levelItem(String level, int amount) {

        ItemStack is = createItem(Material.SEEDS, 0, "&7Level &e" + level, "&7This ability will be unlocked at Level " + level + ".");
        is.setAmount(amount);
        return is;

    }

    private ItemStack createItem(Material mat, int data, String name, String lore, String... stats) {

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

    private ItemStack clearName(ItemStack is) {

        ItemMeta ism = is.getItemMeta();
        ism.setDisplayName(ChatColor.GRAY + " ");
        is.setItemMeta(ism);
        return is;

    }

    private Skin kaineSkin() {

        Champion kaine = Main.champions[2];
        return new Skin(kaine.getSkinUUID(), kaine.getSkinName(), kaine);

    }

    private Skin asgerSkin() {

        Champion asger = Main.champions[0];
        return new Skin(asger.getSkinUUID(), asger.getSkinName(), asger);

    }

    private Skin xiauTsunSkin() {

        Champion xiauTsun = Main.champions[4];
        return new Skin(xiauTsun.getSkinUUID(), xiauTsun.getSkinName(), xiauTsun);


    }

    private Skin xerolSkin() {

        Champion xerol = Main.champions[3];
        return new Skin(xerol.getSkinUUID(), xerol.getSkinName(), xerol);

    }

    private Skin coreliaSkin() {

        Champion corelia = Main.champions[1];
        return new Skin(corelia.getSkinUUID(), corelia.getSkinName(), corelia);

    }

}
