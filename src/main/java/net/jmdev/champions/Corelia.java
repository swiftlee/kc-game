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
 * J&M CONFIDENTIAL - @author Jon - 06/10/2017 | 22:29
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
public final class Corelia implements Champion {

    private final ChampionType championType = ChampionType.MARKSMAN;
    private final UUID skinUUID = UUID.fromString("c898203f-3e27-3d8d-b0df-d7fbb872728a");
    private final String skinName = "skin14730";
    private final String skinHash = "eyJ0aW1lc3RhbXAiOjE0OTcyMjYyMzUzODEsInByb2ZpbGVJZCI6ImUzYjQ0NWM4NDdmNTQ4ZmI4YzhmYTNmMWY3ZWZiYThlIiwicHJvZmlsZU5hbWUiOiJNaW5pRGlnZ2VyVGVzdCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR2cmUvZTQwNjY1MTE3ZjM0NDRjOWFjZDk4YmQ5MTUxMmY0Y2U4YzdkOWQ5NzYzN2I3ZjVjNmI5OWZmNzU4NGMyZCJ9fX0=";
    private final String skinSignature = "scFFLdydw70vGjBGk3IIsR86bQ+1WDyGguEiNlc/G+GRoiTqDIjS2g83W5MUGyrbpagf6mDTrOFyn/RuSkZ1760oGbnPKjg0lHQ1H+bSpBo9Em7QamsHz6A4MzKa6SY+497Uz5eeJ4bK1o3n/dl+Vbl+ivLxS+VF5QXVQgsRQ3f4DUpeS6+1le/C5pETXoj/0vELwDuOi5FtlBZNqyKyGCAuZVbqJyxUQm9VlQ3IMSNBsvB298m6eDOXQ/6JvlcmCZ6cPmQOOhuZWuDPOR/gmGWMmuDCzt0IHMHLsfLUwvcA1zTPsfw4mkxuY+SFJM1gGteq8jDYf9th12znebsSVg4k+TBPACrp9sm/1CWrTVfwkWrrHVDY2sSgSpQ2LNVJPi3p1w9c2LdAPI5VRRT8E2nrlUfZFWJpD/yN33j0OempZDurrG5LxftSf4s3jxC0y+yQC0baEgtp42NUMJJL4ivjZ6xIREnBdAn1grOm51WHl1n177xgy84rX/7ViprfIY52MjEVgX6hI4ECauuuNBN7oL5H2XEhBR2SPlyaQwpe39QiepNQAIgTQV7wtSrAluC1hZuw4B/UQRyBb/7VY8BFBnUyijg5h73HePKseT2AqyyUsuCcpEIFFEmvATzER2SuBmhW1Gfe7VRUw/DhE7kgEUdvl8Cg3RmnBP06uQo=";
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
    private boolean blastZoneActive;

    public Corelia() {
        name = "Corelia";
        attackDamage = 25;
        abilityPower = 0;
        health = 200;
        maxHealth = 200;
        maxMana = 0;
        mana = 0;
        healthRegen = 4; //per 5 seconds
        manaRegen = 0;
        cooldownReduction = 0;
        criticalStrikeChance = 0;
        lifeSteal = 0;
        spellVamp = 0;
        exp = 0;
        blastZoneActive = false;
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

            health += (health * (lifeSteal / 100));

            if (attacker.hasItem("Soulstealer"))
                attacker.getChampion().addHealth(15);

            if (attacker.hasItem("Pandora's Stormbow") && (attacker.pandoraStormbow == 0 || (attacker.pandoraStormbow + 25000) <= System.currentTimeMillis())) {

                ShopItemHandler.doPandoraStormbow(attacked);
                health += attacked.getChampion().getMaxHealth() * 0.1;

            }

            if (attacked.hasItem("Reflecting Shield") && (attacked.reflectingShield == 0 || (attacked.reflectingShield + 45000) <= System.currentTimeMillis())) {

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

    @Override
    public String getSkinHash() {
        return skinHash;
    }

    @Override
    public String getSkinSignature() {
        return skinSignature;
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

    boolean isBlastZoneActive() {
        return blastZoneActive;
    }

    void setBlastZoneActive(boolean blastZoneActive) {
        this.blastZoneActive = blastZoneActive;
    }
}
