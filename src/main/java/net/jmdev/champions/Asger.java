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
 * J&M CONFIDENTIAL - @author Jon - 06/10/2017 | 21:10
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
public final class Asger implements Champion {

    private final ChampionType championType = ChampionType.WARRIOR;
    private final UUID skinUUID = UUID.fromString("3f9d6396-ecf8-3381-9342-e1f9d67ce5a6");
    private final String skinName = "skin14729";
    private final String skinHash = "eyJ0aW1lc3RhbXAiOjE0OTcyMjYyMTg2MTMsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR2cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzViNTNmNjVjYzc3ZjMxNWI0MGIzMDE4NDg1Nzg2ZDU0OTMzNjg1ZDIxODM4YzA4YjIxMzIxZTc4ZjQ3NDgifX19";
    private final String skinSignature = "d9NHH4++sNJQrTDt43S7SXtVqhCj00StAksUuvn196GphGNcUylz0e/HjUIfYPQZuJITPAyLZqJ8cDW08Sk9UxQZtZ18aYKNgHsUCwYZqs1a0wY3oje9NkTYTtx3m6Vu9wGjofbJdlVS3BO31mbEhw7BL9T9qntaD34iHdEuzaSnAnjUJ+izpfExqcvsP2mJ+jfrAuTNgy43KcYQOU/XQN38thOflCT9PIq7HzWRcSsR0X7ztjWn0PU0X6k3dBq9QESHnzCo8gt/kIgyNwuimuDP/1P6Pk96Ljpw+TLzm2ictWHVoIXJX4WMJKAoSGACy1xus3mnIDS8f6qxlzt/BDTAdLQAA2Cm//1Tr5CWiFwKYsxpc22DW8BUMdG0fkyBO/4OEgzj9dvWfU/ybowFOxp/RrBDzSq3gSCosSVwdC2GsU738QlFTAeIRMF0K/TdZ6o7pc2yKqAT7lZTYhKSzSGGe8+MrD8hV1X4QwTsl/fHWvxORS7OmNu5SYAEVypl4rCl6ukyW4Awjn0UkwuJx0xZwJJ7gzZszCj2P/7e5ZJD9JU5Gi1K2GFG31Eij/NKYdRhKxh14C7MUdr16vykQVfZRu4pDUYX9TTW/Yh+8igZlLg0Hp2AyndPLmN/dDldjr/uZR6l6rIkE5jkOqgvoLlv7v0L4M+YBJkop2BfOuE=";
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
    private boolean ultActive;
    private int maxMana;
    private int maxHealth;

    public Asger() {
        name = "Asger";
        attackDamage = 24;
        abilityPower = 0;
        health = 370;
        maxHealth = 370;
        maxMana = 0;
        mana = 0;
        healthRegen = 5; //per 5 seconds
        manaRegen = 0;
        cooldownReduction = 0;
        criticalStrikeChance = 0;
        lifeSteal = 0;
        spellVamp = 0;
        exp = 0;
        ultActive = false;
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
<<<<<<< HEAD
        if (this.health + health >= maxHealth)
            this.health = maxHealth;
        else
=======
        if (this.health + health >= maxHealth) {
            this.health = maxHealth;
        } else {
>>>>>>> 7a09dc0bdc06869793da822b0908fe37e8c7ba03
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

    public boolean isUltActive() {
        return ultActive;
    }

    public void setUltActive(boolean ultActive) {
        this.ultActive = ultActive;
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
