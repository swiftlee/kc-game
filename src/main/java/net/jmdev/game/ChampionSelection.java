package net.jmdev.game;

import de.slikey.effectlib.EffectManager;

import net.jmdev.Connection.Database;
import net.jmdev.Connection.PlayerData;
import net.jmdev.Connection.Weapons;
import net.jmdev.Main;
import net.jmdev.ai.MinionAI;
import net.jmdev.ai.MobCampHandler;
import net.jmdev.champions.AsgerAbilities;
import net.jmdev.gui.ChampionGUI;
import net.jmdev.util.ActionBar;
import net.jmdev.util.Scoreboard;
import net.jmdev.util.TextUtils;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/12/2017 | 20:47
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
public class ChampionSelection {

    private BukkitTask task;
    private Main plugin;
    private EffectManager effectManager;

    public ChampionSelection(Main plugin, EffectManager effectManager) {

        this.plugin = plugin;
        this.effectManager = effectManager;

    }

    private static ArrayList<Block> getSurroundingBlocks(Block block) {

        ArrayList<Block> blocks = new ArrayList<>();

        blocks.add(block.getRelative(BlockFace.UP));
        blocks.add(block.getRelative(BlockFace.DOWN));
        blocks.add(block.getRelative(BlockFace.NORTH));
        blocks.add(block.getRelative(BlockFace.SOUTH));
        blocks.add(block.getRelative(BlockFace.EAST));
        blocks.add(block.getRelative(BlockFace.WEST));

        return blocks;

    }

    public void start(boolean forcestarted) {

        GameState.setGameState(GameState.CHAMPION_SELECT);

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);

        for (Player p : Bukkit.getOnlinePlayers()) {

            ChampionGUI gui = new ChampionGUI();
            gui.open(p);
            meta.setOwner(p.getName());
            head.setItemMeta(meta);
            p.getInventory().setItem(0, head);

        }

        final int[] j = {0};

            task = new BukkitRunnable() {

                public void run() {
                    //Fix for 1v1 and 5v5
                    if (!forcestarted) {
                        if (Bukkit.getOnlinePlayers().size() != 6) {
                            cancel();
                            GameState.setGameState(GameState.WAITING);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.getInventory().clear();
                            }
                            return;
                        }
                    }
                    Bukkit.getOnlinePlayers().forEach(p -> p.setLevel(plugin.getConfig().getInt("championSelect.time") - j[0]));

                    if ((plugin.getConfig().getInt("championSelect.time") - j[0]) <= 5) {

                        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 1.0F, 1.0F));

