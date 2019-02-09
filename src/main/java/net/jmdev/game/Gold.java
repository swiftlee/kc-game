package net.jmdev.game;

import java.util.Random;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 07/21/2017 | 11:34
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
public class Gold {

    public static final int firstEnemyTowerKill = 300;
    public static final int enemyTowerKill = 200;
    public static final int enemyKill = 100; //DONE
    public static final int assist = 50;
    public static final int buffCamp = 200;
    public static final int mercenaryMobCamp = 100;
    public static final int bossCamp = 500; //give to all team players

    public static int rangedMeleeMinionKill() {

        return new Random().nextInt((22 - 16) + 1) + 16;

    }

    public static int siegeMinionKill() {

        return new Random().nextInt((31 - 24) + 1) + 24;

    }

    public static int superMinionKill() {

        return new Random().nextInt((55 - 50) + 1) + 50;

    }

    public static int basicMinionKill() {

        return new Random().nextInt((22 - 14) + 1) + 14;

    }

}
