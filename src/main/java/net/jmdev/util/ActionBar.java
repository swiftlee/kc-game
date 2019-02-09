package net.jmdev.util;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBar {

    private Main plugin;

    public ActionBar(Main plugin) {
        this.plugin = plugin;
    }

    public static void sendActionBar(Player p, String message) {

        if (p == null)
            return;

        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);

    }

    public void startUpdates() {

        new BukkitRunnable() {

            @Override
            public void run() {

                for (GamePlayer gamePlayer : Main.gamePlayers) {

                    if (gamePlayer == null || Bukkit.getPlayer(gamePlayer.getUuid()) == null)
                        continue;

                    String health = TextUtils.formatText("&b&lMana: " + gamePlayer.getChampion().getMana() + "/" + gamePlayer.getChampion().getMaxMana());
                    sendActionBar(Bukkit.getPlayer(gamePlayer.getUuid()), health);
                    Bukkit.getPlayer(gamePlayer.getUuid()).setLevel((int) gamePlayer.getChampion().getHealth());

                }

            }

        }.runTaskTimer(plugin, 0, 5);

    }

}