                        if (plugin.getConfig().getInt("championSelect.time") == j[0]) {

                            Location loc1 = TextUtils.parseLocation(plugin.getConfig().getString("gameStart.blueFence"));
                            Location loc2 = TextUtils.parseLocation(plugin.getConfig().getString("gameStart.redFence"));

                            fillArea(loc1.getBlock(), loc1.getBlock().getType());
                            fillArea(loc2.getBlock(), loc2.getBlock().getType());
                            cancel();
                            assignRandomChampions();
                            Player pl = null;
                            for (Player player : Bukkit.getOnlinePlayers()) {

                                for (GamePlayer gamePlayer : Main.gamePlayers) {
                                    if (gamePlayer.getUuid().equals(player.getUniqueId())) {

                                        if (pl == null)
                                            pl = player;
                                        gamePlayer.getChampion().setHealth(gamePlayer.getChampion().getMaxHealth());
                                        gamePlayer.getChampion().setMana(gamePlayer.getChampion().getMaxMana());
                                    }
                                }

                            }

                            Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
                            for (Player p : Bukkit.getOnlinePlayers()) {

                                p.getInventory().clear();

                            }
                            giveInventory();

                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    new MobCampHandler(plugin).spawnMercenaryCamps(true, true);

                                }

                            }.runTaskLater(plugin, 3 * 60 * 20);

                            Location locRed = null;
                            Location locBlue = null;
                            if (GameMode.getMode() == GameMode._1V1) {

                                //locRed = new Location(pl.getWorld(), 647.5, 18D, 40.5, 135, 0);
                                //locBlue = new Location(pl.getWorld(), 919.5, 18D, 36.5, -45, 0);
                                locRed = new Location(pl.getWorld(), 936.5, 21, 44.5, -135, 0); //for testing
                                locBlue = new Location(pl.getWorld(), 624.5, 21, 44.5, 135, 0); //for testing

                            } else if (GameMode.getMode() == GameMode._3V3) {

                                locRed = new Location(pl.getWorld(), 936.5, 21, 44.5, -135, 0);
                                locBlue = new Location(pl.getWorld(), 624.5, 21, 44.5, 135, 0);

                                Player finalPl = pl;
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {

                                        finalPl.getWorld().setTime(5000);

                                    }

                                }.runTaskTimer(plugin, 0, 15);

                            }

                            GameState.setGameState(GameState.STARTED);
                            task = null;
                            Villager villager = (Villager) pl.getWorld().spawnEntity(locBlue, EntityType.VILLAGER);
                            villager.setAI(false);
                            villager.setBreed(false);
                            villager.setInvulnerable(true);
                            villager.setCustomName(TextUtils.formatText("&a&lMerchant Shop"));
                            villager.setCustomNameVisible(true);
                            villager.setMetadata("blue", new FixedMetadataValue(plugin, ""));
                            villager.setProfession(Villager.Profession.LIBRARIAN);

                            Villager villager2 = (Villager) pl.getWorld().spawnEntity(locRed, EntityType.VILLAGER);
                            villager2.setAI(false);
                            villager2.setBreed(false);
                            villager2.setInvulnerable(true);
                            villager2.setCustomNameVisible(true);
                            villager2.setCustomName(TextUtils.formatText("&a&lMerchant Shop"));
                            villager.setMetadata("red", new FixedMetadataValue(plugin, ""));
                            villager2.setProfession(Villager.Profession.LIBRARIAN);

                            Turrets turrets = new Turrets(plugin, effectManager);
                            turrets.searchBlueLocations(villager.getWorld());
                            turrets.searchRedLocations(villager.getWorld());

                            MinionAI minionAI = new MinionAI(plugin);
                            minionAI.setupNodes(villager.getWorld());

                            Main.gamePlayers.forEach(gamePlayer -> {
                                //TODO: Implement runes.
                                /*PlayerData pd = Database.getPlayerByUUID(gamePlayer.getUuid());
                                List<Rune> runes = pd.getRunes();
                                List<Rune> newRunes = new ArrayList<>();
                                for (int i = 0; i < runes.size(); i++) {
                                    Rune rune = runes.get(i);
                                    if (rune.isActive()){
                                        rune.setGamesLeft(rune.getGamesLeft()-1);
                                        switch (rune.getPrimary()){
                                            case MANA:
                                                gamePlayer.getChampion().setMaxMana(gamePlayer.getChampion().getMaxMana() + gamePlayer.getChampion().getMaxMana()*(rune.getSecondary().num/100));
                                                break;
                                            case LIFE_STEAL:
                                                gamePlayer.getChampion().setLifeSteal(((byte) (gamePlayer.getChampion().getLifeSteal() + gamePlayer.getChampion().getLifeSteal() * (rune.getSecondary().num / 100))));
                                                break;
                                            case HEALTH:
                                                gamePlayer.getChampion().setMaxHealth(((byte) (gamePlayer.getChampion().getMaxHealth() + gamePlayer.getChampion().getMaxHealth() * (rune.getSecondary().num / 100))));
                                                break;
                                            case ABILITY_POWER:
                                                gamePlayer.getChampion().setAbilityPower(((byte) (gamePlayer.getChampion().getAbilityPower() + gamePlayer.getChampion().getAbilityPower() * (rune.getSecondary().num / 100))));
                                                break;
                                            case GOLD_PER_SECOND:
                                                gamePlayer.goldPerSecond = rune.getSecondary().num;
                                                break;
                                            case ATTACK_DAMAGE:
                                                gamePlayer.getChampion().setAttackDamage(((byte) (gamePlayer.getChampion().getAttackDamage() + gamePlayer.getChampion().getAttackDamage() * (rune.getSecondary().num / 100))));
                                                break;
                                            case HEALTH_REGENERATION:
                                                gamePlayer.getChampion().setHealthRegen(((byte) (gamePlayer.getChampion().getCriticalStrikeChance() + gamePlayer.getChampion().getCriticalStrikeChance() * (rune.getSecondary().num / 100))));
                                                break;
                                            case MANA_REGENERATION:
                                                gamePlayer.getChampion().setManaRegen(((byte) (gamePlayer.getChampion().getManaRegen() + gamePlayer.getChampion().getManaRegen() * (rune.getSecondary().num / 100))));
                                                break;
                                            case CRITICAL_STRIKE_CHANCE:
                                                gamePlayer.getChampion().setCriticalStrikeChance(((byte) (gamePlayer.getChampion().getCriticalStrikeChance() + gamePlayer.getChampion().getCriticalStrikeChance() * (rune.getSecondary().num / 100))));
                                                break;
                                            case COOLDOWN_REDUCTION:
                                                gamePlayer.getChampion().setCooldownReduction(((byte) (gamePlayer.getChampion().getCooldownReduction() + ((gamePlayer.getChampion().getCooldownReduction() * (rune.getSecondary().num / 100))))));
                                                break;
                                        }
                                        if (rune.getGamesLeft() > 0){
                                            newRunes.add(rune);
                                        }
                                    }else{
                                        newRunes.add(rune);
                                    }
                                }
                                pd.setRunes(newRunes);*/
                                gamePlayer.setGold(500);
                            }
                            );

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Bukkit.broadcastMessage(TextUtils.formatText("&6Minions are now spawning!"));

                                    new BukkitRunnable() {
                                        public void run() {
                                            minionAI.spawn(villager.getWorld());
                                        }
                                    }.runTaskTimer(plugin, 0, 20 * 45);

                                    minionAI.updateBlueMinions();
                                    minionAI.updateRedMinions();

                                }
                            }.runTaskLater(plugin, 20 * 10);

                            Regeneration regeneration = new Regeneration(plugin, villager.getWorld());
                            regeneration.startHealthRegen();
                            regeneration.startManaRegen();
                            regeneration.startGoldGeneration();
                            new ActionBar(plugin).startUpdates();
                            List<GamePlayer> asgers = new ArrayList<>();
                            for (GamePlayer gamePlayer : Main.gamePlayers) {
                                if (gamePlayer.getChampion().getName().equalsIgnoreCase("asger")) {
                                    asgers.add(gamePlayer);
                                    if (asgers.size() == 2)
                                        break;
                                }
                            }

                            new BukkitRunnable() {

                                @Override
                                public void run() {

                                    if (asgers.size() > 0) {
                                        for (GamePlayer gamePlayer : asgers)
                                            new AsgerAbilities(effectManager, plugin).rage(Bukkit.getPlayer(gamePlayer.getUuid()), gamePlayer);
                                    }

                                }

                            }.runTaskTimer(plugin, 0, 10);

                            if (GameMode.getMode() == GameMode._3V3)
                                Scoreboard.start3v3Board(plugin);

                            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
                            LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
                            ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                            LeatherArmorMeta chestMeta = (LeatherArmorMeta) chest.getItemMeta();
                            ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                            LeatherArmorMeta pantsMeta = (LeatherArmorMeta) pants.getItemMeta();
                            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
                            LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();

                            for (Team team : Main.teams) {

                                for (Object uuid : team) {

                                    if (team.getName().equalsIgnoreCase("red")) {

                                        Player p = Bukkit.getPlayer((UUID) uuid);

                                        helmetMeta.setColor(Color.RED);
                                        helmet.setItemMeta(helmetMeta);
                                        chestMeta.setColor(Color.RED);
                                        chest.setItemMeta(chestMeta);
                                        pantsMeta.setColor(Color.RED);
                                        pants.setItemMeta(pantsMeta);
                                        bootsMeta.setColor(Color.RED);
                                        boots.setItemMeta(bootsMeta);

                                        PlayerData pd = Database.getPlayerByUUID((UUID) uuid);
                                        if (pd.getActiveHat() != null)
                                            p.getEquipment().setHelmet(new ItemStack(Material.valueOf(pd.getActiveHat().getMaterial()), 1, (short) pd.getActiveHat().getB()));
                                        else
                                            p.getEquipment().setHelmet(helmet);
                                        p.getEquipment().setChestplate(chest);
                                        p.getEquipment().setLeggings(pants);
                                        p.getEquipment().setBoots(boots);

                                    } else if (team.getName().equalsIgnoreCase("blue")) {

                                        Player p = Bukkit.getPlayer((UUID) uuid);

                                        helmetMeta.setColor(Color.BLUE);
                                        helmet.setItemMeta(helmetMeta);
                                        chestMeta.setColor(Color.BLUE);
                                        chest.setItemMeta(chestMeta);
                                        pantsMeta.setColor(Color.BLUE);
                                        pants.setItemMeta(pantsMeta);
                                        bootsMeta.setColor(Color.BLUE);
                                        boots.setItemMeta(bootsMeta);

                                        PlayerData pd = Database.getPlayerByUUID((UUID) uuid);
                                        if (pd.getActiveHat() != null)
                                            p.getEquipment().setHelmet(new ItemStack(Material.valueOf(pd.getActiveHat().getMaterial()), 1, (short) pd.getActiveHat().getB()));
                                        else
                                            p.getEquipment().setHelmet(helmet);
                                        p.getEquipment().setChestplate(chest);
                                        p.getEquipment().setLeggings(pants);
                                        p.getEquipment().setBoots(boots);

                                    }

                                }

                            }

                            new Nexus(plugin, effectManager).spawnNexusSlimes(villager.getWorld());

                            Main.gamePlayers.forEach(gamePlayer -> Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&6You are on the &" + (gamePlayer.getTeam().getName().equalsIgnoreCase("red") ? "c&lRed" : "9&lBlue") + " &6team!")));

                            task = null;
                            cancel();

                        }

                    }
                    j[0]++;

                }

            }.runTaskTimer(plugin,0, 20);


    }

    private void fillArea(Block block, Material type) {
        if (block.getType() == Material.AIR){
            return;
        }
        if (block.getType() != type) {
            return;
        }

        BlockState state = block.getState();
        state.setType(Material.AIR);
        state.setData(new MaterialData(Material.AIR));
        state.update(true, false);

        for (Block other : getSurroundingBlocks(block)) {

            fillArea(other, type);

        }

    }

    private void assignRandomChampions() {

        for (Team team : Main.teams) {

            if (GameMode.getMode() != GameMode._1V1) {

                ArrayList<GamePlayer> unassigned = new ArrayList<>();

                StringBuilder championsTakenBuilder = new StringBuilder();
                for (GamePlayer player : Main.gamePlayers) {

                    for (Object uuid : team) {

                        if (uuid == player.getUuid()) {

                            if (player.getChampion() == null) {

                                unassigned.add(player);

                            } else {

                                championsTakenBuilder.append(player.getChampion().getName()).append(",");

                            }

                        }

                    }

                }
                String championsTaken = championsTakenBuilder.toString();

                if (championsTaken.length() > 0)
                    championsTaken = championsTaken.substring(0, championsTaken.length() - 2);
                ArrayList<Champion> championsLeft = new ArrayList<>();
                Collections.addAll(championsLeft, Main.champions);

                for (Iterator<Champion> iterator = championsLeft.iterator(); iterator.hasNext(); ) {
                    Champion champion = iterator.next();

                    for (String name : championsTaken.split(",")) {

                        if (champion.getName().equalsIgnoreCase(name))
                            iterator.remove();

                    }

                }

                for (GamePlayer gamePlayer : unassigned) {

                    for (Iterator<Champion> iterator = championsLeft.iterator(); iterator.hasNext(); ) {
                        Champion champion = iterator.next();

                        gamePlayer.setChampion(champion);
                        iterator.remove();

                    }

                }

            } else {

                GamePlayer gamePlayer = null;

                for (GamePlayer player : Main.gamePlayers) {

                    if (team.get(0) == player.getUuid()) {

                        gamePlayer = player;
                        break;

                    }

                }

                if (gamePlayer.getChampion() == null) {

                    Random rnd = new Random();
                    int i = rnd.nextInt(Main.champions.length);
                    Champion champion = Main.champions[i];
                    gamePlayer.setChampion(champion);

                }

            }

        }

    }

    public void giveInventory() {

        for (GamePlayer gamePlayer : Main.gamePlayers) {

            Inventory inv = Bukkit.getPlayer(gamePlayer.getUuid()).getInventory();
            ItemStack defaultWeapon = null;
            ItemStack abilityOne = null;
            ItemStack abilityTwo = null;
            ItemStack abilityThree = null;
            ItemStack ultimate = null;
            ItemStack passive = null;
            ItemStack mount = new ItemStack(Material.IRON_BARDING, 1);
            ItemStack purchasableItem = new ItemStack(Material.SEEDS, 1);

            if (gamePlayer.getChampion().getName().equalsIgnoreCase("asger")) { //ASGER

                defaultWeapon = new ItemStack(Material.IRON_AXE, 1);
                abilityOne = createAbility("One", "Insanity Aura", "Asger unleashes the flaming rage within him, damaging anyone within a 5 block radius around him.", stat("Damage", "30"), stat("Cost", "0 Mana"), stat("Cooldown", "9"), stat("Scaling", "30% of your Attack Damage"));
                abilityTwo = createAbility("Two", "Slaughterfest", "Basic attacking enemies gives them stacks of Slaughter, capping at 3. After a target has reached 3 stacks, you may activate this ability to dash at them and knock them up.", stat("Dash Damage", "30 Damage"), stat("Cost", "0 Mana"), stat("Cooldown", "13 Seconds"), stat("Scaling", "50% of your Attack Damage"));
                abilityThree = createAbility("Three", "Blood Spikes", "Enchants the ground around you, making spikes rise up in a 5 block radius around you. Anyone who is hit by the spikes bleeds for a short period of time.", stat("Bleed Damage", "5 Damage/Second"), stat("Bleed Duration", "4 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "7 Seconds"), stat("Scaling", "50% of your Attack Damage"));
                ultimate = createUltimate("Limitless Anger", "Upon activation, player in sight will be targeted. After your target has been selected, your attacks and abilities deal double damage against your target, and you are immune to damage from any source except your target.", stat("Duration", "6 Seconds"));
                passive = createPassive("RAGE!!!", "When below &e10% &7health, you regenerate &e40% &7of your maximum health over &e35 &7seconds.");

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("xiau tsun")) {

                defaultWeapon = new ItemStack(Material.IRON_SWORD);
                abilityOne = createAbility("One", "Assassination", "When you're within 10 blocks of an enemy player activate this ability to instantly teleport behind them, stabbing them in the back. This ability will prioritize the closest enemy player.", stat("Damage", "40 Damage"), stat("Cost", "80 Mana"), stat("Cooldown", "12 Seconds"), stat("Scaling", "20% of your Attack Damage"));
                abilityTwo = createAbility("Two", "Runic Daggers", "When using basic attacks, each attack will grant you a special power, stacking up to 5 times. If you do not attack anyone within 7 seconds, all of your stacks will disappear.|&eLvL. 1 - &7Backstab: Basic attacks deal + 15 damage when you attack enemies from behind.|&eLvL. 2 - &7Demoralize: Every basic attack applies a Slow I debuff for 4 seconds.|&eLvL. 3 - &7Reaper's Kiss: Every basic attack heals you for 20 Health.|&eLvL. 4 - &7Forcefulness: Every basic attack will deal 15% more damage.|&eLvL. 5 - Decapitation: Attacking an enemy will immobilize them for 2 seconds, resetting the cycle of Runic Daggers.", stat("Cost", "0 Mana"), stat("Cooldown", "None (Permanently activated ability)"));
                abilityThree = createAbility("Three", "Ambush", "Upon activation, stealths you for a short time. When the stealth period is over, you will automatically jump at the location you're looking at 5 blocks away from you.", stat("Stealth Duration", "3 Seconds"), stat("Cost", "60 Mana"), stat("Cooldown", "18 Seconds"));
                ultimate = createUltimate("Final Calling", "Xiau Tsun summons his inner-Otium to rise in the air and strike the nearest enemy player dealing great damage.", stat("Damage", "80"), stat("Cost", "100 Mana"), stat("Cooldown", "100 Seconds"), stat("Scaling", "100% of your Attack Damage"));
                passive = createPassive("Adrenaline", "On kill, assist or within stealth mode, gain a Speed III buff for 7 Seconds.");

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("kaine")) {

                defaultWeapon = new ItemStack(Material.DIAMOND_HOE);
                abilityOne = createAbility("One", "Electroshock", "Activating this ability sends forth a shockwave to your weapon for the next 5 seconds. If within that period you attack an enemy, it will deal damage to them. If there is an enemy unit within 5 blocks of your target, the shock will transfer to them.", stat("Damage", "30 Damage"), stat("Cost", "55 Mana"), stat("Cooldown", "8 Seconds"), stat("Scaling", "50% of your Ability Power"));
                abilityTwo = createAbility("Two", "Devastating Sparks", "Using this ability strikes lightning upon all enemy units within a 5x5 block area around you, damaging them and applying a Slow II Debuff.", stat("Damage", "20 Damage"), stat("Cost", "35 Mana"), stat("Cooldown", "4 Seconds"), stat("Scaling", "40% of your Ability Power"), stat("Slow II Debuff Duration", "2 Seconds"));
                abilityThree = createAbility("Three", "Rain Dance", "Creates a gray cloud above you, causing rain and thunder to strike from it in a 5x5 area around you for 4 seconds. Allies that stay inside this area are healed every second, and enemies that stay in this area are damaged every second.", stat("Damage", "10 Damage/Second"), stat("Heal", "25 Health/Second"), stat("Cost", "75 Mana"), stat("Cooldown", "12 Seconds"));
                ultimate = createUltimate("Static Field", "Once activated, makes a 8x8 area around you a static field, damaging everyone and giving Slow III to whoever is in it.", stat("Damage", "30 Damage/Second"), stat("Cost", "125 Mana"), stat("Cooldown", "72 Seconds"), stat("Static Field Duration", "5 Seconds"));
                passive = createPassive("Hypercharge", "After you've used 3 abilities, your next one is guaranteed to deal double damage and apply a 3 second stun.");

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("corelia")) {

                defaultWeapon = new ItemStack(Material.WOOD_HOE);
                abilityOne = createAbility("One", "Nuke 'Em!", "&7Fires a TNT Block 4 blocks forward which ignites mid air. Once the TNT block lands on the group, it instantly explodes damaging and knocking back anyone it hits.", stat("Damage", "15"), stat("Cost", "0 Mana"), stat("Cooldown", "9 Seconds"), stat("Scaling", "60% of your Attack Damage"));
                abilityTwo = createAbility("Two", "Blast Zone", "&7Makes a 7x7 area around a blast zone for a short period of time. While in the blast zone, your &eNuke 'Em! &7ability cooldown is deduced to 1 second.", stat("Blast Zone duration", "7 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "16 Seconds"), stat("Scaling", "None"));
                abilityThree = createAbility("Three", "Stupefy", "&7Summons an explosive at your feet and explodes creating a shockwave slowing (Slow II) enemies within 7 blocks of the origin.", stat("Damage", "0"), stat("Slowness II", "4 Seconds"), stat("Cost", "0 Mana"), stat("Cooldown", "9 Seconds"), stat("Scaling", "None"));
                ultimate = createUltimate("KABOOM!", "Target one player to throw a homing TNT missile at. This missile deals massive damage and knocks them back a very large distance.", stat("Damage", "90 Damage"), stat("Knockback", "15 Blocks"), stat("Cost", "0 Mana"), stat("Cooldown", "80 Seconds"));
                passive = createPassive("Need More Firepower!", "&7Using &e3 &7abilities within &e12 &7seconds increases your basic attack and ability damages by &e100% &7for &e8 &7seconds.");

            } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("xerol")) {
                defaultWeapon = new ItemStack(Material.GOLD_AXE);
                passive = createPassive("Grasp of the Undying", "All damage dealt against enemies restores health to every ally within 8 blocks of your enemy. Health restored is equal to 30% of damage dealt to the enemy.");
                abilityOne = createAbility("One", "Spawn: Grave", "Spawns a grave 3 blocks in front of you. Graves will heal you or an ally if they are walked over.", stat("Heal", "15 Health"), stat("Cost", "30 Mana"), stat("Cooldown", "5 Seconds"));
                abilityTwo = createAbility("Two", "Eternal Hunger", "Your next basic attack turns into a bite which heals you 5% of your enemy's maximum health. Eternal Hunger also makes your enemy bleed for a short time for the amount of health you've healed for.", stat("Heal", "10% of Enemy Maximum Health"), stat("Bleed Damage", "3% of Enemy Maximum Health/Second"), stat("Bleed Duration", "5 Seconds"), stat("Cost", "50 Mana"), stat("Cooldown", "7 Seconds"), stat("Scaling", "None"));
                abilityThree = createAbility("Three", "Undead Rising", "Throws a magical bolt 5 blocks forward. If this bolt hits an enemy, 5 zombies will spawn around them and will start attacking them. Zombies that die turn into graves which your allies can over to be healed. Zombies die by themselves if they are not killed within 6 seconds.", stat("Zombie Damage Per Hit", "10 Damage"), stat("Zombie Health", "30 Health"), stat("Grave Heals", "15 Health"), stat("Cost", "90 Mana"), stat("Cooldown", "13 Seconds"), stat("Scaling", "None"));
                ultimate = createUltimate("Resurrection", "Cast this ability on an allied champion to give them the 'Resurrection' buff. When a player with this buff dies, it will rise from the dead as a zombie with all of their stats and abilities for 16 seconds. Once this time runes out, the player is automatically executed and their respawn timer starts. If the player with the 'Resurrection' buff does not die within 6 seconds of ability being cast on them, they will not be resurrected upon death.", stat("Cost", "150 Mana"), stat("Cooldown", "90 Seconds"), stat("Scaling", "None"));

            }

            PlayerData pd = Database.getPlayerByUUID(gamePlayer.getUuid());
            if (pd.getActiveWeapon() != null) {
                Weapons weapon = pd.getActiveWeapon();
                defaultWeapon = new ItemStack(Material.valueOf(weapon.getMaterial()), 1, ((short) weapon.getB()));
            }


            ItemMeta defaultMeta;

            if (defaultWeapon != null) {

                defaultMeta = defaultWeapon.getItemMeta();

                net.minecraft.server.v1_9_R2.ItemStack is = CraftItemStack.asNMSCopy(defaultWeapon);

                NBTTagCompound tag = new NBTTagCompound();
                tag.setBoolean("Unbreakable", true);

                is.setTag(tag);

                defaultWeapon = CraftItemStack.asCraftMirror(is);

                NBTTagCompound tag = is.hasTag() ? is.getTag() : new NBTTagCompound();
                tag.setInt("Unbreakable", 1);
                is.setTag(tag);

                defaultWeapon = CraftItemStack.asBukkitCopy(is);


                defaultMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                defaultMeta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                float cooldown = 0;
                if (gamePlayer.getChampion().getName().equalsIgnoreCase("Asger")) {

                    defaultMeta.setDisplayName(TextUtils.formatText("&7Basic Attack"));
                    cooldown = 0.9F;

                } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xiau Tsun")) {

                    defaultMeta.setDisplayName(TextUtils.formatText("&7Basic Attack"));
                    cooldown = 1F;

                } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Kaine")) {

                    defaultMeta.setDisplayName(TextUtils.formatText("&7Basic Attack (right-click)"));
                    cooldown = 1.15F;

                } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Corelia")) {

                    defaultMeta.setDisplayName(TextUtils.formatText("&7Basic Attack (right-click)"));
                    cooldown = 0.9F;

                } else if (gamePlayer.getChampion().getName().equalsIgnoreCase("Xerol")) {

                    defaultMeta.setDisplayName(TextUtils.formatText("&7Basic Attack"));
                    cooldown = 1.2F;

                }

                List<String> lore = new ArrayList<>();
                lore.add(TextUtils.formatText("&e---"));
                lore.add(TextUtils.formatText("&7Damage: &e" + (gamePlayer.getChampion().getAttackDamage() == 0 ? gamePlayer.getChampion().getAbilityPower() : gamePlayer.getChampion().getAttackDamage())));
                lore.add(TextUtils.formatText("&7Cooldown: &e" + cooldown + "/s"));
                lore.add(TextUtils.formatText("&e---"));
                defaultMeta.setLore(lore);
                defaultWeapon.setItemMeta(defaultMeta);
            }

            ItemMeta purchasableMeta = purchasableItem.getItemMeta();
            purchasableMeta.setDisplayName(TextUtils.formatText("&a&lVisit the merchant to buy items!"));
            purchasableItem.setItemMeta(purchasableMeta);

            ItemStack kills = new ItemStack(Material.IRON_AXE);
            ItemMeta killsMeta = kills.getItemMeta();
            killsMeta.setDisplayName(TextUtils.formatText("&6&lKills: " + gamePlayer.getKills()));
            killsMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            kills.setItemMeta(killsMeta);

            ItemStack assists = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            ItemMeta assistsMeta = assists.getItemMeta();
            assistsMeta.setDisplayName(TextUtils.formatText("&6&lAssists: " + gamePlayer.getAssists()));
            assists.setItemMeta(assistsMeta);

            ItemStack deaths = new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
            ItemMeta deathsMeta = assists.getItemMeta();
            deathsMeta.setDisplayName(TextUtils.formatText("&6&lDeaths: " + gamePlayer.getDeaths()));
            deaths.setItemMeta(deathsMeta);

            ItemStack divider = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
            ItemMeta dividerMeta = divider.getItemMeta();
            dividerMeta.setDisplayName(" ");
            divider.setItemMeta(dividerMeta);

            ItemStack hearthstone = new ItemStack(Material.GLOWSTONE_DUST, 1);
            ItemMeta hearthstoneMeta = hearthstone.getItemMeta();
            hearthstoneMeta.setDisplayName(TextUtils.formatText("&e&lReturn to spawn."));
            hearthstone.setItemMeta(hearthstoneMeta);

            inv.setItem(0, defaultWeapon);
            inv.setItem(2, abilityOne);
            inv.setItem(3, abilityTwo);
            inv.setItem(4, abilityThree);
            inv.setItem(5, ultimate);
            inv.setItem(6, passive);
            inv.setItem(8, mount);
            inv.setItem(9, purchasableItem);
            inv.setItem(10, purchasableItem);
            inv.setItem(11, purchasableItem);
            inv.setItem(12, divider);
            inv.setItem(18, purchasableItem);
            inv.setItem(19, purchasableItem);
            inv.setItem(20, purchasableItem);
            inv.setItem(21, divider);
            inv.setItem(13, kills);
            inv.setItem(15, assists);
            inv.setItem(17, deaths);
            inv.setItem(30, divider);
            inv.setItem(24, hearthstone);

        }

    }

    private ItemStack createUltimate(String name, String desc, String... stats) {
        return createItem(Material.SEEDS, 0, "&7Ultimate Ability - &e&l" + name, TextUtils.addLinebreaks(desc), stats);
    }

    private String stat(String name, String statistic) {
        return "&7" + name + " &7- &e" + statistic;
    }

    private ItemStack createPassive(String name, String desc) {
        return createItem(Material.BOWL, 0, "&7Passive Ability - &e" + name, TextUtils.addLinebreaks(desc));
    }

    ItemStack createAbility(String num, String name, String desc, String... stats) {
        Material mat = Material.BOWL;
        if (num.equalsIgnoreCase("one")) {
            mat = Material.CLAY_BRICK;
        } else if (num.equalsIgnoreCase("two")) {
            mat = Material.SEEDS;
        } else if (num.equalsIgnoreCase("three")) {
            mat = Material.SEEDS;
        }
        return createItem(mat, 0, "&7Ability " + num + " - &e" + name, TextUtils.addLinebreaks(desc), stats);
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

    public BukkitTask getTask() {
        return task;
    }
}
