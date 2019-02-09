package net.jmdev.game;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/10/2017 | 00:59
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
public enum ChampionType {

    WARRIOR, ASSASSIN, MAGE, PRIEST, MARKSMAN;

    @Override
    public String toString() {

        if (this == WARRIOR) return "Warrior";
        else if (this == ASSASSIN) return "Assassin";
        else if (this == MAGE) return "Mage";
        else if (this == PRIEST) return "Priest";
        else if (this == MARKSMAN) return "Marksman";
        else return "Unknown";

    }

}
