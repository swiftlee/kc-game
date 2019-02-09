package net.jmdev.game;

import org.bukkit.Location;

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/09/2017 | 22:02
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
public class Turret {

    private final UUID turretUUID;
    private final String teamColor;
    private final Lane lane;
    private final byte index;
    private final Location location;
    private final boolean isParent;

    public Turret(UUID turretUUID, String teamColor, Lane lane, byte index, Location location, boolean isParent) {

        this.turretUUID = turretUUID;
        this.teamColor = teamColor;
        this.lane = lane;
        this.index = index;
        this.location = location;
        this.isParent = isParent;
    }

    public UUID getTurretUUID() {
        return turretUUID;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public Lane getLane() {
        return lane;
    }

    public byte getIndex() {
        return index;
    }

    public boolean isParent() {
        return isParent;
    }

    public boolean matches(Turret turret) {

        return turretUUID.equals(turret.getTurretUUID());

    }

    public boolean matches(UUID uuid) {

        return this.turretUUID.equals(uuid);

    }

    public boolean matches(Lane lane, byte index) {

        return this.index == index && this.lane == lane;

    }

    public Location getLocation() {
        return location;
    }
}
