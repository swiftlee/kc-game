package net.jmdev.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/10/2017 | 12:57
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
public class CollisionScoreboard {

    private static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    private Team collision = board.registerNewTeam("collision");

    public CollisionScoreboard(Entity e) {

        if (collision.getOption(Team.Option.COLLISION_RULE) != Team.OptionStatus.NEVER)
            collision.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        collision.addEntry(e.getUniqueId().toString());


    }

}
