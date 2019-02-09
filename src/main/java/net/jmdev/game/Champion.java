package net.jmdev.game;

import net.jmdev.Main;

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/10/2017 | 20:41
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
public interface Champion {

    String name = "";
    double attackDamage = 0;
    int abilityPower = 0;
    double health = 0;
    int mana = 0;
    int healthRegen = 0;
    int manaRegen = 0;
    byte cooldownReduction = 0;
    byte criticalStrikeChance = 0;
    byte lifeSteal = 0;
    byte spellVamp = 0;
    ChampionType championType = null;
    String skinHash = "";
    String skinSignature = "";
    UUID skinUUID = null;
    String skinName = "";

    //getters
    String getName();

    //setters
    void setName(String name);

    double getAttackDamage();

    void setAttackDamage(double attackDamage);

    double getAbilityPower();

    void setAbilityPower(double abilityPower);

    double getHealth();

    void setHealth(double health);

    int getMana();

    void setMana(int mana);

    int getHealthRegen();

    void setHealthRegen(int healthRegen);

    int getManaRegen();

    void setManaRegen(int manaRegen);

    byte getCooldownReduction();

    void setCooldownReduction(byte cooldownReduction);

    byte getCriticalStrikeChance();

    void setCriticalStrikeChance(byte criticalStrikeChance);

    byte getLifeSteal();

    void setLifeSteal(byte lifeSteal);

    byte getSpellVamp();

    void setSpellVamp(byte spellVamp);

    ChampionType getChampion();

    String getSkinHash();

    String getSkinSignature();

    UUID getSkinUUID();

    String getSkinName();

    Skin getSkin();

    //extras - addable
    void addAttackDamage(double attackDamage);

    void addAbilityPower(double abilityPower);

    void addHealth(double health);

    void addMana(int mana);

    void addHealthRegen(int healthRegen);

    void addManaRegen(int manaRegen);

    void addCooldownReduction(byte cooldownReduction);

    void addCriticalStrikeChance(byte criticalStrikeChance);

    void addLifeSteal(byte lifeSteal);

    void addSpellVamp(byte spellVamp);


    //extras - subtractable
    void subtractAttackDamage(double attackDamage);

    void subtractAbilityPower(double abilityPower);

<<<<<<< HEAD
    void subtractHealth(double health, Main plugin, GamePlayer attacker, GamePlayer attacked, boolean isBasic);
=======
    void subtractHealth(double health, Main plugin, GamePlayer attacker, GamePlayer attacked);
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    void subtractMana(int mana);

    void subtractHealthRegen(int healthRegen);

    void subtractManaRegen(int manaRegen);

    void subtractCooldownReduction(byte cooldownReduction);

    void subtractCriticalStrikeChance(byte criticalStrikeChance);

    void subtractLifeSteal(byte lifeSteal);

    void subtractSpellVamp(byte spellVamp);

    int getExp();

    void setExp(int exp);

    void setUltActive(boolean b);

    boolean isUltActive();

    int getMaxMana();

    void setMaxMana(int maxMana);

    int getMaxHealth();

    void setMaxHealth(int maxHealth);
}
