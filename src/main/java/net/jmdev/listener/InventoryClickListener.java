package net.jmdev.listener;

import net.jmdev.Main;
import net.jmdev.game.Champion;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.GameState;
import net.jmdev.game.ShopItemHandler;
import net.jmdev.gui.ChampionGUI;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/13/2017 | 15:09
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
@SuppressWarnings("deprecation")
public class InventoryClickListener implements Listener {

    private Main plugin;
    private ShopItemHandler itemHandler;

    public InventoryClickListener(Main plugin, ShopItemHandler itemHandler) {

        this.plugin = plugin;
        this.itemHandler = itemHandler;

    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent e) {

        if (e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.CREEPER || e.getEntityType() == EntityType.SKELETON || e.getEntityType() == EntityType.SPIDER) {

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        Iterator<ItemStack> iterator = e.getDrops().iterator();

        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            iterator.remove();
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getInventory() == null || e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getRawSlot() != e.getSlot()) {
            System.out.println("11111111111");
            e.setCancelled(true);
            return;
        }

        System.out.println("222222222222");

        GamePlayer gamePlayer = GeneralUtils.getGamePlayer(e.getWhoClicked().getUniqueId());

        if (GameState.getGameState() == GameState.CHAMPION_SELECT) {

            if (e.getInventory().getName().equalsIgnoreCase("Champion Selector")) {

                String name = e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() ? e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0].replace("§e§l", "") : null;

                if (name == null) {
                    e.setCancelled(true);
                    return;
                }

                if (name.equalsIgnoreCase("Asger")) {

                    new ChampionGUI("Asger", gamePlayer).open((Player) e.getWhoClicked());

                } else if (name.equalsIgnoreCase("Corelia")) {

                    new ChampionGUI("Corelia", gamePlayer).open((Player) e.getWhoClicked());
                    //e.getWhoClicked().sendMessage(TextUtils.formatText("&cThat champion is not available yet!"));

                } else if (name.equalsIgnoreCase("Kaine")) {

                    new ChampionGUI("Kaine", gamePlayer).open((Player) e.getWhoClicked());

                } else if (name.equalsIgnoreCase("Xerol")) {

                    new ChampionGUI("Xerol", gamePlayer).open((Player) e.getWhoClicked());
                    //e.getWhoClicked().sendMessage(TextUtils.formatText("&cThat champion is not available yet!"));

                } else if (name.equalsIgnoreCase("Xiau")) {

                    new ChampionGUI("Xiau Tsun", gamePlayer).open((Player) e.getWhoClicked());

                }

            } else if (e.getInventory().getName().matches("(?i)^(asger|corelia|kaine|xerol|xiau tsun)$")) {

                if (e.getCurrentItem().getType() == Material.ARROW) {

                    new ChampionGUI().open((Player) e.getWhoClicked());

                } else if (e.getCurrentItem().getData().getData() == 8) {

                    Champion champion = null;

                    for (Champion c : Main.champions) {

                        if (c.getName().equalsIgnoreCase(e.getInventory().getName())) {

                            champion = c;
                            break;

                        }

                    }

                    boolean championTaken = false;

                    for (Object uuid : gamePlayer.getTeam()) {

                        for (GamePlayer player : Main.gamePlayers) {

                            if (uuid.equals(player.getUuid())) {

                                if (player.getChampion() != null) {

                                    if (player.getChampion().getName().equalsIgnoreCase(champion.getName())) {
                                        championTaken = true;
                                        break;
                                    }

                                }

                            }

                        }

                    }

                    ItemStack championStatusStack;

                    if (championTaken) {

                        championStatusStack = new ItemStack(Material.BARRIER, 1);
                        ItemMeta championStatusMeta = championStatusStack.getItemMeta();
                        championStatusMeta.setDisplayName(TextUtils.formatText("&cSomeone else has already claimed this champion!"));
                        championStatusStack.setItemMeta(championStatusMeta);
                        e.getWhoClicked().sendMessage(TextUtils.formatText("&cSorry, this champion has been taken! Please refresh this menu if you wish to see if it is available."));

                    } else {

                        championStatusStack = new ItemStack(Material.INK_SACK, 1, (byte) 10);
                        ItemMeta championStatusMeta = championStatusStack.getItemMeta();
                        championStatusMeta.setDisplayName(TextUtils.formatText("&aYou have chosen this champion!"));
                        championStatusStack.setItemMeta(championStatusMeta);
                        gamePlayer.setChampion(champion);

                    }

                    e.getInventory().setItem(49, championStatusStack);

                } else if (e.getCurrentItem().getData().getData() == 10) {

                    ItemStack championStatusStack = new ItemStack(Material.INK_SACK, 1, (byte) 8);
                    ItemMeta championStatusMeta = championStatusStack.getItemMeta();
                    championStatusMeta.setDisplayName(TextUtils.formatText("&eThis champion has not been claimed!"));
                    championStatusStack.setItemMeta(championStatusMeta);
                    gamePlayer.setChampion(null);

                    e.getInventory().setItem(49, championStatusStack);


                }

            }

        } else if (GameState.getGameState() == GameState.STARTED) {

            System.out.println("pass");

            if (e.getInventory().getName().substring(2, e.getInventory().getName().length()).equalsIgnoreCase("Merchant")) {

                List<String> lore = e.getCurrentItem().getItemMeta().getLore();

                if (lore == null) {
                    e.setCancelled(true);
                    return;
                }

                for (String s : lore) {

                    if (s.substring(2, s.length()).matches("(?i)^Cost.*$")) {

                        String gold = s.split("-")[1].replace(" ", "").replace("§e", "").replace("Gold", "").replaceAll("§7.*", "");
                        int value = Integer.valueOf(gold);

                        String name = e.getCurrentItem().getItemMeta().getDisplayName();
                        value = getNewPrice(gamePlayer, name.substring(2, name.length()), value, gamePlayer.getGold());
                        boolean maxItems = true;
                        if (gamePlayer.getGold() >= value) {

                            int i = 9;

                            while (i <= 20) {

                                if (e.getWhoClicked().getInventory().getItem(i).getType() == Material.SEEDS) {

                                    ItemStack stack = e.getCurrentItem().clone();
                                    e.getWhoClicked().getInventory().setItem(i, stack);
                                    e.getWhoClicked().sendMessage(TextUtils.formatText("&aYou bought the &7[" + name + "&7]&a!\n&7» &c-" + value + "&e gold"));
                                    if (i <= 11)
                                        gamePlayer.shopItems[i - 9] = name.substring(2, name.length());
                                    else
                                        gamePlayer.shopItems[(i - 18) << 1] = name.substring(2, name.length());

                                    itemHandler.buyEffect(gamePlayer, name.substring(2, name.length()));

                                    gamePlayer.setGold(gamePlayer.getGold() - value);
                                    maxItems = false;
                                    break;

                                }

                                if (i == 11)
                                    i += 7;
                                else
                                    i += 1;

                            }

                            if (maxItems) {

                                e.getWhoClicked().closeInventory();
                                e.getWhoClicked().sendMessage(TextUtils.formatText("&cYou have already bought the maximum amount of items!"));

                            }

                        } else {

                            e.getWhoClicked().sendMessage(TextUtils.formatText("&bYou need &e" + (value - gamePlayer.getGold()) + " &bmore gold for that!"));

                        }

                    }

                }

            } else if (e.getCurrentItem().getType() == Material.GLOWSTONE_DUST) {

                if ((gamePlayer.hearthstone == 0 || (gamePlayer.hearthstone + 60000) <= System.currentTimeMillis()) && gamePlayer.hearthstoneTask == null) {
                    e.getWhoClicked().sendMessage(TextUtils.formatText("&7Returning to base in 5 seconds..."));
                    gamePlayer.hearthstoneTask = new BukkitRunnable() {

                        @Override
                        public void run() {
                            e.getWhoClicked().sendMessage(TextUtils.formatText("&7Returning to base..."));
                            if (gamePlayer.getTeam().getName().equalsIgnoreCase("red")) {

                                e.getWhoClicked().teleport(new Location(Bukkit.getWorld("3v3"), 943.5, 21, 37.5, 90, 0));
                                gamePlayer.hearthstone = System.currentTimeMillis();

                            } else {

                                e.getWhoClicked().teleport(new Location(Bukkit.getWorld("3v3"), 617.5, 21, 37.5, -90, 0));
                                gamePlayer.hearthstone = System.currentTimeMillis();

                            }

                            gamePlayer.hearthstoneTask = null;

                        }

                    }.runTaskLater(plugin, 20 * 5);

                } else {

                    e.getWhoClicked().sendMessage(TextUtils.formatText("&7" + (((gamePlayer.hearthstone + 60000) / 1000) - (System.currentTimeMillis() / 1000)) + " &cseconds left."));

                }

            } else if (e.getCurrentItem().getType() == Material.BOOK) {

                String name = e.getCurrentItem().getItemMeta().getDisplayName().substring(2, e.getCurrentItem().getItemMeta().getDisplayName().length());
                itemHandler.doClickEffect(gamePlayer, name, e.getSlot());

            }

        }

