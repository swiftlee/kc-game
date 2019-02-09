package net.jmdev.game;

import java.util.ArrayList;
import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/25/2017 | 17:07
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
public class Team extends ArrayList {

    public ArrayList<Champion> takenChampions = new ArrayList<>();
    private final String name;

    public Team(String name) {

        this.name = name;

    }

    @Override
    public boolean add(Object uuid) {

        if (uuid instanceof UUID && !this.contains(uuid)) {

            super.add(uuid);
            return true;

        }

        return false;

    }

    public String getName() {
        return name;
    }

}
