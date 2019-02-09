package net.jmdev.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

<<<<<<< HEAD
import com.mojang.authlib.GameProfile;

=======
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
import net.jmdev.Main;
import net.jmdev.game.GameMode;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.Gold;
import net.jmdev.game.ItemLevelHandler;
import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NBTTagByte;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.minecraft.server.v1_9_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;
import net.minecraft.server.v1_9_R2.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/25/2017 | 13:55
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
public class GeneralUtils {

    private static int entityId = 0;

    public static void levelUp(GamePlayer gamePlayer) {

        if (gamePlayer.getLevel() == 1) {

            if (gamePlayer.getChampion().getExp() >= 150) {

                gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() - 150);
                gamePlayer.setLevel((byte) 2);

                Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&aYou are now level &7&l2&a!"));

            }

        } else if (gamePlayer.getLevel() == 2) {

            if (gamePlayer.getChampion().getExp() >= 182) {

                gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() - 182);
                gamePlayer.setLevel((byte) 3);

                Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&aYou are now level &7&l3&a! You have unlocked your next ability."));
                ItemLevelHandler.giveLevelItem(gamePlayer, (byte) 3);

            }

        } else if (gamePlayer.getLevel() == 3) {

            if (gamePlayer.getChampion().getExp() >= 210) {

                gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() - 210);
                gamePlayer.setLevel((byte) 4);

                Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&aYou are now level &7&l4&a!"));

            }

        } else if (gamePlayer.getLevel() == 4) {

            if (gamePlayer.getChampion().getExp() >= 243) {

                gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() - 243);
                gamePlayer.setLevel((byte) 5);

                Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&aYou are now level &7&l5&a! You have unlocked your next ability."));
                ItemLevelHandler.giveLevelItem(gamePlayer, (byte) 5);

            }

        } else if (gamePlayer.getLevel() == 5) {

            if (gamePlayer.getChampion().getExp() >= 271) {

                gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() - 271);
                gamePlayer.setLevel((byte) 6);

                Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&aYou are now level &7&l6&a!"));

            }

        } else if (gamePlayer.getLevel() == 6) {

            if (gamePlayer.getChampion().getExp() >= 300) {

                gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() - 300);
                gamePlayer.setLevel((byte) 7);

                Bukkit.getPlayer(gamePlayer.getUuid()).sendMessage(TextUtils.formatText("&aYou are now level &7&l7&a! You have unlocked your final ability."));
                ItemLevelHandler.giveLevelItem(gamePlayer, (byte) 7);

            }

        }

    }

    public static void sendPlayerToLobby(Main plugin, Player player, String message) {

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("lobby");
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());

        if (!message.equalsIgnoreCase("")) {
            ByteArrayDataOutput out1 = ByteStreams.newDataOutput();
            out1.writeUTF("Message");
            out1.writeUTF(player.getName());
            out1.writeUTF(TextUtils.formatText(message));
            player.sendPluginMessage(plugin, "BungeeCord", out1.toByteArray());
        }

    }

    public static GamePlayer getGamePlayer(Player player) {
        for (GamePlayer gp : Main.gamePlayers) {
            if (gp.getUuid().equals(player.getUniqueId())) return gp;
        }
        return null;
    }

    public static GamePlayer getGamePlayer(UUID uuid) {

        for (GamePlayer gp : Main.gamePlayers)
            if (gp.getUuid().equals(uuid)) return gp;

        return null;
    }

    public static void cooldown(Main plugin, GamePlayer gamePlayer, int num, int time) {
        ItemStack stack = Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().getItem(num);
        ItemStack cooldown;
        if (num != 5)
            cooldown = new ItemStack(Material.WHEAT);
        else
            cooldown = new ItemStack(Material.SULPHUR);
<<<<<<< HEAD
=======

        ItemMeta cooldownMeta = cooldown.getItemMeta();
        cooldownMeta.setDisplayName(TextUtils.formatText("&cAbility cooldown"));
        cooldown.setItemMeta(cooldownMeta);
        Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(num, cooldown);
        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(num, stack);

            }

        }.runTaskLater(plugin, 20 * time);
    }

>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

        ItemMeta cooldownMeta = cooldown.getItemMeta();
        cooldownMeta.setDisplayName(TextUtils.formatText("&cAbility cooldown"));
        cooldown.setItemMeta(cooldownMeta);
        Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(num, cooldown);
        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getPlayer(gamePlayer.getUuid()).getInventory().setItem(num, stack);

<<<<<<< HEAD
            }

        }.runTaskLater(plugin, 20 * time);
    }

    public static void addPersitence(Entity e) {

        NBTTagCompound tag = new NBTTagCompound(); // Create our tag
        e.c(tag); //Write our tag
        tag.set("PersistenceRequired", new NBTTagByte((byte) 1)); // Set our tag
        e.f(tag); // Add it to villager :)

    }
