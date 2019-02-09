package net.jmdev.commands;

import net.jmdev.Main;
import net.jmdev.game.EndGame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/12/2017 | 18:18
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
public class EndGameCommand implements CommandExecutor {

    private Main plugin;

    public EndGameCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (cmd.getName().equalsIgnoreCase("endgame")) {

            if (sender instanceof Player) {

                Player p = (Player) sender;
                if (p.hasPermission("kc.endgame")) {

                    new EndGame(plugin, Main.teams.get(0)).endGame();

                }

            }

            return true;

        }

        return false;
    }
}
