package net.jmdev.game;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/09/2017 | 21:52
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
public class TurretPriorityHandler {

    private static Collection<Turret> redTurrets = new ArrayList<>();
    private static Collection<Turret> blueTurrets = new ArrayList<>();

    //PARENT TURRET INDEX IS 0, ALL CHILDREN ARE 1+

    static void addTurret(UUID turretUUID, String teamColor, Lane lane, Location location, byte index) {

        if (teamColor.equalsIgnoreCase("red"))
            redTurrets.add(new Turret(turretUUID, teamColor, lane, index, location, index == 0));
        else
            blueTurrets.add(new Turret(turretUUID, teamColor, lane, index,  location, index == 0));

        System.out.print("uuid: " + turretUUID.toString() + " color: " + teamColor + " lane: " + lane.name() + " index: " + index);

    }
    static void removeTurret(Turret turret) {

        if (turret.getTeamColor().equalsIgnoreCase("red"))
            redTurrets.remove(turret);
        else
            blueTurrets.remove(turret);


    }

    public static Collection<Turret> getRedTurrets() {

        return redTurrets;

    }

    public static Collection<Turret> getBlueTurrets() {

        return blueTurrets;

    }

    private static Turret turretFromUUID(String color, UUID uuid) {

        if (color.equalsIgnoreCase("red")) {

            for (Turret turret : redTurrets) {

                if (turret.matches(uuid))
                    return turret;

            }

        } else {

            for (Turret turret : blueTurrets) {

                if (turret.matches(uuid))
                    return turret;

            }

        }

        return null;

    }

    public Turret turretFromLaneIndex(String color, Lane lane, byte index) {

        if (color.equalsIgnoreCase("red")) {

            for (Turret turret : redTurrets) {

                if (turret.matches(lane, index))
                    return turret;

            }

        } else {

            for (Turret turret : blueTurrets) {

                if (turret.matches(lane, index))
                    return turret;

            }

        }

        return null;

    }

    public static boolean rightLaneExists(String color) {

        if (color.equalsIgnoreCase("red")) {

            for (Turret turret : redTurrets) {

                if (turret.getLane() == Lane.RIGHT)
                    return true;

            }

        } else {

            for (Turret turret : blueTurrets) {

                if (turret.getLane() == Lane.RIGHT)
                    return true;

            }

        }

        return false;

    }

    public static boolean leftLaneExists(String color) {

        if (color.equalsIgnoreCase("red")) {

            for (Turret turret : redTurrets) {

                if (turret.getLane() == Lane.LEFT)
                    return true;

            }

        } else {

            for (Turret turret : blueTurrets) {

                if (turret.getLane() == Lane.LEFT)
                    return true;

            }

        }

        return false;

    }

    public static boolean middleLaneExists(String color) {

        if (GameMode.getMode() == GameMode._3V3) //we have to handle 3v3 differently since there is no middle-lane, we don't want a false check happening for nexus
            return true;

        if (color.equalsIgnoreCase("red")) {

            for (Turret turret : redTurrets) {

                if (turret.getLane() == Lane.MIDDLE)
                    return true;

            }

        } else {

            for (Turret turret : blueTurrets) {

                if (turret.getLane() == Lane.MIDDLE)
                    return true;

            }

        }

        return false;

    }

    public static boolean canAttack(String color, UUID uuid) {

        return !isParentAlive(color, uuid);

    }

    private static boolean isParentAlive(String color, UUID uuid) {

        Turret turret = turretFromUUID(color, uuid);

        if (turret == null)
            return true; //return true here since we invert the statement for everything else
        else if (turret.isParent())
            return false;

        if (color.equalsIgnoreCase("red")) {

            if (redTurrets.size() <= 0)
                return true;

            for (Turret turret1 : redTurrets) {

                if (turret1.matches(turret.getLane(), (byte) (turret.getIndex() - (byte) 1)))
                    return true;

            }

        } else {

            if (blueTurrets.size() <= 0)
                return true;

            for (Turret turret1 : blueTurrets) {

                if (turret1.matches(turret.getLane(), (byte) (turret.getIndex() - (byte) 1)))
                    return true;

            }

        }

        return false;

    }

}
