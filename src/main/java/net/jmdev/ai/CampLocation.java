package net.jmdev.ai;

import net.jmdev.game.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 09/02/2017 | 15:23
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
final class CampLocation {

    /**
     Mercenaries (Red Team) - X: 830 Y: 8 Z: 63
     Guardian (Red Team) - X: 793 Y: 8 Z: 60
     Weavers (Red Team) - X: 816 Y: 8 Z: 18
     Guardian (Blue Team) - X: 761 Y: 8 Z: 15
     Weavers (Blue Team) - X: 739 Y: 8 Z: 61
     Mercenaries (Blue Team) - X: 730 Y: 8 Z: 11
     The Boss - X: 780 Y: 8 Z: -66
     */

    private static final World _3V3 = GameMode.getMode() == GameMode._3V3 ? Bukkit.getWorld("3v3") : null;
    public static final Location _3V3MR = GameMode.getMode() == GameMode._3V3 ? new Location(_3V3, 830.5, 8, 63.5) : null;
    public static final Location _3V3MB = GameMode.getMode() == GameMode._3V3 ? new Location(_3V3, 730.5, 8, -66.5) : null;



}
