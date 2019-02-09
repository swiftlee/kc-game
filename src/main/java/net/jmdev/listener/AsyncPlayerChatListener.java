package net.jmdev.listener;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.GameState;
import net.jmdev.util.TextUtils;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/26/2017 | 01:00
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
public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerTalk(AsyncPlayerChatEvent e) {

        if (GameState.getGameState() == GameState.STARTED) {

            e.setCancelled(true);
            if (!e.getMessage().substring(0, 1).equalsIgnoreCase("!")) {

                int i = 0;

                for (GamePlayer gamePlayer : Main.gamePlayers) {

                    if (gamePlayer.getUuid().equals(e.getPlayer().getUniqueId())) {

                        for (Object uuid : gamePlayer.getTeam()) {

                            Bukkit.getPlayer((UUID) uuid).sendMessage(TextUtils.formatText("&e[Team Chat] " + e.getPlayer().getName() + " &7» &f" + e.getMessage()));
                            i++;
                            if (i == gamePlayer.getTeam().size()) {

                                return;

                            }

                        }

                    }

                }

            } else {

                Chat chat = Main.getChat();
                String prefix = chat.getPlayerPrefix(e.getPlayer());
                String suffix = chat.getPlayerSuffix(e.getPlayer());
                String name = e.getPlayer().getName().trim();
                if (e.getPlayer().hasPermission("text.color")) {
                    e.setMessage(TextUtils.formatText(e.getMessage()));
                }
                if (prefix == null && suffix == null) {
                    prefix = "";
                    suffix = "";
                }
                if (!prefix.equalsIgnoreCase("") && !prefix.equalsIgnoreCase(" ")) {
                    prefix += " ";
                }
                if (!suffix.equalsIgnoreCase("") && !suffix.equalsIgnoreCase(" ")) {
                    suffix += " ";
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(TextUtils.formatText(prefix + name + suffix + "&7 » &f") + e.getMessage().substring(1, e.getMessage().length()));
                }

            }

        }

    }

}
