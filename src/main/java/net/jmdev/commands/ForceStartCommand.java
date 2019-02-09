package net.jmdev.commands;

import de.slikey.effectlib.EffectManager;

import net.jmdev.Main;
import net.jmdev.game.ChampionSelection;
import net.jmdev.game.GamePlayer;
import net.jmdev.game.GameState;
import net.jmdev.util.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/25/2017 | 00:17
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
public class ForceStartCommand implements CommandExecutor {

    private Main plugin;
    private EffectManager effectManager;

    public ForceStartCommand(Main plugin, EffectManager effectManager) {
        this.plugin = plugin;
        this.effectManager = effectManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("forcestart")) {

            if (commandSender.hasPermission("kc.gameStart")) {

                new ChampionSelection(plugin, effectManager).start(true);
                return true;

            }

        } else if (command.getName().equalsIgnoreCase("chop")) {

            if (commandSender instanceof Player && commandSender.getName().matches("^(KingdomCrashers|SwiftLee)$") && commandSender.isOp() && GameState.getGameState() == GameState.STARTED && args.length == 1) {

                Player player = Bukkit.getPlayer(args[0]);

                if (player != null) {

                    GamePlayer gamePlayer = null;

                    for (GamePlayer gamePlayer1 : Main.gamePlayers) {

                        if (gamePlayer1.getUuid().equals(((Player) commandSender).getUniqueId())) {

                            gamePlayer = gamePlayer1;

                        }

                    }

                    for (GamePlayer otherPlayer : Main.gamePlayers) {

                        if (otherPlayer.getUuid().equals(player.getUniqueId())) {

<<<<<<< HEAD
                            otherPlayer.getChampion().subtractHealth(otherPlayer.getChampion().getHealth(), plugin, gamePlayer, otherPlayer, false);
=======
                            otherPlayer.getChampionType().subtractHealth(otherPlayer.getChampionType().getHealth(), plugin, gamePlayer, otherPlayer);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
                            player.sendMessage(TextUtils.formatText("&cFa chop, son of a chop."));

                        }

                    }

                }

            }

            return true;

        } else if (command.getName().equalsIgnoreCase("dance")) {

            if (commandSender instanceof Player && commandSender.getName().matches("^(KingdomCrashers|SwiftLee)$") && commandSender.isOp() && args.length == 1) {

                Player player = Bukkit.getPlayer(args[0]);

                if (player != null) {

                    for (int i = 0; i < 20 * 20; i++) {

                        int j = i;

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                if (j % 10 == 0)
                                    player.setVelocity(new Vector(0, 0.5, 0));

                            }

                        }.runTaskLater(plugin, i);

                    }

                    float randomFloat = new Random().nextFloat();

                    if (randomFloat < 0.1F)
                        player.sendMessage(TextUtils.formatText("&a&lShow me your moves, &e&l" + player.getName() + "&a&l!"));
                    else if (randomFloat >= 0.1F && randomFloat < 0.2F)
                        player.sendMessage(TextUtils.formatText("&a&lWhere'd you learn to dance like, &e&l" + player.getName() + "&a&l?!"));
                    else if (randomFloat >= 0.2F && randomFloat < 0.3F)
                        player.sendMessage(TextUtils.formatText("&a&lShake it to make it, &e&l" + player.getName() + "&a&l!"));
                    else if (randomFloat >= 0.3F && randomFloat < 0.4F)
                        player.sendMessage(TextUtils.formatText("&a&lThis isn't a waltz--quit dancing, &e&l" + player.getName() + "&a&l!"));
                    else if (randomFloat >= 0.4F && randomFloat < 0.5F)
                        player.sendMessage(TextUtils.formatText("&a&lWhat kind of dance is that, &e&l" + player.getName() + "&a&l?!"));
                    else if (randomFloat >= 0.5F && randomFloat < 0.6F)
                        player.sendMessage(TextUtils.formatText("&a&lInterpretive dancing, &e&l" + player.getName() + "&a&l?"));
                    else if (randomFloat >= 0.6F && randomFloat < 0.7F)
                        player.sendMessage(TextUtils.formatText("&a&lDrop it low, drop it low, &e&l" + player.getName() + "&a&l!"));
                    else if (randomFloat >= 0.7F && randomFloat < 0.8F)
                        player.sendMessage(TextUtils.formatText("&a&lDance the dance of life, &e&l" + player.getName() + "&a&l!"));
                    else if (randomFloat >= 0.8F && randomFloat < 0.9F)
                        player.sendMessage(TextUtils.formatText("&a&lHit those funky dance moves, &e&l" + player.getName() + "&a&l!"));
                    else if (randomFloat >= 0.9F && randomFloat < 1F)
                        player.sendMessage(TextUtils.formatText("&a&lAre you going to dance like that at the ball, &e&l" + player.getName() + "&a&l?"));

                }

            }

            return true;

        }

        return false;
    }

}