        e.setCancelled(true);

    }

    private int itemCount(GamePlayer gamePlayer, String name, int limit) {

        int count = 0;

        for (String s : gamePlayer.shopItems) {

            System.out.println(s);

            if (s.equalsIgnoreCase(name))
                count++;

            if (count == limit)
                break;

        }

        return count;

    }

    private void replaceItem(GamePlayer gamePlayer, String name, int limit) {

        ItemStack purchasableItem = new ItemStack(Material.SEEDS, 1);
        ItemMeta purchasableMeta = purchasableItem.getItemMeta();
        purchasableMeta.setDisplayName(TextUtils.formatText("&a&lVisit the merchant to buy items!"));
        purchasableItem.setItemMeta(purchasableMeta);
        Player p = Bukkit.getPlayer(gamePlayer.getUuid());

        int i = 9;
        int count = 0;

        while (i <= 20) {

            String itemName = p.getInventory().getItem(i).getItemMeta().getDisplayName();
            itemName = itemName.substring(2, itemName.length());

            if (itemName.equalsIgnoreCase(name)) {
                p.getInventory().setItem(i, purchasableItem);
                if (i <= 11)
                    gamePlayer.shopItems[i - 9] = "";
                else
                    gamePlayer.shopItems[(i - 18) << 1] = "";

                itemHandler.replaceEffect(gamePlayer, name);

                count++;
            }

            if (count == limit)
                break;

            if (i == 11)
                i += 7;
            else
                i += 1;

        }

    }

    private int getNewPrice(GamePlayer p, String name, int oldPrice, int playerGold) {

        if (name.equalsIgnoreCase("Iron Broadsword")) {

            int count = itemCount(p, "Training Sword", 2);
            int discount = count * 350;

            if (playerGold >= (oldPrice - discount)) {

                if (count > 0)
                    replaceItem(p, "Training Sword", count);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Bloodborn Hatchet")) {

            int countTraining = itemCount(p, "Training Sword", 1);
            int countAxe = itemCount(p, "Battle Axe", 1);
            int discount = ((countTraining) + (countAxe)) * 350;

            if (playerGold >= (oldPrice - discount)) {

                if (countTraining > 0)
                    replaceItem(p, "Training Sword", countTraining);
                if (countAxe > 0)
                    replaceItem(p, "Battle Axe", countAxe);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Sealed Pendant")) {

            int countBook = itemCount(p, "Book of Power", 2);
            int countEnergySoul = itemCount(p, "Energy Soul", 1);
            int discount = (countBook + countEnergySoul) * 300;

            if (playerGold >= (oldPrice - discount)) {

                if (countBook > 0)
                    replaceItem(p, "Book of Power", countBook);
                if (countEnergySoul > 0)
                    replaceItem(p, "Energy Soul", countEnergySoul);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Passion's Embrace")) {

            int countStone = itemCount(p, "Shining Stone", 2);
            int discount = countStone * 300;

            if (playerGold >= (oldPrice - discount)) {

                if (countStone > 0)
                    replaceItem(p, "Shining Stone", countStone);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Cowl of The Assassin")) {

            int countCloak = itemCount(p, "Cloak & Dagger", 2);
            int discount = countCloak * 500;

            if (playerGold >= (oldPrice - discount)) {

                if (countCloak > 0)
                    replaceItem(p, "Cloak & Dagger", countCloak);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Material Spirit")) {

            int countEnergySoul = itemCount(p, "Energy Soul", 2);
            int discount = countEnergySoul * 300;

            if (playerGold >= (oldPrice - discount)) {

                if (countEnergySoul > 0)
                    replaceItem(p, "Energy Soul", countEnergySoul);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Blade of Iron Will")) {

            int countBroadsword = itemCount(p, "Iron Broadsword", 2);
            int countStone = itemCount(p, "Shining Stone", 1);
            int discount = (countBroadsword * 850) + (countStone * 300);

            if (playerGold >= (oldPrice - discount)) {

                if (countBroadsword > 0)
                    replaceItem(p, "Iron Broadsword", countBroadsword);
                if (countStone > 0)
                    replaceItem(p, "Shining Stone", countStone);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Illusionist's Gem")) {

            int countPendant = itemCount(p, "Sealed Pendant", 2);
            int discount = countPendant * 1200;

            if (playerGold >= (oldPrice - discount)) {

                if (countPendant > 0)
                    replaceItem(p, "Sealed Pendant", countPendant);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Pandora's Stormbow")) {

            int countCowl = itemCount(p, "Cowl of The Assassin", 1);
            int countCloak = itemCount(p, "Cloak & Dagger", 1);
            int discount = (countCowl * 1200) + (countCloak * 500);

            if (playerGold >= (oldPrice - discount)) {

                if (countCowl > 0)
                    replaceItem(p, "Cowl of The Assassin", countCowl);
                if (countCloak > 0)
                    replaceItem(p, "Cloak & Dagger", countCloak);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Soulstealer")) {

            int countAxe = itemCount(p, "Battle Axe", 1);
            int countBloodborn = itemCount(p, "Bloodborn Hatchet", 1);
            int discount = (countAxe * 500) + (countBloodborn * 1000);

            if (playerGold >= (oldPrice - discount)) {

                if (countAxe > 0)
                    replaceItem(p, "Battle Axe", countAxe);
                if (countBloodborn > 0)
                    replaceItem(p, "Bloodborn Hatchet", countBloodborn);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Reflecting Shield")) {

            int countPassion = itemCount(p, "Passion's Embrace", 2);
            int discount = countPassion * 1150;

            if (playerGold >= (oldPrice - discount)) {

                if (countPassion > 0)
                    replaceItem(p, "Passion's Embrace", countPassion);

            }

            return oldPrice - discount;

        } else if (name.equalsIgnoreCase("Vow of The Protector")) {

            int countPendant = itemCount(p, "Sealed Pendant", 2);
            int countBook = itemCount(p, "Book of Power", 1);
            int discount = (countPendant * 1200) + (countBook * 300);

            if (playerGold >= (oldPrice - discount)) {

                if (countPendant > 0)
                    replaceItem(p, "Sealed Pendant", countPendant);
                if (countBook > 0)
                    replaceItem(p, "Book of Power", countBook);

            }

            return oldPrice - discount;

        }

        return oldPrice;

    }

    public Main getPlugin() {
        return plugin;
    }

    public void setPlugin(Main plugin) {
        this.plugin = plugin;
    }
}
