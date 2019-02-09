package net.jmdev.commands;

import net.jmdev.Main;
import net.jmdev.game.GamePlayer;
import net.jmdev.util.GeneralUtils;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/03/2017 | 18:34
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
public class SetLevelCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setlevel")) {

            if (sender instanceof Player && sender.hasPermission("kc.level")) {

                if (args.length == 2) {

                    try {

                        int level = Integer.valueOf(args[0]);

                        for (GamePlayer gamePlayer : Main.gamePlayers) {

                            if (Bukkit.getPlayer(gamePlayer.getUuid()).getName().equalsIgnoreCase(args[1])) {

                                for (int i = gamePlayer.getLevel() + 1; i <= (level > 7 ? 7 : level); i++) {

                                    if (i == 2)
                                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 150);
                                    else if (i == 3)
                                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 182);
                                    else if (i == 4)
                                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 210);
                                    else if (i == 5)
                                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 243);
                                    else if (i == 6)
                                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 271);
                                    else if (i == 7)
                                        gamePlayer.getChampion().setExp(gamePlayer.getChampion().getExp() + 300);

                                    GeneralUtils.levelUp(gamePlayer);

                                }

                                return true;

                            }

                        }

                        sender.sendMessage(TextUtils.formatText("&7That player is not in the game or does not exist."));

                    } catch (NumberFormatException e) {
                        sender.sendMessage(TextUtils.formatText("&7Your argument must be a number."));
                    }

                } else {

                    sender.sendMessage(TextUtils.formatText("&7/setlevel <level> <player>"));

                }

            }

            return true;

        }

        return false;
    }
}
