package net.jmdev.game;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/09/2017 | 03:15
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
public enum GameState {

    WAITING, CHAMPION_SELECT, STARTED, ENDED;

    private static GameState state;

    public static GameState getGameState() {

        return state;

    }

    public static void setGameState(GameState state) {

        GameState.state = state;

    }

}
