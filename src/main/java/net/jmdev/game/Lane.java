package net.jmdev.game;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 08/09/2017 | 21:55
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
public enum Lane {

    LEFT, RIGHT, MIDDLE;

    public static Lane fromString(String s) {

        if (s.equalsIgnoreCase("left"))
            return LEFT;
        else if (s.equalsIgnoreCase("right"))
            return RIGHT;
        else if (s.equalsIgnoreCase("middle"))
            return MIDDLE;
        else
            return null;

    }

}
