package net.jmdev.champions;

import net.jmdev.Main;
import net.jmdev.game.Champion;
import net.jmdev.game.ChampionType;
import net.jmdev.game.GamePlayer;
<<<<<<< HEAD
import net.jmdev.game.ShopItemHandler;
import net.jmdev.game.Skin;
import net.jmdev.util.GeneralUtils;
import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutEntityDestroy;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
=======
import net.jmdev.game.Skin;
import net.jmdev.util.GeneralUtils;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

import java.util.UUID;

/*************************************************************************
 *
 * J&M CONFIDENTIAL - @author Jon - 06/10/2017 | 22:18
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
public final class XiauTsun implements Champion {

    private final ChampionType championType = ChampionType.ASSASSIN;
    private final UUID skinUUID = UUID.fromString("393f2133-6361-3ad7-8f23-c513ea0eef24");
    private final String skinName = "skin14731";
    private final String skinHash = "eyJ0aW1lc3RhbXAiOjE0OTcyMjYzNTE3MzMsInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJZZWxlaGEiLCJzaWduYXR2cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE5MWUyZTFiMTk3MWI5ZmRjNTQ5YTllZTNiMDM3OTExNmU4MmNiMjRmMTc4MzQyOWQ5NTMzN2VhNmMifX19";
    private final String skinSignature = "aLOQQFOVC/zDsrfDsSwVRhiqrIaoTpL0/rBpQ0ofcXRN3f8jFVGBuBBwHAaccyq3RaFhdwkqDE4n82he0VRXQNW+jEtgD3pqnZbWZSK7djlnlCGzo3zTNB0epcWirgSCNQpyYuXldxMICgMqhhnGEjHjeJ2AR/9KoGasznw1beTf1SyHrUkN+EdJwrSqynSeFaMTotRVKf5eAciOHt0ZzUy8S8Q0d/wwDcFD42PhnmiNPiTQ7M8DieQWc4NiebKCRtiG0d+8HrppN0Nh5GClifa2vbOizWQZJLNFobt4tXP97s5XIe261kQXPO/9sO9C7IWyB5XqDEnWV/m+gR3Vy5LgiuZv9OiOWZCCCesCcrj5dAd76IitK9y1HSZqP/5i3VXOGFh5aJNmysjYAsg1DW5PDFJRB5vDL7R5bl4KOjL4bqiXDi1QBOEzCE0S8ZO/JJ35WiGy3uVhyxo+E06gEzaqQzdT62FQKUOHb++XNGV8ImskXyVGpaRNc+na4CEXc3WdwAEQch64dgq7FQg328lOFpjUIYGxqtzEH8C8ZcRv4lYFCSEcPdtCRiWYQRFY2RwWQ/ccNYgsqqTOsREMs0cpea/yetLmYO1tFrpYxJ3pc3oAbfss3IZAFIEAFOJztj8q3gq58Lf27XOFnOdEx8ptePuNubJ7OxGnIfBqkoQ=";
    private String name;
    private double attackDamage;
    private double abilityPower;
    private double health;
    private int mana;
    private int healthRegen;
    private int manaRegen;
    private byte cooldownReduction;
    private byte criticalStrikeChance;
    private byte lifeSteal;
    private byte spellVamp;
    private int exp;
    private int maxMana;
    private int maxHealth;

    public XiauTsun() {
        name = "Xiau Tsun";
        attackDamage = 33;
        abilityPower = 0;
        health = 240;
        maxHealth = 240;
        maxMana = 250;
        mana = 250;
        maxHealth = 250;
        healthRegen = 5; //per 5 seconds
        manaRegen = 3; //per 5 seconds
        cooldownReduction = 0;
        criticalStrikeChance = 0;
        lifeSteal = 0;
        spellVamp = 0;
        exp = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {

        this.name = name;

    }

    @Override
    public double getAttackDamage() {
        return attackDamage;
    }

    @Override
    public void setAttackDamage(double attackDamage) {

        this.attackDamage = attackDamage;

    }

    @Override
    public double getAbilityPower() {
        return abilityPower;
    }

    @Override
    public void setAbilityPower(double abilityPower) {

        this.abilityPower = abilityPower;

    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {

        this.health = health;

    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(int mana) {

        this.mana = mana;

    }

    @Override
    public int getHealthRegen() {
        return healthRegen;
    }

    @Override
    public void setHealthRegen(int healthRegen) {

        this.healthRegen = healthRegen;

    }

    @Override
    public int getManaRegen() {
        return manaRegen;
    }

    @Override
    public void setManaRegen(int manaRegen) {

        this.manaRegen = manaRegen;

    }

    @Override
    public byte getCooldownReduction() {
        return cooldownReduction;
    }

    @Override
    public void setCooldownReduction(byte cooldownReduction) {

        this.cooldownReduction = cooldownReduction;

    }

    @Override
    public byte getCriticalStrikeChance() {
        return criticalStrikeChance;
    }

    @Override
    public void setCriticalStrikeChance(byte criticalStrikeChance) {

        this.criticalStrikeChance = criticalStrikeChance;

    }

    @Override
    public byte getLifeSteal() {
        return lifeSteal;
    }

    @Override
    public void setLifeSteal(byte lifeSteal) {

        this.lifeSteal = lifeSteal;

    }

    @Override
    public byte getSpellVamp() {
        return spellVamp;
    }

    @Override
    public void setSpellVamp(byte spellVamp) {

        this.spellVamp = spellVamp;

    }

    @Override
    public void addAttackDamage(double attackDamage) {

        this.attackDamage += attackDamage;

    }

    @Override
    public void addAbilityPower(double abilityPower) {

        this.abilityPower += abilityPower;

    }

    @Override
    public void addHealth(double health) {
        if (this.health + health >= maxHealth)
            this.health = maxHealth;
        else
            this.health += health;

    }

    @Override
    public void addMana(int mana) {

        if (this.mana + mana >= maxMana)
            this.mana = maxMana;
        else
            this.mana += mana;

    }

    @Override
    public void addHealthRegen(int healthRegen) {

        this.healthRegen += healthRegen;

    }

    @Override
    public void addManaRegen(int manaRegen) {

        this.manaRegen += manaRegen;

    }

    @Override
    public void addCooldownReduction(byte cooldownReduction) {

        this.cooldownReduction += cooldownReduction;

    }

    @Override
    public void addCriticalStrikeChance(byte criticalStrikeChance) {

        this.criticalStrikeChance += criticalStrikeChance;

    }

    @Override
    public void addLifeSteal(byte lifeSteal) {

        this.lifeSteal += lifeSteal;

    }

    @Override
    public void addSpellVamp(byte spellVamp) {

        this.spellVamp += spellVamp;

    }

    @Override
    public void subtractAttackDamage(double attackDamage) {

        this.attackDamage -= attackDamage;

    }

    @Override
    public void subtractAbilityPower(double abilityPower) {

        this.abilityPower -= abilityPower;

    }

    @Override
<<<<<<< HEAD
    public void subtractHealth(double health, Main plugin, GamePlayer attacker, GamePlayer attacked, boolean isBasic) {

        if (attacked.hearthstoneTask != null) {

            attacked.hearthstoneTask.cancel();
            attacked.hearthstoneTask = null;

        }

        if (attacker != null && attacked.hasItem("Vow of The Protector") && (attacked.vowOfProtector == 0 || (attacked.vowOfProtector + 45000) <= System.currentTimeMillis())) {

            attacked.vowOfProtector = System.currentTimeMillis();
            return;

        }

            if (isBasic && attacker != null) {

                attacker.getChampion().addHealth(health * (lifeSteal / 100));

                if (attacker.hasItem("Soulstealer"))
                    attacker.getChampion().addHealth(15);

                if (attacker.hasItem("Pandora's Stormbow") && (attacker.pandoraStormbow == 0 || (attacker.pandoraStormbow + 25000) <= System.currentTimeMillis())) {

                    ShopItemHandler.doPandoraStormbow(attacked);
                    health += attacked.getChampion().getMaxHealth() * 0.1;

                }

                if (attacked.hasItem("Reflecting Shield") && (attacked.reflectingShield == 0 || attacked.reflectingShield + 45000 <= System.currentTimeMillis())) {

                    attacker.getChampion().subtractHealth(health / 2, plugin, attacked, attacker, false);
                    attacked.reflectingShield = System.currentTimeMillis();
                    health /= 2;

                }

            }

            if (this.health - health <= 0)
                GeneralUtils.hasDied(plugin, attacker, attacked);
            else {

                if (attacked != null && attacked.resistancePotion) {

                    health -= (health * 0.1);

                }

                this.health -= health;
                if (attacked == null)
                    return;

                if (attacked.hasItem("Illusionist's Gem") && (attacked.illusionGem == 0 || (attacked.illusionGem + 120000) <= System.currentTimeMillis()) && (int) health <= (int) (maxHealth * 0.2)) {

                    EntityPlayer fakePlayer;

                    try {

                        fakePlayer = GeneralUtils.spawnFakePlayer(Bukkit.getPlayer(attacked.getUuid()), new BlockPosition(((CraftPlayer) Bukkit.getPlayer(attacked.getUuid())).getHandle()), plugin);
                        attacked.getChampion().addHealth(attacked.getChampion().getMaxHealth() * 0.2);
                        attacked.illusionGem = System.currentTimeMillis();

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                for (Player all : Bukkit.getOnlinePlayers()) {

                                    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(fakePlayer.getBukkitEntity().getEntityId());
                                    ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);

                                }

                                fakePlayer.getBukkitEntity().remove();

                            }

                        }.runTaskLater(plugin, 20 * 6);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
=======
    public void subtractHealth(double health, Main plugin, GamePlayer attacker, GamePlayer attacked) {

        if (this.health <= 0)
            GeneralUtils.hasDied(plugin, attacker, attacked);
        else
            this.health -= health;
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03

    }

    @Override
    public void subtractMana(int mana) {

        this.mana -= mana;

    }

    @Override
    public void subtractHealthRegen(int healthRegen) {

        this.healthRegen -= healthRegen;

    }

    @Override
    public void subtractManaRegen(int manaRegen) {

        this.manaRegen -= manaRegen;

    }

    @Override
    public void subtractCooldownReduction(byte cooldownReduction) {

        this.cooldownReduction -= cooldownReduction;

    }

    @Override
    public void subtractCriticalStrikeChance(byte criticalStrikeChance) {

        this.criticalStrikeChance -= criticalStrikeChance;

    }

    @Override
    public void subtractLifeSteal(byte lifeSteal) {

        this.lifeSteal -= lifeSteal;

    }

    @Override
    public void subtractSpellVamp(byte spellVamp) {

        this.spellVamp -= spellVamp;

    }

    @Override
    public ChampionType getChampion() {
        return championType;
    }

    @Override
    public String getSkinHash() {
        return skinHash;
    }

    @Override
    public String getSkinSignature() {
        return skinSignature;
    }

    @Override
    public UUID getSkinUUID() {
        return skinUUID;
    }

    @Override
    public String getSkinName() {
        return skinName;
    }

    @Override
    public Skin getSkin() {
        return new Skin(skinUUID, skinName, this);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public boolean isUltActive() {
        return false;
    }

    @Override
    public void setUltActive(boolean b) {

    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
