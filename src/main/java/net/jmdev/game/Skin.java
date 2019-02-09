package net.jmdev.game;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/12/2017 | 16:03
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
public class Skin extends GameProfile {

    private Champion champion;

    public Skin(UUID id, String name, Champion champion) {
        super(id, name);
        this.champion = champion;
        this.getProperties().put("textures", new Property("textures", champion.getSkinHash(), champion.getSkinSignature()));
    }

    public Champion getChampion() {

        return champion;

    }

}
