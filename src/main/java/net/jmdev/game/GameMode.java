package net.jmdev.game;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/09/2017 | 03:30
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
public enum GameMode {

    _1V1, _3V3, _5V5;

    private static GameMode mode;
    private static int maxPlayers;

    public static GameMode getMode() {

        return mode;

    }

    public static void setMode(GameMode mode) {

        GameMode.mode = mode;

    }

    public int getMaxPlayers() {
        switch (GameMode.getMode()) {
            case _1V1:
                maxPlayers = 2;
                break;
            case _3V3:
                maxPlayers = 6;
                break;
            case _5V5:
                maxPlayers = 10;
                break;
        }
        return maxPlayers;
    }

    @Override
    public String toString() {

        if (this == _1V1) {
            return "1v1";
        } else if (this == _3V3) {
            return "3v3";
        } else if (this == _5V5) {
            return "5v5";
        } else {
            return "null";
        }

    }
}