=======
                if (damaged.resurrection) {
                    damaged.getChampionType().setHealth(damaged.getChampionType().getMaxHealth());
                    damaged.getChampionType().setMana(damaged.getChampionType().getMaxMana());
                    damaged.getChampionType().setHealth((int)(damaged.getChampionType().getMaxHealth() * 0.5));
                    damaged.getChampionType().setMana((int)(damaged.getChampionType().getMaxMana() * 0.5));
                   new Title("", ChatColor.GREEN + "You have been resurrected.").send(Bukkit.getPlayer(damaged.getUuid()));

                    return false;
                }
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    private static void setInvisible(Player player, Main plugin) {

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 2, 1, false, false);
        player.addPotionEffect(potionEffect);
        ItemStack[] armorContents = player.getEquipment().getArmorContents();
        clearArmor(player);

        new BukkitRunnable() {

            @Override
            public void run() {

                player.getEquipment().setArmorContents(armorContents);

            }

        }.runTaskLater(plugin, 20 * 2);

    }

    private static void clearArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public static EntityPlayer spawnFakePlayer(Player p, BlockPosition pos, Main plugin) throws Exception {

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

        EntityPlayer npc;
        npc = new EntityPlayer(server, world, new GameProfile(p.getUniqueId(), p.getName()), new PlayerInteractManager(world));
        Location loc = p.getLocation();
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        npc.getBukkitEntity().getInventory().setArmorContents(p.getInventory().getArmorContents());
        npc.getBukkitEntity().getInventory().setItemInMainHand(p.getInventory().getItem(0));
        setInvisible(p, plugin);

        for (Player all : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) all).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        }
        return npc;
    }

    public static org.bukkit.entity.Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (org.bukkit.entity.Entity entity : chunk.getEntities()) {
                    if (entity.getUniqueId().equals(uniqueId))
                        return entity;
                }
            }
        }

        return null;
    }

    public static void hasDied(Main plugin, GamePlayer damager, GamePlayer damaged) {

        if (damager != null) {

            if (damaged.resurrection) {
                damaged.getChampion().setHealth(damaged.getChampion().getMaxHealth());
                damaged.getChampion().setMana(damaged.getChampion().getMaxMana());
                damaged.getChampion().setHealth((int) (damaged.getChampion().getMaxHealth() * 0.5));
                damaged.getChampion().setMana((int) (damaged.getChampion().getMaxMana() * 0.5));
                new Title("", ChatColor.GREEN + "You have been resurrected.").send(Bukkit.getPlayer(damaged.getUuid()));

                return;
            }

            if (Bukkit.getPlayer(damager.getUuid()).getGameMode() == org.bukkit.GameMode.SPECTATOR || Bukkit.getPlayer(damaged.getUuid()).getGameMode() == org.bukkit.GameMode.SPECTATOR)
                return;


            /**
             * HANDLE SHOP ITEMS
             */

            if (damager.hasItem("Passion's Embrace")) {

                for (int i = 0; i < 3; i++) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            if (Bukkit.getPlayer(damaged.getUuid()).getGameMode() != org.bukkit.GameMode.SPECTATOR)
                                damager.getChampion().addHealth(50 / 3);
                            else
                                cancel();

                        }

                    }.runTaskLater(plugin, 20 * i);

                }

            }

            damager.setKills(damager.getKills() + 1);
            damager.setGold(damager.getGold() + Gold.enemyKill);
            Bukkit.getPlayer(damager.getUuid()).sendMessage(TextUtils.formatText("&7[&e+50 Gold&7]"));

            if (damaged.resistancePotion) {

                damaged.getChampion().setMaxHealth(damaged.getChampion().getMaxHealth() - 75);
                damaged.resistancePotion = false;
            }

            if (damaged.powerPotion) {

                damaged.getChampion().subtractAttackDamage(18);
                damaged.powerPotion = false;

            }

            ItemStack kills = new ItemStack(Material.IRON_AXE);
            ItemMeta killsMeta = kills.getItemMeta();
            killsMeta.setDisplayName(TextUtils.formatText("&6&lKills: " + damager.getKills()));
            killsMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            kills.setItemMeta(killsMeta);

            damager.getChampion().setExp(damager.getChampion().getExp() + 50);
            Bukkit.getPlayer(damager.getUuid()).sendMessage(TextUtils.formatText("&6&l+50 EXP"));
            Bukkit.getPlayer(damager.getUuid()).getInventory().setItem(13, kills);
            levelUp(damager);

        }

        damaged.setDeaths(damaged.getDeaths() + 1);
        boolean firstKill = false;
        for(GamePlayer gp : Main.gamePlayers){
            if (gp.firstPlayerKill){
                firstKill=true;
            }
        }
        if (!firstKill){
            damager.firstPlayerKill = true;
        }

        ItemStack deaths = new ItemStack(Material.SKULL_ITEM, 1, (byte) 0);
        ItemMeta deathsMeta = deaths.getItemMeta();
        deathsMeta.setDisplayName(TextUtils.formatText("&6&lDeaths: " + damaged.getDeaths()));
        deaths.setItemMeta(deathsMeta);
        Bukkit.getPlayer(damaged.getUuid()).getInventory().setItem(17, deaths);

        if (GameMode.getMode() == GameMode._1V1) {

                /*if (damaged.getTeam().getName().equalsIgnoreCase("red"))
                    Bukkit.getPlayer(damaged.getUuid()).teleport(new Location(Bukkit.getWorld("3v3"), 928, 17, 38)); // TODO: 7/20/2017 Change the world name.
                else
                    Bukkit.getPlayer(damaged.getUuid()).teleport(new Location(Bukkit.getWorld("3v3"), 638, 18, 37)); // TODO: 7/20/2017 Change the world name.*/

            if (damaged.getTeam().getName().equalsIgnoreCase("red")) {
                Bukkit.getPlayer(damaged.getUuid()).teleport(new Location(Bukkit.getWorld("3v3"), 943.5, 21, 37.5, 90, 0)); // for testing
            } else {
                Bukkit.getPlayer(damaged.getUuid()).teleport(new Location(Bukkit.getWorld("3v3"), 617.5, 21, 37.5, -90, 0)); // for testing
            }

        } else if (GameMode.getMode() == GameMode._3V3) {

            int seconds = 0;
            if (damaged.getDeaths() == 1)
                seconds = 7;
            else if (damaged.getDeaths() == 2)
                seconds = 15;
            else if (damaged.getDeaths() == 3)
                seconds = 20;
            else if (damaged.getDeaths() == 4)
                seconds = 25;
            else if (damaged.getDeaths() >= 5)
                seconds = 30;

            new Title("", TextUtils.formatText("&c&lYou have been slain by enemy sentry!"), 1, 2, 1).send(Bukkit.getPlayer(damaged.getUuid()));
            int finalSeconds = seconds;
            new BukkitRunnable() {

                @Override
                public void run() {

                    new Title("", TextUtils.formatText("&cYou will respawn in &7" + finalSeconds + " &cseconds!"), 1, 2, 1).send(Bukkit.getPlayer(damaged.getUuid()));

                }

            }.runTaskLater(plugin, 40);

            Bukkit.getPlayer(damaged.getUuid()).setGameMode(org.bukkit.GameMode.SPECTATOR);

            new BukkitRunnable() {

                @Override
                public void run() {

                    if (damaged.getTeam().getName().equalsIgnoreCase("red")) {
                        Bukkit.getPlayer(damaged.getUuid()).teleport(new Location(Bukkit.getWorld("3v3"), 943.5, 21, 37.5, 90, 0)); // TODO: 7/20/2017 Change the world name.
                    } else {
                        Bukkit.getPlayer(damaged.getUuid()).teleport(new Location(Bukkit.getWorld("3v3"), 617.5, 21, 37.5, -90, 0)); // TODO: 7/20/2017 Change the world name.
                    }

                    Bukkit.getPlayer(damaged.getUuid()).setGameMode(org.bukkit.GameMode.SURVIVAL);
                    damaged.getChampion().setHealth(damaged.getChampion().getMaxHealth());

                }

            }.runTaskLater(plugin, 20 * seconds);

        }

        if (damager == null)
            new Title("", TextUtils.formatText("&c&lYou have been slain by enemy sentry!"), 1, 2, 1).send(Bukkit.getPlayer(damaged.getUuid()));
        else
            new Title("", TextUtils.formatText("&c&lYou have been slain by an enemy!")).send(Bukkit.getPlayer(damaged.getUuid()));

        for (GamePlayer gamePlayer : Main.gamePlayers) {

            if (gamePlayer.getTeam().getName().equalsIgnoreCase(damaged.getTeam().getName())) {

                if (!damaged.getUuid().equals(gamePlayer.getUuid()))
                    new Title("", TextUtils.formatText("&cAn ally has been slain!"), 1, 2, 1).send(Bukkit.getPlayer(gamePlayer.getUuid()));

            } else {

                if (damager != null && !damager.getUuid().equals(gamePlayer.getUuid()))
                    new Title("", TextUtils.formatText("&cAn enemy has been slain!"), 1, 2, 1).send(Bukkit.getPlayer(gamePlayer.getUuid()));
                else if (damager != null && damager.getUuid().equals(gamePlayer.getUuid()))
                    new Title("", TextUtils.formatText("&c&lYou have slain an enemy!"), 1, 2, 1).send(Bukkit.getPlayer(gamePlayer.getUuid()));
                else
                    new Title("", TextUtils.formatText("&cAn enemy has been slain by your sentry!"), 1, 2, 1).send(Bukkit.getPlayer(gamePlayer.getUuid()));
            }

        }

    }

}
