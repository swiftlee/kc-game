package net.jmdev;

import com.google.common.io.ByteStreams;

import de.slikey.effectlib.EffectManager;

import net.jmdev.champions.Asger;
import net.jmdev.champions.Corelia;
import net.jmdev.champions.Kaine;
import net.jmdev.champions.Xerol;
import net.jmdev.champions.XiauTsun;
import net.jmdev.commands.EndGameCommand;
import net.jmdev.commands.ForceStartCommand;
import net.jmdev.commands.SetLevelCommand;
import net.jmdev.entity.*;
import net.jmdev.game.Champion;
import net.jmdev.game.GameMode;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.GameState;
import net.jmdev.game.ShopItemHandler;
import net.jmdev.game.Team;
import net.jmdev.gui.Item;
import net.jmdev.listener.AsyncPlayerChatListener;
import net.jmdev.listener.HealthChangeListener;
import net.jmdev.listener.InventoryClickListener;
import net.jmdev.listener.InventoryCloseListener;
import net.jmdev.listener.PlayerInteractListener;
import net.jmdev.listener.PlayerJoinListener;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/06/2017 | 21:33
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
public class Main extends JavaPlugin {

    public static ArrayList<Team> teams;
    public static ArrayList<GamePlayer> gamePlayers;
    public static Champion[] champions = {new Asger(), new Corelia(), new Kaine(), new Xerol(), new XiauTsun()};
    private static Permission perms = null;
    private static Chat chat = null;
    public java.util.Map<String, Item> items = new HashMap<>();
    private ShopItemHandler shopItemHandler = null;

    public static Chat getChat() {
        return chat;
    }

    @Override
    public void onEnable() {

        GameState.setGameState(GameState.WAITING);
        teams = new ArrayList<>();
        gamePlayers = new ArrayList<>();
        shopItemHandler = new ShopItemHandler(this);

        loadResource("config.yml");

        EffectManager effectManager = new EffectManager(this);

        switch (getConfig().getString("onEnoughPlayers.gameModeType")) {
            case "1v1":
                GameMode.setMode(GameMode._1V1);
                teams.add(new Team("Red"));
                teams.add(new Team("Blue"));
                break;
            case "3v3":
                GameMode.setMode(GameMode._3V3);
                teams.add(new Team("Red"));
                teams.add(new Team("Blue"));
                Bukkit.getWorld("3v3").setAutoSave(false);
                break;
            case "5v5":
                GameMode.setMode(GameMode._5V5);
                teams.add(new Team("Red"));
                teams.add(new Team("Blue"));
                break;
        }

        getCommand("forcestart").setExecutor(new ForceStartCommand(this, effectManager));
        getCommand("chop").setExecutor(new ForceStartCommand(this, effectManager));
        getCommand("dance").setExecutor(new ForceStartCommand(this, effectManager));
        getCommand("setlevel").setExecutor(new SetLevelCommand());
        getCommand("endgame").setExecutor(new EndGameCommand(this));
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this, shopItemHandler), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, effectManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(effectManager, this), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new HealthChangeListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        List<String> names = new ArrayList<>();
        names.add("&eBattle Axe");
        names.add("&eBook of Power");
        names.add("&eTraining Sword");
        names.add("&eShining Stone");
        names.add("&eEnergy Soul");
        names.add("&eCloak & Dagger");
        names.add("&eHealth Potion");
        names.add("&ePotion of Mist");
        names.add("&ePotion of Agility");
        names.add("&ePotion of Resistance");
        names.add("&ePotion of Power");
        names.add("&eIron Broadsword");
        names.add("&eBloodborn Hatchet");
        names.add("&eSealed Pendant");
        names.add("&ePassion's Embrace");
        names.add("&eCowl of the Assassin");
        names.add("&eMaterial Spirit");
        names.add("&eBlade of Iron Will");
        names.add("&eIllusionist's Gem");
        names.add("&ePandora's Stormbow");
        names.add("&eSoulstealer");
        names.add("&eReflecting Shield");
        names.add("&eVow of the Protector");
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "items.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
            for (String name : names) {
                Item item = new Item(name);
                ConfigurationSection cs = yc.createSection(name);
                for (Field field : Item.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        cs.set(field.getName(), field.get(item));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                items.put(name, item);
            }
            try {
                yc.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
            for (String name : yc.getKeys(false)) {
                Item item = new Item(name);
                ConfigurationSection cs = yc.getConfigurationSection(name);
                for (Field field : Item.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (field.isEnumConstant()) {
                            field.set(item, cs.get(field.getName()));
                        } else {
                            if (field.get(item) instanceof Byte)
                                field.set(item, (byte) cs.getInt(field.getName()));
                            else if (field.get(item) instanceof Integer)
                                field.set(item, cs.getInt(field.getName()));
                            else if (field.get(item) instanceof Double)
                                field.set(item, cs.getDouble(field.getName()));
                            else if (field.get(item) instanceof Float)
                                field.set(item, (float) cs.getInt(field.getName()));
                            else if (field.get(item) instanceof String)
                                field.set(item, cs.getString(field.getName()));
                            else
                                field.set(item, cs.getList(field.getName()));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                items.put(name, item);

            }
        }

        setupChat();
        setupPermissions();
        CustomEntities.registerEntity("Basic Minion", 54, BasicMinion.class);
        CustomEntities.registerEntity("Ranged Minion", 54, RangedMinion.class);
        CustomEntities.registerEntity("Siege Minion", 54, SiegeMinion.class);
        CustomEntities.registerEntity("Super Minion", 54, SuperMinion.class);
        CustomEntities.registerEntity("Turret", 55, HitBox.class);
        CustomEntities.registerEntity("Rideable Entity", 30, RideableEntity.class);
        CustomEntities.registerEntity("Rideable Donkey", 100, DonkeyRideable.class);
        CustomEntities.registerEntity("Mystical Golem", 99, MysticalGolem.class);

    }

    private File loadResource(String resource) {

        File folder = this.getDataFolder();

        if (!folder.exists())
            folder.mkdir();

        File resourceFile = new File(folder, resource);

        try {

            if (!resourceFile.exists()) {

                resourceFile.createNewFile();

                try (InputStream in = this.getResource(resource); OutputStream out = new FileOutputStream(resourceFile)) {

                    ByteStreams.copy(in, out);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return resourceFile;
    }

    private boolean setupPermissions() {

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;

    }

    private boolean setupChat() {

        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;

    }

}
